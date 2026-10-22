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
        if (participants.empty())
            return false;

        participants.findFirst();

        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) {
                participants.remove();
                return true;
            }

            participants.findNext();
        }

        if (participants.retrieve().getId() == riderId) {
            participants.remove();
            return true;
        }

        return false;
    }

    public boolean isEmpty() {
        return participants.empty();
    }

    public boolean hasRider(int riderId) {
        if (participants.empty())
            return false;

        participants.findFirst();

        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId)
                return true;

            participants.findNext();
        }

        return participants.retrieve().getId() == riderId;
    }

    @Override
    public String toString() {
        String str = "Shared " + super.toString() + ", Participants: ";

        if (participants.empty())
            return str + "None";

        participants.findFirst();

        while (!participants.last()) {
            str += participants.retrieve().getName() + ", ";
            participants.findNext();
        }

        str += participants.retrieve().getName();

        return str;
    }
}
