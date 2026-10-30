import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class Main{

  private RideSharingSystem system;
  private BufferedReader input;

  // Create the system and prepare keyboard input.
  public Main(){
    system = new RideSharingSystem();
    input = new BufferedReader(new InputStreamReader(System.in));
  }

  public static void main(String[] args){
    new Main().run();
  }

  private void run(){
    // People load first because the ride records refer to their IDs.
    showLoad(system.loadRidersFromCSV("riders_100.csv"), "riders_100.csv");
    showLoad(system.loadDriversFromCSV("drivers_30.csv"), "drivers_30.csv");
    showLoad(system.loadRidesFromCSV("rides_40.csv"), "rides_40.csv");
    while(true){
      menu();
      try{
        int choice = Integer.parseInt(read("Choose an option: "));
        switch(choice){
          case 1:
            print(system.getAllRiders());
            break;
          case 2:
            print(system.searchRidersByHomeCity(read("Home city: ")));
            break;
          case 3:
            print(system.searchRidersByName(read("Full name: ")));
            break;
          case 4:
            addRider();
            break;
          case 5:
            print(system.getAllDrivers());
            break;
          case 6:
            print(system.searchDriversByVehicleType(vehicleType()));
            break;
          case 7:
            addDriver();
            break;
          case 8:
            print(system.getAllRidesAlphabetically());
            break;
          case 9:
            print(system.searchRidesByPickupLocation(read("Pickup location: ")));
            break;
          case 10:
            print(system.searchRidesByRiderName(read("Rider full name: ")));
            break;
          case 11:
            print(system.getSharedRideParticipants(read("Pickup location: ")));
            break;
          case 12:
            addRide();
            break;
          case 0:
            System.out.println("Goodbye.");
            return;
          default:
            System.out.println("Choose a number from 0 to 12.");
        }
      }
      catch(EOFException e){
        System.out.println("Input ended. Goodbye.");
        return;
      }
      catch(IllegalArgumentException e){
        System.out.println("Operation failed. Check your input: " + e.getMessage());
      }
      catch(IOException e){
        System.out.println("Could not read input.");
        return;
      }
    }
  }

  // Display the required menu options.
  private void menu(){
    System.out.println();
    System.out.println("1. List all riders.");
    System.out.println("2. Search for riders by home city.");
    System.out.println("3. Search for riders by name.");
    System.out.println("4. Add a rider.");
    System.out.println("5. List all drivers.");
    System.out.println("6. Search for drivers by vehicle type.");
    System.out.println("7. Add a driver.");
    System.out.println("8. List all rides alphabetically by pickup location.");
    System.out.println("9. Search for rides by pickup location.");
    System.out.println("10. Search for rides by rider name.");
    System.out.println("11. List the participants of a shared ride by pickup location.");
    System.out.println("12. Add a ride (ask the user whether it is private or shared, then prompt accordingly).");
    System.out.println("0. Exit the program.");
  }

  // Show whether a CSV file loaded successfully.
  private void showLoad(boolean loaded, String file){
    if(loaded)
      System.out.println("Loaded " + file + ".");
    else
      System.out.println("Could not load " + file + ". Check the file and its records.");
  }

  // Read one value and reject empty input.
  private String read(String prompt) throws IOException{
    System.out.print(prompt);
    String value = input.readLine();
    if(value == null)
      throw new EOFException();
    if(value.trim().isEmpty())
      throw new IllegalArgumentException("A value is required.");
    return value.trim();
  }

  // Convert the entered vehicle type to an enum.
  private VehicleType vehicleType() throws IOException{
    return VehicleType.valueOf(read("Vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ").toUpperCase(Locale.ROOT));
  }

  // Read the rider details and try to add the rider.
  private void addRider() throws IOException{
    int id = Integer.parseInt(read("Rider ID: "));
    String name = read("Full name: ");
    String email = read("Email: ");
    String phone = read("Phone number (10 digits): ");
    String city = read("Home city: ");
    boolean added = system.addRider(new Rider(id, name, phone, email, city));
    System.out.println(added ? "Rider added." : "Rider was not added. Check for duplicate ID or email.");
  }

  // Read the driver details and try to add the driver.
  private void addDriver() throws IOException{
    int id = Integer.parseInt(read("Driver ID: "));
    String name = read("Full name: ");
    String phone = read("Phone number (10 digits): ");
    String plate = read("Vehicle plate: ");
    VehicleType type = vehicleType();
    boolean added = system.addDriver(new Driver(id, name, phone, plate, type));
    System.out.println(added ? "Driver added." : "Driver was not added. Check for duplicate ID or plate.");
  }

  // Read the ride details and call the matching scheduling method.
  private void addRide() throws IOException{
    String type = read("Ride type (PRIVATE or SHARED): ").toUpperCase(Locale.ROOT);
    if(!type.equals("PRIVATE") && !type.equals("SHARED"))
      throw new IllegalArgumentException("Use PRIVATE or SHARED.");
    String pickup = read("Pickup location: ");
    IDateTime start = RideSharingSystem.parseDate(read("Pickup time (MM/DD/YYYY HH:MM): "));
    IDateTime end = RideSharingSystem.parseDate(read("Drop-off time (MM/DD/YYYY HH:MM): "));
    String dropoff = read("Drop-off location: ");
    int driverId = Integer.parseInt(read("Driver ID: "));
    boolean added;
    if(type.equals("PRIVATE")){
      int riderId = Integer.parseInt(read("Rider ID: "));
      added = system.schedulePrivateRide(pickup, start, end, dropoff, riderId, driverId);
    }
    else{
      int[] ids = RideSharingSystem.parseRiderIds(read("Rider IDs (at least two, separated by semicolons): "));
      added = system.scheduleSharedRide(pickup, start, end, dropoff, ids, driverId);
    }
    System.out.println(added ? "Ride scheduled." : "Ride was not scheduled. Check people, times and conflicts.");
  }

  // Print each result, or a message if the list is empty.
  private <T> void print(LinkedList<T> list){
    if(list.empty()){
      System.out.println("No results.");
      return;
    }
    list.findFirst();
    while(!list.last()){
      System.out.println(list.retrieve().toString());
      list.findNext();
    }
    System.out.println(list.retrieve().toString());
  }
}
