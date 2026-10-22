public class SharedRide extends Ride implements ISharedRide {

    private LinkedList<IRider> participants;

    public SharedRide(int rideId, String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, IDriver driver) {

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

    public boolean removeParticipantById(int riderId) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        boolean removed = false;

        if (participants.empty())
            return false;

        participants.findFirst();

        while (true) {
            IRider rider = participants.retrieve();

            if (!removed && rider.getId() == riderId) {
                removed = true;
            } else {
                result.insert(rider);
            }

            if (participants.last())
                break;

            participants.findNext();
        }

        participants = result;
        return removed;
    }

    public boolean isEmpty() {
        return participants.empty();
    }

    public boolean hasRider(int riderId) {
        if (participants.empty())
            return false;

        participants.findFirst();

        while (true) {
            if (participants.retrieve().getId() == riderId)
                return true;

            if (participants.last())
                break;

            participants.findNext();
        }

        return false;
    }

    @Override
    public String toString() {
        String str = "Shared " + super.toString() + ", Participants: ";

        if (participants.empty())
            return str + "None";

        participants.findFirst();

        while (true) {
            str += participants.retrieve().getName();

            if (participants.last())
                break;

            str += ", ";
            participants.findNext();
        }

        return str;
    }
}
