package nz.ac.auckland.se281;

public class Profile {

  private String userName;
  private String age;

  public Profile(String userName, String age) {
    this.userName = userName;
    this.age = age;
  }

  public String getUserNameProfileClass() {
    return userName;
  }

  public String getAgeProfileClass() {
    return age;
  }

  @Override
  public String toString() {
    return userName + ", " + age;
  }
}
