package nz.ac.auckland.se281;

public class Profile {

  private String userName;
  private String age;

  public Profile(String userName, String age) {
    this.userName = userName;
    this.age = age;
  }

  @Override
  public String toString() {
    return userName + ", " + age;
  }
}
