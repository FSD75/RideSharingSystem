public class DriverList implements IDriverList{ // Class DriverList implementing IDriverList.
  // IDriverList's attributes.
  private LinkedList<IDriver> drivers;
  private int count;

  public DriverList(){ // Initializing an empty dirver list.
    drivers = new LinkedList<IDriver>();
    count = 0;
  }

  public boolean add(IDriver driver){ // Adds a driver to the list if possible.
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
      
      if(drivers.last()){
        drivers.insert(driver);
        count++;
        return true;
      }
        
      drivers.findNext();
   }
    
    return false;
  }

  
  public IDriver findById(int driverId){ // Finding a driver by ID.
    if(drivers.empty())
      return null;

    drivers.findFirst();
    while(drivers.retrieve() != null){
      if(drivers.retrieve().getId() == driverId){
        return drivers.retrieve();
      }
      if(drivers.last())
        return null;
      
      drivers.findNext();
    }
    return null;
  }

  public LinkedList<IDriver> findByName(String fullName){ // Returns all drivers whose full names match exactly.
    LinkedList<IDriver> nameList = new LinkedList<IDriver>();
    if(drivers.empty() || fullName == null)
      return nameList;

    drivers.findFirst();

    while(!drivers.last()){
      if(fullName.equals(drivers.retrieve().getName())){
        nameList.insert(drivers.retrieve());
      }
    drivers.findNext();
    }
    
    if(fullName.equals(drivers.retrieve().getName()))
      nameList.insert(drivers.retrieve());

    return nameList;
  }

  public IDriver findByVehiclePlate(String vehiclePlate){ // Finds a driver by an exact vehicle plate match.
    if(drivers.empty() || vehiclePlate == null)
      return null;
    drivers.findFirst();

    while(!drivers.last()){
      if(drivers.retrieve().getVehiclePlate().equals(vehiclePlate)){
        return drivers.retrieve();
      }
      drivers.findNext();
    }
    if(drivers.retrieve().getVehiclePlate().equals(vehiclePlate)){
        return drivers.retrieve();
    }
    return null;
  }

  public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType){ // Returns all drivers with the requested vehicle type.
    LinkedList<IDriver> typeList = new LinkedList<IDriver>();
    if(drivers.empty())
      return typeList;

    drivers.findFirst();

    while(!drivers.last()){
      if(drivers.retrieve().getVehicleType() == vehicleType){
        typeList.insert(drivers.retrieve());
      }
      drivers.findNext();
    }
    if(drivers.retrieve().getVehicleType() == vehicleType)
      typeList.insert(drivers.retrieve());

    return typeList;
  }

  public LinkedList<IDriver> getAll(){ // Returns all drivers in the list.
    return drivers;
  }

  public boolean removeById(int driverId){ // Removes a driver by ID.
    if(findById(driverId) == null)
      return false;

    drivers.remove();
    count--;
    return true;
  }

  public boolean removeByVehiclePlate(String vehiclePlate){ // Removes the driver with the requested vehicle plate.
    if(findByVehiclePlate(vehiclePlate) == null)
      return false;

    drivers.remove();
    count--;
    return true;
  }

  public int removeByName(String fullName){ // Removes every driver whose full name matches exactly and returns the number of removed drivers.
    int num = 0;
    if(drivers.empty() || fullName == null)
      return num;

    drivers.findFirst();
    while(!drivers.last()){
      if(fullName.equals(drivers.retrieve().getName())){
        drivers.remove();
        count--;
        num++;
      }
      else
        drivers.findNext();
    }
    
    if(fullName.equals(drivers.retrieve().getName())){
        drivers.remove();
        count--;
        num++;
      }
    return num;
  }

  public int removeByVehicleType(VehicleType vehicleType){ // Removes every driver by vehicle type and returns the number of removed drivers.
    int num = 0;
    if(drivers.empty())
      return num;

    drivers.findFirst();
    while(!drivers.last()){
      if(drivers.retrieve().getVehicleType() == vehicleType){
        drivers.remove();
        count--;
        num++;
      }
      else
        drivers.findNext();
    }

     if(drivers.retrieve().getVehicleType() == vehicleType){
        drivers.remove();
        count--;
        num++;
     }
    return num;
  }

  public int size(){ // Returns the number of drivers in the list.
    return count;
  }



  
}
