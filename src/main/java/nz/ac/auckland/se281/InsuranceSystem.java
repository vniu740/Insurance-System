package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  Database entireDatabase = new Database("entireDatabase");

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).

  }

  public void printDatabase() {
    int rank = 1;
    int numberOfProfilesInt = entireDatabase.getTotalNumberOfProfiles();
    String numberOfProfilesString;
    if (numberOfProfilesInt == 0) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("0", "s", ".");
    }
    if (numberOfProfilesInt == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("1", "", ":");
      System.out.println(" " + rank + ":" + " " + entireDatabase.getProfile(0));
    }
    if (numberOfProfilesInt > 1) {
      numberOfProfilesString = String.valueOf(numberOfProfilesInt);
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(numberOfProfilesString, "s", ":");
    }
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
      }
      if (entireDatabase.isProfileUnique(userName) == false) {
        MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(userName);
      } else {
        // create new profile instance
        Profile newProfile = new Profile(userName, age);
        entireDatabase.addProfileToCollection(newProfile);
        MessageCli.PROFILE_CREATED.printMessage(userName, age);
        System.out.println("the format is " + newProfile);
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
