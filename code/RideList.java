public class RideList implements IRideList {

	private LinkedList<IRide> rides;

	public RideList() {
		rides = new LinkedList<IRide>();
	}

	// add ride in sorted order
	public boolean addRide(IRide ride) {
		if (ride == null)
			return false;

		LinkedList<IRide> result = new LinkedList<IRide>();

		if (rides.empty()) {
			rides.insert(ride);
			return true;
		}

		rides.findFirst();

		while ((!rides.last()) && ride.compareTo(rides.retrieve()) >= 0) {
			result.insert(rides.retrieve());
			rides.findNext();
		}

		if (ride.compareTo(rides.retrieve()) < 0) {
			result.insert(ride);
			result.insert(rides.retrieve());
		} else {

			result.insert(rides.retrieve());
			result.insert(ride);
		}

		while (!rides.last()) {
			rides.findNext();
			result.insert(rides.retrieve());
		}

		rides = result;
		return true;
	}

	// Remove first ride by id
	public boolean removeRideById(int rideId) {
		if (rides.empty())
			return false;

		LinkedList<IRide> result = new LinkedList<IRide>();
		boolean removed = false;

		rides.findFirst();

		while (!rides.last()) {
			IRide current = rides.retrieve();

			if (!removed && current.getRideId() == rideId)
				removed = true;
			else
				result.insert(current);

			rides.findNext();
		}

		// last node
		IRide current = rides.retrieve();
		if (!removed && current.getRideId() == rideId)
			removed = true;
		else
			result.insert(current);

		rides = result;
		return removed;
	}

	// Return copy of all rides
	public LinkedList<IRide> getAllAlphabetically() {
		LinkedList<IRide> result = new LinkedList<IRide>();

		if (rides.empty())
			return result;

		rides.findFirst();

		while (!rides.last()) {
			result.insert(rides.retrieve());
			rides.findNext();
		}

		// Last node
		result.insert(rides.retrieve());
		return result;
	}

	public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
		LinkedList<IRide> result = new LinkedList<IRide>();

		if (rides.empty())
			return result;

		rides.findFirst();

		while (!rides.last()) {
			if (rides.retrieve().getPickupLocation().equals(pickupLocation))
				result.insert(rides.retrieve());
			rides.findNext();
		}

		// last node
		if (rides.retrieve().getPickupLocation().equals(pickupLocation))
			result.insert(rides.retrieve());

		return result;
	}

	public LinkedList<IRide> findByRiderName(String riderFullName) {
		LinkedList<IRide> result = new LinkedList<IRide>();

		if (rides.empty())
			return result;

		rides.findFirst();

		while (!rides.last()) {
			if (hasRiderName(rides.retrieve(), riderFullName))
				result.insert(rides.retrieve());
			rides.findNext();
		}

		// last node
		if (hasRiderName(rides.retrieve(), riderFullName))
			result.insert(rides.retrieve());

		return result;
	}

	// Check if the rider in private or shared ride
	private boolean hasRiderName(IRide ride, String name) {
		if (ride instanceof IPrivateRide) {
			IRider rider = ((IPrivateRide) ride).getRider();

			if (rider != null) {
				return rider.getName().equals(name);
			}

		}

		if (ride instanceof ISharedRide) {
			LinkedList<IRider> list = ((ISharedRide) ride).getParticipants();

			if (list.empty())
				return false;

			list.findFirst();

			while (!list.last()) {
				if (list.retrieve().getName().equals(name))
					return true;
				list.findNext();
			}

			// last participant
			return list.retrieve().getName().equals(name);
		}

		return false;
	}

	// Return size of ridelist
	public int size() {
		if (rides.empty())
			return 0;

		int count = 0;

		rides.findFirst();

		while (!rides.last()) {
			count++;
			rides.findNext();
		}

		count++;

		return count;
	}
}
