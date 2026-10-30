public abstract class Person implements IPerson{ // Class Person implementing IPerson.
    // Person's information (attributes).
    private final int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    public Person(int id, String name, String phoneNumber){ // Creating a person with all infomration.
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber);
        rideHistory = new LinkedList<IRide>();
    }

    public int getId(){ // Returns Person's ID.
        return id;
    }

    public String getName(){ // Returns person's name.
        return name;
    }

    public void setName(String name){ // Updates person's name.
        this.name = name;
    }

    public String getPhoneNumber(){ // Returns the phone number.
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){ // Updates phone number.
        if(phoneNumber == null || !phoneNumber.matches("[0-9]{10}"))
            throw new IllegalArgumentException("Phone number must be exactly 10 digits");

        this.phoneNumber = phoneNumber;
    }

    public LinkedList<IRide> getRideHistory(){ // Returns the ride-history list associated with this person.
        return rideHistory;
    }

    @Override
    public String toString(){ // Printing person's info.
        return ("Person ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber);
    }

}
