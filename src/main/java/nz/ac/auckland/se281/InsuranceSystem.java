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
      // if the profile has the same username as the inputted username return false
      if (element.getUserNameProfileClass().equals(uncertainUsername)) {
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

  public String turnToTitleCase(String notTitleCase) {
    notTitleCase = notTitleCase.toLowerCase();
    notTitleCase = notTitleCase.substring(0, 1).toUpperCase() + notTitleCase.substring(1);
    return notTitleCase;
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
        // if the profile is loaded, print the profile with the loaded profile message
        if (profileCollection.get(i).getProfileLoadedStatus() == true) {
          MessageCli.PRINT_DB_PROFILE_HEADER_SHORT.printMessage(
              "*** ", rankString, getUsername(i), getAge(i));
        } else {
          // print the normal profile message
          MessageCli.PRINT_DB_PROFILE_HEADER_MINIMAL.printMessage(
              rankString, getUsername(i), getAge(i));
        }
        rank++;
      }
    }
  }

  public void createNewProfile(String userName, String age) {

    // Change the username input to title case
    // userName = userName.toLowerCase();
    // userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
    userName = turnToTitleCase(userName);

    // Check if profile is already loaded, if it is, print out invalid message and stop running
    // mrthod
    for (Profile element : profileCollection) {
      if (element.getProfileLoadedStatus() == true) {
        MessageCli.CANNOT_CREATE_WHILE_LOADED.printMessage(element.getUserNameProfileClass());
        return;
      }
    }

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
    // Turn input userName to title case by calling the method turnToTitleCase
    userName = turnToTitleCase(userName);

    // Use method to determine if inputted username is stored in arraylist, if it is not (i.e not
    // unique) profile is loaded
    // otherwise if it is not unique, the profile is loaded
    if (isProfileUnique(userName) == true) {
      MessageCli.NO_PROFILE_FOUND_TO_LOAD.printMessage(userName);
    } else {
      // check if any profiles are already loaded, if they are, unload them
      for (Profile element : profileCollection) {
        if (element.getProfileLoadedStatus() == true) {
          element.setProfileLoadedStatusToFalse();
        }
      }
      // Print profile has been loaded message
      MessageCli.PROFILE_LOADED.printMessage(userName);

      // Set boolean isProfileLoaded to true
      for (Profile element : profileCollection) {
        if (element.toString().contains(userName)) {
          element.setProfileLoadedStatusToTrue();
        }
      }
    }
  }

  public void unloadProfile() {
    // loop through profileCollection arraylist and check if any profile is loaded
    for (Profile element : profileCollection) {
      // if the profile is loaded, unload it and print out unloaded message
      if (element.getProfileLoadedStatus() == true) {
        element.setProfileLoadedStatusToFalse();
        MessageCli.PROFILE_UNLOADED.printMessage(element.getUserNameProfileClass());
        return;
      }
    }
    // if no profile is unloaded, print the no profile loaded message
    MessageCli.NO_PROFILE_LOADED.printMessage();
  }

  public void deleteProfile(String userName) {
    // Turn input userName to title case by calling the method turnToTitleCase
    userName = turnToTitleCase(userName);

    // check if profile is in database
    // if the profile is not in the database, print no profile found message
    if (isProfileUnique(userName) == true) {
      MessageCli.NO_PROFILE_FOUND_TO_DELETE.printMessage(userName);
    } else {
      // if the profile is in the database:
      // check if the profile that the user wants to delete is loaded, if it is print cannot delete
      // profile message
      for (Profile element : profileCollection) {
        if (element.getUserNameProfileClass().equals(userName)) {
          if (element.getProfileLoadedStatus() == true) {
            MessageCli.CANNOT_DELETE_PROFILE_WHILE_LOADED.printMessage(userName);
            return;
          }
        }
      }
      // if the profile is not loaded, remove the profile from the arraylist and print deleted
      // message
      for (int i = 0; i < profileCollection.size(); i++) {
        if (profileCollection.get(i).getUserNameProfileClass().equals(userName)) {
          profileCollection.remove(i);
          MessageCli.PROFILE_DELETED.printMessage(userName);
        }
      }
    }
  }

  public void createPolicy(PolicyType type, String[] options) {

    Profile loadedProfile = null;
    Policy newPolicy;

    // check for loaded profile
    for (Profile element : profileCollection) {
      // if a profile is loaded set the loadedProfile to the profile
      if (element.getProfileLoadedStatus() == true) {
        loadedProfile = element;
        break;
      }
      // if a profile is not loaded print the message that a profile must be loaded to create a
      // policy
      MessageCli.NO_PROFILE_FOUND_TO_CREATE_POLICY.printMessage();
    }

    // if profile is loaded create new policy for specific policy type
    if (loadedProfile != null) {
      boolean inputMechanicalBreakdown;
      boolean inputRental;

      switch (type) {
        case CAR:
          // Change inputMechanicalBreakdown input in options array from string to boolean
          if (options[3].equals("y") || options[3].equals("yes")) {
            inputMechanicalBreakdown = true;
          } else {
            inputMechanicalBreakdown = false;
          }
          // Create new car policy
          newPolicy =
              new CarPolicy(
                  Integer.parseInt(options[0]), options[1], options[2], inputMechanicalBreakdown);
          // System.out.println(newPolicy.toString());
          break;

        case HOME:
          // Change inputRental input in options array from string to boolean
          if (options[2].equals("y") || options[2].equals("yes")) {
            inputRental = true;
          } else {
            inputRental = false;
          }

          // Create new home policy
          newPolicy = new HomePolicy(Integer.parseInt(options[0]), options[1], inputRental);
          break;

        case LIFE:
          // create new life policy
          newPolicy = new LifePolicy(Integer.parseInt(options[0]));
      }
    }
  }
}
