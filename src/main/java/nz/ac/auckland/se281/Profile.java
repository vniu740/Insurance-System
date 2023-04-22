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

  // Method for setting the profile loaded status to true
  public boolean setProfileLoadedStatusToTrue() {
    profileLoadedStatus = true;
    return profileLoadedStatus;
  }

  // Method for setting the profile loaded status to false
  public boolean setProfileLoadedStatusToFalse() {
    profileLoadedStatus = false;
    return profileLoadedStatus;
  }

  // Method for getting the profile loaded status
  public boolean getProfileLoadedStatus() {
    return profileLoadedStatus;
  }

  // Method for adding a policy to the profile's policy array
  public void addPolicyToProfileArray(Policy policy) {
    arrayOfPolicies.add(policy);
  }

  // Method for getting the size of the profile's policy array
  public String getSizeOfArrayOfPolicies() {
    return String.valueOf(arrayOfPolicies.size());
  }

  // Method for a particular policy within the profile's policy array
  public Policy getPolicyWithinArray(int i) {
    return arrayOfPolicies.get(i);
  }

  // Method for getting the profile's policy array
  public ArrayList<Policy> getPolicyArray() {
    return arrayOfPolicies;
  }
}
