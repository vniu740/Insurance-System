package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Profile {

  private ArrayList<Profile> profileCollection = new ArrayList<Profile>();

  private String userName;
  private String age;

  public Profile(String userName, String age) {
    this.userName = userName;
    this.age = age;
    // profileNames.add(userName);
    // profileAges.add(age);
  }

  public void addProfileToCollection(Profile profile) {
    profileCollection.add(profile);
  }

  @Override
  public String toString() {
    return userName + " " + age;
  }
}
