public class RiderList implements IRiderList{

  private LinkedList<IRider> riders;
  private int count;

  public RiderList(){
    riders = new LinkedList<IRider>();
    count = 0;
  }

  public boolean add(IRider rider){
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
  

  public IRider findById(int riderId){
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

  public LinkedList<IRider> findByName(String fullName){
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

  public IRider findByEmail(String email){
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

  public LinkedList<IRider> findByHomeCity(String homeCity){
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

  public LinkedList<IRider> getAll(){
    return riders;
  }

  public boolean removeById(int riderId){
    if(findById(riderId) == null)
      return false;

    riders.remove();
    count--;
    return true;
  }

  public boolean removeByEmail(String email){
    if(findByEmail(email) == null)
      return false;

    riders.remove();
    count--;
    return true;
  }

  public int removeByName(String fullName){
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

  public int removeByHomeCity(String homeCity){
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

  public int size(){
    return count;
  }


  
}
