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
      riders.findFirst();
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

  }

  public LinkedList<IRider> findByName(String fullName){

  }

  public IRider findByEmail(String email){

  }

  public LinkedList<IRider> findByHomeCity(Strimg homeCity){

  }

  public LinkedList<IRider> getAll(){

  }

  public boolean removeById(int riderId){

  }

  public boolean removeByEmail(String email){

  }

  public int removebyName(String fullName){

  }

  public int removeByHomeCity(String homeCity){

  }

  public int size(){
    return count;
  }


  
}
