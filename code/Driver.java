public class Driver extends Person implements IDriver{ // Class Driver inheriting from Person implementing IDriver.
  // Driver's attributes. 
  private String vehiclePlate; 
  private VehicleType vehicleType;

  public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType){ // The inherited constructor and initializing driver's info.
    super(id, name, phoneNumber);
    setVehiclePlate(vehiclePlate);
    this.vehicleType = vehicleType;
  }

  public String getVehiclePlate(){ // Returns the vehicle's plate number.
    return vehiclePlate;
  }

  public void setVehiclePlate(String vehiclePlate){
    if(vehiclePlate == null || !vehiclePlate.matches("[A-Z]{3}[0-9]{4}")) // A valid plate contains three uppercase letters followed by four digits.
       throw new IllegalArgumentException("The plate must have exactly 3 uppercase letters followed by 4 digits."); // Throws IllegalArgumentException if the plate is invalid.

    this.vehiclePlate = vehiclePlate;
  }

  public VehicleType getVehicleType(){ // Returns the vehicle's type.
    return vehicleType;
  }

  public void setVehicleType(VehicleType vehicleType){ // Updates vehicle's type.
    this.vehicleType = vehicleType;
  }

  @Override
  public int compareTo(IDriver other){ // Compares drivers by thier IDs.
    if(getId() > other.getId())
      return 1;

    else if(getId() < other.getId())
      return -1;

    else
      return 0;
  }

  @Override
  public String toString(){ // Printng driver's info + inherited personal information.
    return super.toString() + ", vehiclePlate: " + vehiclePlate + ", vehicleType: " + vehicleType;
  }






  
}
