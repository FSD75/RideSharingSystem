import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RideSharingSystem implements IRideSharingSystem{

  private RiderList riders;
  private DriverList drivers;
  private RideList rides;
  private LinkedList<IRider> riderNames;
  private LinkedList<IDriver> driverNames;
  private int nextRideId;

  // Start with empty lists and the first ride ID.
  public RideSharingSystem(){
    riders = new RiderList();
    drivers = new DriverList();
    rides = new RideList();
    riderNames = new LinkedList<IRider>();
    driverNames = new LinkedList<IDriver>();
    nextRideId = 1;
  }

  // Check that a required text value is present.
  private boolean hasText(String value){
    return value != null && !value.trim().isEmpty();
  }

  // Check the common details before adding a person.
  private boolean validPerson(IPerson person){
    return person != null && hasText(person.getName())
        && person.getPhoneNumber() != null && person.getPhoneNumber().matches("[0-9]{10}")
        && person.getRideHistory() != null;
  }

  // Add a valid rider; RiderList checks duplicate IDs and emails.
  public boolean addRider(IRider rider){
    if(!validPerson(rider) || !hasText(rider.getEmail()) || !hasText(rider.getHomeCity()))
      return false;
    if(!riders.add(rider))
      return false;
    insertName(riderNames, rider);
    return true;
  }

  // Add a valid driver; DriverList checks duplicate IDs and plates.
  public boolean addDriver(IDriver driver){
    if(!validPerson(driver) || driver.getVehicleType() == null || driver.getVehiclePlate() == null
        || !driver.getVehiclePlate().matches("[A-Z]{3}[0-9]{4}"))
      return false;
    if(!drivers.add(driver))
      return false;
    insertName(driverNames, driver);
    return true;
  }

  // Store a name ordering as well as the ID ordering, making listing O(n).
  private <T extends IPerson> void insertName(LinkedList<T> list, T person){
    if(list.empty()){
      list.insert(person);
      return;
    }
    list.findFirst();
    while(true){
      if(person.getName().compareTo(list.retrieve().getName()) < 0){
        T tmp = list.retrieve();
        list.update(person);
        list.insert(tmp);
        return;
      }
      if(list.last()){
        list.insert(person);
        return;
      }
      list.findNext();
    }
  }

  // Copy list nodes so the returned list can be changed separately.
  private <T> LinkedList<T> copy(LinkedList<T> list){
    LinkedList<T> result = new LinkedList<T>();
    if(list.empty())
      return result;
    list.findFirst();
    while(!list.last()){
      result.insert(list.retrieve());
      list.findNext();
    }
    result.insert(list.retrieve());
    return result;
  }

  // Remove the person from the alphabetical list.
  private <T extends IPerson> void removeName(LinkedList<T> list, int id){
    if(list.empty())
      return;
    list.findFirst();
    while(true){
      if(list.retrieve().getId() == id){
        list.remove();
        return;
      }
      if(list.last())
        return;
      list.findNext();
    }
  }

  public IRider searchRiderById(int riderId){
    return riders.findById(riderId);
  }

  public IRider searchRiderByEmail(String email){
    return riders.findByEmail(email);
  }

  // Match the complete name, not only the first name.
  public LinkedList<IRider> searchRidersByName(String fullName){
    return riders.findByName(fullName);
  }

  public LinkedList<IRider> searchRidersByHomeCity(String homeCity){
    return riders.findByHomeCity(homeCity);
  }

  // Return riders alphabetically by name.
  public LinkedList<IRider> getAllRiders(){
    return copy(riderNames);
  }

  public IDriver searchDriverById(int driverId){
    return drivers.findById(driverId);
  }

  public IDriver searchDriverByVehiclePlate(String vehiclePlate){
    return drivers.findByVehiclePlate(vehiclePlate);
  }

  public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType){
    return drivers.findByVehicleType(vehicleType);
  }

  // Return drivers alphabetically by name.
  public LinkedList<IDriver> getAllDrivers(){
    return copy(driverNames);
  }

  public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation){
    return rides.findByPickupLocation(pickupLocation);
  }

  public LinkedList<IRide> searchRidesByRiderName(String riderName){
    return rides.findByRiderName(riderName);
  }

  public LinkedList<IRide> getAllRidesAlphabetically(){
    return rides.getAllAlphabetically();
  }

  // Collect participants from shared rides with this pickup location.
  public LinkedList<IRider> getSharedRideParticipants(String pickupLocation){
    RiderList result = new RiderList();
    LinkedList<IRide> matches = rides.findByPickupLocation(pickupLocation);
    if(matches.empty())
      return result.getAll();
    matches.findFirst();
    while(true){
      if(matches.retrieve() instanceof ISharedRide){
        LinkedList<IRider> people = ((ISharedRide) matches.retrieve()).getParticipants();
        if(!people.empty()){
          people.findFirst();
          while(true){
            result.add(people.retrieve());
            if(people.last())
              break;
            people.findNext();
          }
        }
      }
      if(matches.last())
        break;
      matches.findNext();
    }
    // If several shared rides have this pickup, include each rider only once.
    return result.getAll();
  }

  // The supplied DateTime checks ranges; this also rejects impossible calendar dates.
  private static boolean validDate(IDateTime time){
    if(time == null || time.getYear() < 1 || time.getMonth() < 1 || time.getMonth() > 12
        || time.getHour() < 0 || time.getHour() > 23 || time.getMinute() < 0 || time.getMinute() > 59)
      return false;
    int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
    int year = time.getYear();
    if(year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
      days[1] = 29;
    return time.getDay() > 0 && time.getDay() <= days[time.getMonth() - 1];
  }

  // Used by both the menu and CSV loaders, so input has one consistent format.
  static IDateTime parseDate(String text){
    if(text == null || !text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}"))
      throw new IllegalArgumentException("Use MM/DD/YYYY HH:MM.");
    IDateTime time = new DateTime(Integer.parseInt(text.substring(6,10)),
        Integer.parseInt(text.substring(0,2)), Integer.parseInt(text.substring(3,5)),
        Integer.parseInt(text.substring(11,13)), Integer.parseInt(text.substring(14,16)));
    if(!validDate(time))
      throw new IllegalArgumentException("Invalid calendar date.");
    return time;
  }

  // Split semicolon-separated rider IDs into an integer array.
  static int[] parseRiderIds(String text){
    String[] parts = text.split(";", -1);
    int[] ids = new int[parts.length];
    for(int i = 0; i < parts.length; i++)
      ids[i] = Integer.parseInt(parts[i].trim());
    return ids;
  }

  // Check this person for overlapping rides.
  private boolean available(IPerson person, IDateTime start, IDateTime end){
    LinkedList<IRide> history = person.getRideHistory();
    if(history.empty())
      return true;
    history.findFirst();
    while(true){
      IRide ride = history.retrieve();
      // Strict comparisons allow a ride to start when another one ends.
      if(start.compareTo(ride.getDropoffTime()) < 0 && ride.getPickupTime().compareTo(end) < 0)
        return false;
      if(history.last())
        return true;
      history.findNext();
    }
  }

  // Append the scheduled ride to the person's history.
  private void addHistory(IPerson person, IRide ride){
    LinkedList<IRide> history = person.getRideHistory();
    if(!history.empty()){
      history.findFirst();
      while(!history.last())
        history.findNext();
    }
    history.insert(ride);
  }

  // Schedule one rider with one driver.
  public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime,
      IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId){
    return schedule(pickupLocation, pickupTime, dropoffTime, dropoffLocation,
        new int[]{riderId}, driverId, false);
  }

  // Schedule several different riders with one driver.
  public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime,
      IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId){
    return schedule(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIds, driverId, true);
  }

  // Check every participant before changing the ride list or any history.
  private boolean schedule(String pickup, IDateTime start, IDateTime end, String dropoff,
      int[] ids, int driverId, boolean shared){
    if(!hasText(pickup) || !hasText(dropoff) || !validDate(start) || !validDate(end)
        || start.compareTo(end) >= 0 || ids == null || ids.length == 0
        || (shared && ids.length < 2) || (!shared && ids.length != 1))
      return false;
    IDriver driver = drivers.findById(driverId);
    if(driver == null || !available(driver, start, end))
      return false;
    IRider[] people = new IRider[ids.length];
    for(int i = 0; i < ids.length; i++){
      people[i] = riders.findById(ids[i]);
      if(people[i] == null || !available(people[i], start, end))
        return false;
      for(int j = 0; j < i; j++){
        if(ids[i] == ids[j])
          return false;
      }
    }
    start = new DateTime(start.getYear(), start.getMonth(), start.getDay(), start.getHour(), start.getMinute());
    end = new DateTime(end.getYear(), end.getMonth(), end.getDay(), end.getHour(), end.getMinute());
    IRide ride;
    if(shared){
      SharedRide sharedRide = new SharedRide(nextRideId, pickup, start, end, dropoff, driver);
      for(int i = 0; i < people.length; i++)
        sharedRide.addParticipant(people[i]);
      ride = sharedRide;
    }
    else
      ride = new PrivateRide(nextRideId, pickup, start, end, dropoff, driver, people[0]);
    if(!rides.addRide(ride))
      return false;
    nextRideId++;
    addHistory(driver, ride);
    for(int i = 0; i < people.length; i++)
      addHistory(people[i], ride);
    return true;
  }

  // Remove one ride from the person's history.
  private void removeHistory(IPerson person, int rideId){
    LinkedList<IRide> history = person.getRideHistory();
    if(history.empty())
      return;
    history.findFirst();
    while(true){
      if(history.retrieve().getRideId() == rideId){
        history.remove();
        return;
      }
      if(history.last())
        return;
      history.findNext();
    }
  }

  // Delete the ride and clean up every associated history.
  private void deleteRide(IRide ride){
    rides.removeRideById(ride.getRideId());
    removeHistory(ride.getDriver(), ride.getRideId());
    if(ride instanceof IPrivateRide)
      removeHistory(((IPrivateRide) ride).getRider(), ride.getRideId());
    else{
      LinkedList<IRider> people = ((ISharedRide) ride).getParticipants();
      if(!people.empty()){
        people.findFirst();
        while(true){
          removeHistory(people.retrieve(), ride.getRideId());
          if(people.last())
            break;
          people.findNext();
        }
      }
    }
  }

  // Delete private rides and remove this rider from shared rides.
  public boolean removeRider(int riderId){
    IRider rider = riders.findById(riderId);
    if(rider == null)
      return false;
    LinkedList<IRide> all = rides.getAllAlphabetically();
    if(!all.empty()){
      all.findFirst();
      while(true){
        IRide ride = all.retrieve();
        if(ride.hasRider(riderId)){
          if(ride instanceof IPrivateRide)
            deleteRide(ride);
          else{
            ISharedRide shared = (ISharedRide) ride;
            shared.removeParticipantById(riderId);
            removeHistory(rider, ride.getRideId());
            if(shared.isEmpty())
              deleteRide(ride);
          }
        }
        if(all.last())
          break;
        all.findNext();
      }
    }
    removeName(riderNames, riderId);
    return riders.removeById(riderId);
  }

  // Delete every ride assigned to this driver.
  public boolean removeDriver(int driverId){
    if(drivers.findById(driverId) == null)
      return false;
    LinkedList<IRide> all = rides.getAllAlphabetically();
    if(!all.empty()){
      all.findFirst();
      while(true){
        if(all.retrieve().getDriver().getId() == driverId)
          deleteRide(all.retrieve());
        if(all.last())
          break;
        all.findNext();
      }
    }
    removeName(driverNames, driverId);
    return drivers.removeById(driverId);
  }

  // Load rider records using the assignment's CSV column order.
  public boolean loadRidersFromCSV(String filePath){
    return loadFile(filePath, 1);
  }

  // Load driver records and enforce uniqueness.
  public boolean loadDriversFromCSV(String filePath){
    return loadFile(filePath, 2);
  }

  // Load rides through the same scheduling checks used by the menu.
  public boolean loadRidesFromCSV(String filePath){
    return loadFile(filePath, 3);
  }

  // Remember new records so a failed file can be rolled back completely.
  private boolean loadFile(String path, int kind){
    if(!hasText(path))
      return false;
    LinkedList<Integer> added = new LinkedList<Integer>();
    int firstRideId = nextRideId;
    try(BufferedReader file = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)){
      String line;
      boolean first = true;
      while((line = file.readLine()) != null){
        if(first && line.startsWith("\uFEFF"))
          line = line.substring(1);
        if(line.trim().isEmpty())
          continue;
        String[] row = csvRow(line);
        if(first && isHeader(row, kind)){
          first = false;
          continue;
        }
        first = false;
        if(row.length != (kind == 3 ? 7 : 5))
          throw new IllegalArgumentException();
        if(kind == 1){
          int id = Integer.parseInt(row[0]);
          if(!addRider(new Rider(id, row[1], row[3], row[2], row[4])))
            throw new IllegalArgumentException();
          added.insert(id);
        }
        else if(kind == 2){
          int id = Integer.parseInt(row[0]);
          if(!addDriver(new Driver(id, row[1], row[2], row[3], VehicleType.valueOf(row[4]))))
            throw new IllegalArgumentException();
          added.insert(id);
        }
        else{
          if(!row[0].equals("PRIVATE") && !row[0].equals("SHARED"))
            throw new IllegalArgumentException();
          if(!schedule(row[1], parseDate(row[2]), parseDate(row[3]), row[4],
              parseRiderIds(row[6]), Integer.parseInt(row[5]), row[0].equals("SHARED")))
            throw new IllegalArgumentException();
        }
      }
      return true;
    }
    catch(IOException | IllegalArgumentException e){
      if(kind == 3){
        LinkedList<IRide> all = rides.getAllAlphabetically();
        if(!all.empty()){
          all.findFirst();
          while(true){
            if(all.retrieve().getRideId() >= firstRideId)
              deleteRide(all.retrieve());
            if(all.last())
              break;
            all.findNext();
          }
        }
        nextRideId = firstRideId;
      }
      else if(!added.empty()){
        added.findFirst();
        while(true){
          if(kind == 1)
            removeRider(added.retrieve());
          else
            removeDriver(added.retrieve());
          if(added.last())
            break;
          added.findNext();
        }
      }
      return false;
    }
  }

  // Recognize an optional header with the expected column names.
  private boolean isHeader(String[] row, int kind){
    String[] expected;
    if(kind == 1)
      expected = new String[]{"riderId","name","email","phoneNumber","homeCity"};
    else if(kind == 2)
      expected = new String[]{"driverId","name","phoneNumber","vehiclePlate","vehicleType"};
    else
      expected = new String[]{"rideType","pickupLocation","pickupDateTime","dropoffDateTime","dropoffLocation","driverId","riderIds"};
    if(row.length != expected.length)
      return false;
    for(int i = 0; i < row.length; i++){
      if(!row[i].equalsIgnoreCase(expected[i]))
        return false;
    }
    return true;
  }

  // One record per line, as specified. Quotes may contain commas or doubled quotes.
  private String[] csvRow(String line){
    String[] fields = new String[7];
    int count = 0;
    StringBuilder value = new StringBuilder();
    boolean quoted = false;
    boolean closed = false;
    for(int i = 0; i < line.length(); i++){
      char ch = line.charAt(i);
      if(quoted){
        if(ch == '"'){
          if(i + 1 < line.length() && line.charAt(i + 1) == '"'){
            value.append('"');
            i++;
          }
          else{
            quoted = false;
            closed = true;
          }
        }
        else
          value.append(ch);
      }
      else if(ch == ','){
        if(count == fields.length)
          throw new IllegalArgumentException();
        fields[count++] = value.toString().trim();
        value.setLength(0);
        closed = false;
      }
      else if(ch == '"'){
        if(closed || !value.toString().trim().isEmpty())
          throw new IllegalArgumentException();
        value.setLength(0);
        quoted = true;
      }
      else{
        if(closed && !Character.isWhitespace(ch))
          throw new IllegalArgumentException();
        value.append(ch);
      }
    }
    if(quoted || count == fields.length)
      throw new IllegalArgumentException();
    fields[count++] = value.toString().trim();
    String[] result = new String[count];
    for(int i = 0; i < count; i++)
      result[i] = fields[i];
    return result;
  }
}
