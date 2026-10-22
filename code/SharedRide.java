public class SharedRide extends Ride implements ISharedRide {

	private LinkedList<IRider> participants;

	public SharedRide(int rideId, String pickupLocation, IDateTime pickupTime, IDateTime dropoff, String dropoffLocation, IDriver driver) {

		super(rideId, pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);

		participants = new LinkedList<IRider>();
	}

	public boolean addParticipant(IRider rider) {
		if (rider == null || hasRider(rider.getId()))
			return false;

		if (participants.empty()) {
			participants.insert(rider);
			return true;
		}
		participants.findFirst();

		while (!participants.last())
			participants.findNext();

		participants.insert(rider);
		return true;
	}

	public LinkedList<IRider> getParticipants() {
		return participants;
	}

	public  boolean removeParticipantById(int riderId) {
		Node<IRider> temp = participants.head;
		Node<IRider> previous = null;

		while (temp != null) {
			if (temp.data.getId() == riderId) {
				if (previous == null)
					participants.head = temp.next;
				else
					previous.next = temp.next;

				if (participants.current == temp)
					participants.current = temp.next;

				return true;
			}

			previous = temp;
			temp = temp.next;
		}

		return false;
	}

	public 	boolean isEmpty() {
		return participants.empty();
	}

	public boolean hasRider(int riderId) {
		Node<IRider> temp = participants.head;

		while	 (temp != null) {
			if (temp.data.getId() == riderId)
				return true;

			temp = temp.next;
		}

		return false;
	}

	@Override
	public	 String toString() {
		String str = "Shared " + super.toString() + ", Participants: ";

		if (participants.empty())
			return str + "None";

		Node<IRider> temp = participants.head;

		while (temp != null) {
			str += temp.data.getName();

			if (temp.next != null)
				str += ", ";

			temp = temp.next;
		}

		return str;
	}
}
