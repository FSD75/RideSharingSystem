public class DriverList implements IDriverList{

  private LinkedList<IDriver> drivers;
  private int count;

  public DriverList(){
    drivers = new LinkedList<IDriver>();
    count = 0;
  }

  public boolean add(IDriver driver){
    if(driver == null || findByVehiclePlate(driver.getVehiclePlate()) != null)
      return false;

    if(drivers.empty()){
      drivers.insert(driver);
      count++;
      return true;
    }

    if(findById(driver.getId()) != null)
      return false;

    IDriver tmp = null;
    drivers.findFirst();
      
    while(drivers.retrieve() != null){
      if(drivers.retrieve().getId() > driver.getId()){
        tmp = drivers.retrieve();
        drivers.update(driver);
        drivers.insert(tmp);
        count++;
        return true;
      }
      
      drivers.findNext(); 
   }
    return false;
  }

  
  public IDriver findById(int driverId){
    if(drivers.empty())
      return null;

    drivers.findFirst();
    while(drivers.retrieve() != null){
      if(drivers.retrieve().getId() == driverId){
        return drivers.retrieve();
      }
      drivers.findNext();
    }
    return null;
  }

  public LinkedList<IDriver> findByName(String fullName){

  }

  public IDriver findByVehiclePlate(String vehiclePlate){

  }

  public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType){

  }

  public LinkedList<IDriver> getAll(){

  }

  public boolean removeById(int driverId){


  }

  public boolean removeByVehiclePlate(String vehiclePlate){

  }

  public int removeByName(String fullName){

  }

  public int removeByVehicleType(VehicleType vehicleType){

  }

  public int size(){
    return count;
  }

  
}
