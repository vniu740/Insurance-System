package nz.ac.auckland.se281;

public class Profile {

  private String userName;
  private String age;

  // Define Profile constructor
  public Profile(String userName, String age) {
    this.userName = userName;
    this.age = age;
  }

  // Method for returning Profile instance userName as a string
  public String getUserNameProfileClass() {
    return userName;
  }

  // Method for returning Profile instance userName as a string
  public String getAgeProfileClass() {
    return age;
  }

  // Turn profile attributes into a string
  @Override
  public String toString() {
    return userName + ", " + age;
  }
}
