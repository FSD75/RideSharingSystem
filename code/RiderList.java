public class RiderList implements IRiderList{ // Class RiderList implementing IRiderList.
  // IDriverList's attributes.
  private LinkedList<IRider> riders;
  private int count;

  public RiderList(){ // Initializing an empty rider list.
    riders = new LinkedList<IRider>();
    count = 0;
  }

  public boolean add(IRider rider){ // Adds a rider to the list if possible.
    if(rider == null || findByEmail(rider.getEmail()) != null)
     return false;
    
    if(riders.empty()){
      riders.insert(rider);
      count++;
      return true;
    }

    if(findById(rider.getId()) != null)
      return false;
    
    IRider tmp = null;
    riders.findFirst();

    while(riders.retrieve() != null){
      if(riders.retrieve().getId() > rider.getId()){
        tmp = riders.retrieve();
        riders.update(rider);
        riders.insert(tmp);
        count++;
        return true;
      }

      if(riders.last()){
        riders.insert(rider);
        count++;
        return true;
      }

      riders.findNext();
    }

    return false;
  }
  

  public IRider findById(int riderId){ // Finding a rider by ID.
    if(riders.empty())
      return null;
    
    riders.findFirst();
    
    while(!riders.last()){
      if(riders.retrieve().getId() == riderId){
        return riders.retrieve();
      }
      riders.findNext();
    }

    if(riders.retrieve().getId() == riderId)
        return riders.retrieve();
      
    return null;
  }

  public LinkedList<IRider> findByName(String fullName){ // Returns all riders whose full names match exactly.
    LinkedList<IRider> nameList = new LinkedList<IRider>();
    
    if(riders.empty() || fullName == null)
      return nameList;
    
    riders.findFirst();

    while(!riders.last()){
      if(fullName.equals(riders.retrieve().getName())){
        nameList.insert(riders.retrieve());
      }
      riders.findNext();
    }

    if(fullName.equals(riders.retrieve().getName()))
        nameList.insert(riders.retrieve());

    return nameList;
  }

  public IRider findByEmail(String email){ // Finds a rider by an exact email match.
    if(riders.empty() || email == null)
      return null;

    riders.findFirst();
    
    while(!riders.last()){
      if(email.equals(riders.retrieve().getEmail())){
        return riders.retrieve();
      }
      riders.findNext();
    }

    if(email.equals(riders.retrieve().getEmail()))
        return riders.retrieve();

    return null;
  }

  public LinkedList<IRider> findByHomeCity(String homeCity){ // Returns all riders with the requested home city.
    LinkedList<IRider> cityList = new LinkedList<IRider>();
    
    if(riders.empty() || homeCity == null)
      return cityList;
    
    riders.findFirst();
    
    while(!riders.last()){
      if(homeCity.equals(riders.retrieve().getHomeCity())){
        cityList.insert(riders.retrieve());
      }
      riders.findNext();
    }

    if(homeCity.equals(riders.retrieve().getHomeCity()))
        cityList.insert(riders.retrieve());
      
    return cityList;
  }

  public LinkedList<IRider> getAll(){ // Returns all riders in the list.
    return riders;
  }

  public boolean removeById(int riderId){
    if(findById(riderId) == null)
      return false;

    riders.remove();
    count--;
    return true;
  }

  public boolean removeByEmail(String email){ // Removes the rider with the requested email.
    if(findByEmail(email) == null)
      return false;

    riders.remove();
    count--;
    return true;
  }

  public int removeByName(String fullName){ // Removes every rider whose full name matches exactly and returns the number of removed riders.
    int num = 0;
    if(riders.empty() || fullName == null)
      return num;

    riders.findFirst();
    
    while(!riders.last()){
      if(fullName.equals(riders.retrieve().getName())){
        riders.remove();
        count--;
        num++;
      }
      else{
        riders.findNext();
      }
    }

     if(fullName.equals(riders.retrieve().getName())){
        riders.remove();
        count--;
        num++;
      }
    
    return num;
  }

  public int removeByHomeCity(String homeCity){ // Removes every rider by home city and returns the number of removed riders.
    int num = 0;
    if(riders.empty() || homeCity == null)
      return num;

    riders.findFirst();
    
    while(!riders.last()){
      if(homeCity.equals(riders.retrieve().getHomeCity())){
        riders.remove();
        count--;
        num++;
      }
      else{
        riders.findNext();
      }
    }

    if(homeCity.equals(riders.retrieve().getHomeCity())){
        riders.remove();
        count--;
        num++;
      }
    
    return num;
  }

  public int size(){ // Returns  the number of riders in the list.
    return count;
  }


  
}
