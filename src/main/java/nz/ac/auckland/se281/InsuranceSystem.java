package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).

  }

  public void printDatabase() {
    System.out.println("Database has 0 profiles.");
  }

  public void createNewProfile(String userName, String age) {
    // TODO: Complete this method.
    // Change the username input to title case
    userName = userName.toLowerCase();
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);

    // Check if username input is lesss than 3 letters, if it is
    if (userName.length() < 3) {
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userName);
    }

    // Check if age input is a positive integer
    for (int i = 0; i < age.length(); i++) {
      if (!Character.isDigit(age.charAt(i))) {
        MessageCli.INVALID_AGE.printMessage(age, userName);
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
