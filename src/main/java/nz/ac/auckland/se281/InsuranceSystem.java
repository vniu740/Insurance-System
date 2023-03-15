package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  Database entireDatabase = new Database("entireDatabase");

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).

  }

  public void printDatabase() {

    System.out.println(
        "Number of profiles in database is: " + entireDatabase.getTotalNumberOfProfiles());
  }

  public void createNewProfile(String userName, String age) {

    // TODO: Complete this method.
    // Change the username input to title case
    userName = userName.toLowerCase();
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);

    // Check if age input is a positive integer, if it is not, print out invalid message
    for (int i = 0; i < age.length(); i++) {
      if (!Character.isDigit(age.charAt(i))) {
        MessageCli.INVALID_AGE.printMessage(age, userName);
        break;
      }
      // Check if username input is less than 3 letters, if it is print out invalid message
      if (userName.length() < 3) {
        MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userName);
        break;
      } else {
        // create new profile instance
        Profile newProfile = new Profile(userName, age);
        entireDatabase.addProfileToCollection(newProfile);
        MessageCli.PROFILE_CREATED.printMessage(userName, age);
        break;
      }
    }
  }

  public void loadProfile(String userName) {
    // TODO: Complete this method.
  }

  public void unloadProfile() {
    // TODO: Complete this method.
  }

  public void deleteProfile(String userName) {
    // TODO: Complete this method.
  }

  public void createPolicy(PolicyType type, String[] options) {
    // TODO: Complete this method.
  }
}
