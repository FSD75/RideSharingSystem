public class Driver extends Person implements IDriver{
  private String vehiclePlate;
  private VehicleType vehicleType;

  Public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType){
    super(id, name, phoneNumber);
    this.vehiclePlate = vehiclePlate;
    this.vehicleType = vehicleType;
  }

  public String getVehiclePlate(){
    return vehiclePlate;
  }

  public void setVehiclePlate(String vehiclePlate){
    this.vehiclePlate = vehiclePlate;
  }

  public String getVehicleType(){
    return vehicleType;
  }

  public void setVehicleType(VehicleType vehicleType){
    this.vehicleType = vehicleType;
  }

  @override
  public int compareTo(IDriver other){
    

  }








}
