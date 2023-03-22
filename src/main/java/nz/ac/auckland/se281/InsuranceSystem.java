package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {

  // Create arraylist to store Profile instances in
  ArrayList<Profile> profileCollection = new ArrayList<Profile>();

  // Method to add profile instance to the arraylist, profileCollection
  public void addProfileToCollection(Profile profile) {
    profileCollection.add(profile);
  }

  // Method to determine number of profiles stored in the arraylist, profileCollection
  public int getTotalNumberOfProfiles() {
    return profileCollection.size();
  }

  // Method to determine if username being added to the database is unique
  public boolean isProfileUnique(String uncertainUsername) {
    // Loop through arraylist profileCollection
    for (Profile element : profileCollection) {
      // if the profile turned int to a string (toString method in Profile class) contains the
      // inputted username, return false
      if (element.toString().contains(uncertainUsername)) {
        return false;
      }
    }
    return true;
  }

  // Method to return a Profile's username as a string
  public String getUsername(int i) {
    Profile gotProfile = profileCollection.get(i);
    return gotProfile.getUserNameProfileClass();
  }

  // Method to return a Profile's age as a string
  public String getAge(int i) {
    Profile gotProfile = profileCollection.get(i);
    return gotProfile.getAgeProfileClass();
  }

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).

  }

  public void printDatabase() {
    // Initialise variables
    int rank = 1;
    String numberOfProfilesString;
    String rankString;

    // Get the total number of profiles stored in the arraylist profileCollections
    int numberOfProfilesInt = getTotalNumberOfProfiles();

    // If the total number of profiles is 0, print the specific message for 0 profiles
    if (numberOfProfilesInt == 0) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("0", "s", ".");
    }

    // If the total number of profiles is 1, print the specific message for 1 profile
    if (numberOfProfilesInt == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("1", "", ":");
      MessageCli.PRINT_DB_PROFILE_HEADER_MINIMAL.printMessage("1", getUsername(0), getAge(0));
    }

    // If the total number of profiles is greater than 1,
    if (numberOfProfilesInt > 1) {

      // Turn the integer, numberOfProfilesInt, into a string
      numberOfProfilesString = String.valueOf(numberOfProfilesInt);

      // Print the specific message for more than 1 profile
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(numberOfProfilesString, "s", ":");

      // Get each profile and print it by looping through the Arraylist
      for (int i = 0; i < numberOfProfilesInt; i++) {
        rankString = String.valueOf(rank);
        MessageCli.PRINT_DB_PROFILE_HEADER_MINIMAL.printMessage(
            rankString, getUsername(i), getAge(i));
        rank++;
      }
    }
  }

  public void createNewProfile(String userName, String age) {

    // Change the username input to title case
    userName = userName.toLowerCase();
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);

    // Check if username input is less than 3 letters, if it is, print out invalid message and stop
    // running method
    if (userName.length() < 3) {
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userName);
      return;
    }

    // Check if username input is unique, if it is not, print out invalid message and stop running
    // method
    if (isProfileUnique(userName) == false) {
      MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(userName);
      return;
    }

    // Check if age input is a positive integer, if it is not, print out invalid message and stop
    // running method
    for (int i = 0; i < age.length(); i++) {
      if (!Character.isDigit(age.charAt(i))) {
        MessageCli.INVALID_AGE.printMessage(age, userName);
        return;
      }
    }

    // create new profile instance
    Profile newProfile = new Profile(userName, age);
    addProfileToCollection(newProfile);
    MessageCli.PROFILE_CREATED.printMessage(userName, age);
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
