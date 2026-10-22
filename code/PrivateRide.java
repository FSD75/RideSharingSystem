public class PrivateRide extends Ride implements IPrivateRide {

    private IRider rider;

    public PrivateRide(int rideId, String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, IDriver driver, IRider rider) {

        super(rideId, pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
        this.rider = rider;
    }

    public IRider getRider() {
        return rider;
    }

    public void setRider(IRider rider) {
        this.rider = rider;
    }

    public boolean hasRider(int riderId) {
        return rider != null && rider.getId() == riderId;
    }

    @Override
    public String toString() {
        String str = "Private " + super.toString();

        if (rider == null)
            str = str + ", Rider: None";
        else
            str = str + ", Rider: " + rider.getName();

        return str;
    }
}
