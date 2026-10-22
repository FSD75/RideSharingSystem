public class Rider extends Person implements IRider{
  private String email;
  private String homeCity;

  public Rider(int id, String name, String phoneNumber, String email, String homeCity){
    super(id, name, phoneNumber);
    this.email = email;
    this.homeCity = homeCity;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getHomeCity(){
    return homeCity;
  }

  public void setHomeCity(String homeCity){
    this.homeCity = homeCity;
  }

  @Override
  public int compareTo(IRider other){
    if(getId() < other.getId())
      return -1;

    else if(getId() > other.getId())
      return 1;

    else
      return 0;
  }

  @Override
  public String toString(){
    return super.toString() + ", Email: " + email + ", homeCity: " + homeCity;
  }

  





}
