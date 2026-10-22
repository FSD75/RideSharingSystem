public class RideList implements IRideList {

    private LinkedList<IRide> rides;

    public RideList() {
        rides = new LinkedList<IRide>();
    }

    public boolean addRide(IRide ride) {
        if (ride == null)
            return false;

        LinkedList<IRide> result = new LinkedList<IRide>();

        if (rides.empty()) {
            result.insert(ride);
            rides = result;
            return true;
        }

        rides.findFirst();

        while (!rides.last()
                && ride.compareTo(rides.retrieve()) >= 0) {
            result.insert(rides.retrieve());
            rides.findNext();
        }

        if (ride.compareTo(rides.retrieve()) < 0)
            result.insert(ride);

        result.insert(rides.retrieve());

        while (!rides.last()) {
            rides.findNext();
            result.insert(rides.retrieve());
        }

        rides = result;
        return true;
    }

    public boolean removeRideById(int rideId) {
        if (rides.empty())
            return false;

        LinkedList<IRide> result = new LinkedList<IRide>();
        boolean removed = false;

        rides.findFirst();

        while (!rides.last()) {
            IRide ride = rides.retrieve();

            if (ride.getRideId() == rideId && !removed)
                removed = true;
            else
                result.insert(ride);

            rides.findNext();
        }

        IRide ride = rides.retrieve();

        if (ride.getRideId() == rideId && !removed)
            removed = true;
        else
            result.insert(ride);

        rides = result;
        return removed;
    }

    public LinkedList<IRide> getAllAlphabetically() {
        LinkedList<IRide> result = new LinkedList<IRide>();

        if (rides.empty())
            return result;

        rides.findFirst();

        while (!rides.last()) {
            result.insert(rides.retrieve());
            rides.findNext();
        }

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

        if (hasRiderName(rides.retrieve(), riderFullName))
            result.insert(rides.retrieve());

        return result;
    }

    private boolean hasRiderName(IRide ride, String riderFullName) {
        if (ride instanceof IPrivateRide) {
            IRider rider = ((IPrivateRide) ride).getRider();

            return rider != null && rider.getName().equals(riderFullName);
        }

        if (ride instanceof ISharedRide) {
            LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();

            if (participants.empty())
                return false;

            participants.findFirst();

            while (!participants.last()) {
                if (participants.retrieve().getName().equals(riderFullName))
                    return true;

                participants.findNext();
            }

            return participants.retrieve().getName().equals(riderFullName);
        }

        return false;
    }

    public int size() {
        int count = 0;

        if (rides.empty())
            return count;

        rides.findFirst();

        while (!rides.last()) {
            count++;
            rides.findNext();
        }

        count++;
        return count;
    }
}
