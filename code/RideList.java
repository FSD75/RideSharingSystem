
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
        LinkedList<IRide> result = new LinkedList<IRide>();
        boolean removed = false;

        if (rides.empty())
            return false;

        rides.findFirst();

        while (true) {
            IRide ride = rides.retrieve();

            if (!removed && ride.getRideId() == rideId) {
                removed = true;
            } else {
                result.insert(ride);
            }

            if (rides.last())
                break;

            rides.findNext();
        }

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

        while (true) {
            IRide ride = rides.retrieve();
            boolean found = false;

            if (ride instanceof IPrivateRide) {
                IRider rider = ((IPrivateRide) ride).getRider();

                if (rider != null &&
                    rider.getName().equals(riderFullName)) {
                    found = true;
                }

            } else if (ride instanceof ISharedRide) {
                LinkedList<IRider> participants =
                    ((ISharedRide) ride).getParticipants();

                if (!participants.empty()) {
                    participants.findFirst();

                    while (true) {
                        if (participants.retrieve().getName()
                                .equals(riderFullName)) {
                            found = true;
                            break;
                        }

                        if (participants.last())
                            break;

                        participants.findNext();
                    }
                }
            }

            if (found)
                result.insert(ride);

            if (rides.last())
                break;

            rides.findNext();
        }

        return result;
    }

    public int size() {
        int count = 0;

        if (rides.empty())
            return 0;

        rides.findFirst();

        while (true) {
            count++;

            if (rides.last())
                break;

            rides.findNext();
        }

        return count;
    }
}
