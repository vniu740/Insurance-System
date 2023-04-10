package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Profile {

  private String userName;
  private String age;
  private boolean profileLoadedStatus = false;
  ArrayList<Policy> arrayOfPolicies = new ArrayList<>();

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

  // // Turn profile attributes into a string
  // @Override
  // public String toString() {
  //   return userName + ", " + age;
  // }

  public boolean setProfileLoadedStatusToTrue() {
    profileLoadedStatus = true;
    return profileLoadedStatus;
  }

  public boolean setProfileLoadedStatusToFalse() {
    profileLoadedStatus = false;
    return profileLoadedStatus;
  }

  public boolean getProfileLoadedStatus() {
    return profileLoadedStatus;
  }

  public void addPolicyToProfileArray(Policy policy) {
    arrayOfPolicies.add(policy);
  }

  public String getSizeOfArrayOfPolicies() {
    return String.valueOf(arrayOfPolicies.size());
  }

  public Policy getPolicyWithinArray(int i) {
    return arrayOfPolicies.get(i);
  }

  public ArrayList<Policy> getPolicyArray() {
    return arrayOfPolicies;
  }
}
