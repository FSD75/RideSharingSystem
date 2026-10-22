public class RideList implements IRideList{

    private LinkedList<IRide> rides;

	public RideList() {
		rides = new LinkedList<IRide>();
		
	}
	
	public  boolean addRide(IRide ride) {
		if( ride == null)
			return false;
		
		Node<IRide> node = new Node<IRide>(ride);
		
		if(rides.head == null || ride.compareTo(rides.head.data) <0) {
		node.next = rides.head;
		rides.head=node;
		return true;

	}

		Node<IRide> temp = rides.head;
		
		while(temp.next != null && ride.compareTo(temp.next.data) >=0) 
			temp = temp.next;
			
		node.next=temp.next;
		temp.next=node;
		
		return true;
		
		
	}
		
	 public   boolean removeRideById(int rideId) {
		 
		 Node<IRide> temp = rides.head;
	        Node<IRide> previous = null;

	        while (temp != null) {
	            if (temp.data.getRideId() == rideId) {
		        	//remove first ride
	            	if (previous == null)
	                    rides.head = temp.next;
	              
	            	else
	                    previous.next = temp.next;

	                return true;
	            }

	            previous = temp;
	            temp = temp.next;
	        }

	        return false;
	    }

	 
	 

	    public LinkedList<IRide> getAllAlphabetically(){
	    	
	    	 LinkedList<IRide> result = new LinkedList<IRide>();
	         Node<IRide> temp = rides.head;

	         while (temp != null) {
	        	 addhelper(result, temp.data);
	             temp = temp.next;
	         }

	         return result;
	     }

	    	
	    

	    public    LinkedList<IRide> findByPickupLocation(String pickupLocation){
	    	
	    	LinkedList<IRide> result = new LinkedList<IRide>();
	        Node<IRide> temp = rides.head;

	        while (temp != null) {
	            if (temp.data.getPickupLocation().equals(pickupLocation))
	            	addhelper(result, temp.data);

	            temp = temp.next;
	        }

	        return result;
	    	
	    }
	    	

	    public LinkedList<IRide> findByRiderName(String riderFullName){
	    
	    	  LinkedList<IRide> result = new LinkedList<IRide>();
	          Node<IRide> temp = rides.head;

	          while (temp != null) {
	              IRide ride = temp.data;
	              boolean found = false;

	              if (ride instanceof IPrivateRide) {
	                  IRider rider = ((IPrivateRide) ride).getRider();

	                  if (rider != null &&
	                      rider.getName().equals(riderFullName))
	                      found = true;
	              } else if (ride instanceof ISharedRide) {
	                  LinkedList<IRider> participants =
	                          ((ISharedRide) ride).getParticipants();

	                  Node<IRider> p = participants.head;

	                  while (p != null) {
	                      if (p.data.getName().equals(riderFullName)) {
	                          found = true;
	                          break;
	                      }
	                      p = p.next;
	                  }
	              }

	              if (found)
	            	  addhelper(result, ride);

	              temp = temp.next;
	          }

	          return result;
	    }

	    
	    
	    
	    
	    
	   public int size() {
		   int count = 0;
	        Node<IRide> temp = rides.head;

	        while (temp != null) {
	            count++;
	            temp = temp.next;
	        }
	        return count;
	   
	   }
	   
	    private void addhelper(LinkedList<IRide> list, IRide ride) {
	    	if( list.empty()) {
	    		list.insert(ride);
	    	return;
	    	}
	    	
	    	list.findFirst();
	    	
	    	while(!list.last()) 
		    	list.findNext();
    		
	    	list.insert(ride);

	    }

}
