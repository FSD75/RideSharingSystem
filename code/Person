public abstract class Person implements IPerson{
    private final int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    public Person(int id, String name, String phoneNumber){
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber);
        rideHistory = new LinkedList<IRide>();
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        if(phoneNumber == null || !phoneNumber.matches("[0-9]{10}"))
            throw new IllegalArgumentException("Phone number must be exactly 10 digits");

        this.phoneNumber = phoneNumber;
    }

    public LinkedList<IRide> getRideHistory(){
        return rideHistory;
    }

    @Override
    public String toString(){
        return ("Person ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber);
    }

}