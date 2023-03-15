package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Database {

  private String databaseName;

  private ArrayList<Profile> profileCollection = new ArrayList<Profile>();

  public Database(String databaseName) {
    this.databaseName = databaseName;
  }

  public void addProfileToCollection(Profile profile) {
    profileCollection.add(profile);
  }

  public int getTotalNumberOfProfiles() {
    return profileCollection.size();
  }

  public Profile getProfile(int i) {
    return profileCollection.get(i);
  }
}
