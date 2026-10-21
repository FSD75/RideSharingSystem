public class RiderList implements IRiderList{

  private LinkedList<IRider> riders;
  private int count;

  public RiderList(){
    riders = new LinkedList<IRider>();
    count = 0;
  }

  public boolean add(IRider rider){
    if(riders.empty()){
      riders.insert(rider);
      return true;
    }
    Node<IRider> tmp = riders.head;
    riders.findFirst();
    while(riders.current != null){
      if(riders.current.data.compareTo(rider) == 0){
        return false;
      }
      if(riders.current.data.compareTo(rider) == 1){
        riders.findNext();
        if(riders.current.next.data.compareTo(rider) == 1){
          tmp = tmp.next;
        }
      }
      if(riders.current.data.compareTo(rider) == -1){
        riders.current = tmp;
        riders.insert(rider);
        return true;
      }
    }
  }

      
}
