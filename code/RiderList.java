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
    
    Node<IRider> tmp = null;
    riders.findFirst();

    while(riders.current != null && riders.cuurent.data.compareTo(rider) == -1){
       tmp = riders.current;
       riders.findNext();
     }

    if(riders.current != null && riders.current.data.compareTo(rider) == 0)
     return false;

    if(tmp == null){
     tmp = riders.current;
     riders.insert(rider);
     tmp.next = riders.current.next;
     riders.current.next = riders.head;
     riders.head = riders.current;
   }
     
    else{
     riders.current = tmp;
     riders.insert(rider);
   }
    
    count++;
    return true;
}

  public IRider findById(int riderId){
    if(riders.empty())
      return null;
    
    riders.findFirst();
    while(riders.current != null){
      if(riders.current.data.getId() == riderId){
        return riders.current.data;
      }
      riders.findNext();
    }
    return null;
  }

  public LinkedList<IRider> findByName(String fullName){
    LinkedList<IRider> nameList = new LinkedList<IRider>();
    
    if(riders.empty() || fullName == null)
      return nameList;
    
    riders.findFirst();

    while(riders.current != null){
      if(fulName.equals(riders.current.data.getName())){
        nameList.insert(riders.current.data);
      }
      riders.findNext();
    }

    return nameList;
  }

  public IRider findByEmail(String email){
    if(riders.empty() || email == null)
      return null;

    riders.findFirst();
    
    while(riders.current != null){
      if(email.equals(riders.current.data.getEmail())){
        return riders.current.data;
      }
      riders.findNext();
    }
    return null;
  }

  public LinkedList<IRider> findByHomeCity(Strimg homeCity){
    LinkedList<IRider> cityList = new LinkedList<IRider>();
    
    if(riders.empty() || homeCity == null)
      return cityList;
    
    riders.findFirst();
    
    while(riders.current != null){
      if(homeCity.equals(riders.current.data.getHomeCity())){
        cityList.insert(riders.current.data);
      }
      riders.findNext();
    }
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

    boolean isRemoved = false;
    riders.findFirst();
    
    while(riders.current != null){
      if(fullName.equals(riders.current.data.getName())){
        riders.remove();
        isRemoved = true;
        count--;
        num++;
      }
      if(!isRemoved)
        riders.findNext();
      isRemoved = false;
    }
    return num;
  }

  public int removeByHomeCity(String homeCity){
    int num = 0;
    if(riders.empty() || homeCity == null)
      return num;

    boolean isRemoved = false;
    riders.findFirst();
    
    while(riders.current != null){
      if(homeCity.equals(riders.current.data.getHomeCity())){
        riders.remove();
        isRemoved = true;
        count--;
        num++;
      }
      if(!isRemoved)
        riders.findNext();
      isRemoved = false;
    }
    return num;
  }

  public int size(){
    return count;
  }


  
}
