public class Rider extends Person implements IRider{ // Class Rider inheriting from Person implementing IRider.
  // Rider's attributes.
  private String email;
  private String homeCity;

  public Rider(int id, String name, String phoneNumber, String email, String homeCity){ // The inherited constructor and initializing rider's info.
    super(id, name, phoneNumber);
    this.email = email;
    this.homeCity = homeCity;
  }

  public String getEmail(){ // Returns rider's email.
    return email;
  }

  public void setEmail(String email){ // Updates rider's email.
    this.email = email;
  }

  public String getHomeCity(){ // Returns rider's home city.
    return homeCity;
  }

  public void setHomeCity(String homeCity){ // Updates rider's home city.
    this.homeCity = homeCity;
  }

  @Override
  public int compareTo(IRider other){ //Compares riders by thier IDs.
    if(getId() < other.getId())
      return -1;

    else if(getId() > other.getId())
      return 1;

    else
      return 0;
  }

  @Override
  public String toString(){ // Printng rider's info + inherited personal information.
    return super.toString() + ", Email: " + email + ", homeCity: " + homeCity;
  }

  





}
