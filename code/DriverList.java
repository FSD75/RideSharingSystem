public class DriverList implements IDriverList{

  private LinkedList<IDriver> drivers;
  private int count;

  public DriverList(){
    drivers = new LinkedList<IDriver>();
    count = 0;
  }

  public boolean add(IDriver driver){

  }

  public IDriver findById(int driverId){

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
