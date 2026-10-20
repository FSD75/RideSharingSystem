public class Driver extends Person implements IDriver{
  private String vehiclePlate;
  private VehicleType vehicleType;

  public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType){
    super(id, name, phoneNumber);
    setVehiclePlate(vehiclePlate);
    this.vehicleType = vehicleType;
  }

  public String getVehiclePlate(){
    return vehiclePlate;
  }

  public void setVehiclePlate(String vehiclePlate){
    if(vehiclePlate == null || !vehiclePlate.matches("[A-Z]{3}[0-9]{4}"))
       throw new IllegalArgumentException("The plate must have exactly 3 uppercase letters followed by 4 digits.");

    this.vehiclePlate = vehiclePlate;
  }

  public VehicleType getVehicleType(){
    return vehicleType;
  }

  public void setVehicleType(VehicleType vehicleType){
    this.vehicleType = vehicleType;
  }

  @Override
  public int compareTo(IDriver other){
    if(getID() > other.getID())
      return 1;

    else if(getID() < other.getID())
      return -1;

    else
      return 0;
  }

  @Override
  public String toString(){
    return super.toString() + ", vehiclePlate: " + vehiclePlate + ", vehicleType: " + vehicleType;
  }






  
}
