package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Database {

  private String dataBaseName;

  private ArrayList<Profile> profileCollection = new ArrayList<Profile>();

  public Database(String dataBaseName) {
    this.dataBaseName = dataBaseName;
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

  public boolean isProfileUnique(String Usernammm) {
    for (Profile element : profileCollection) {
      if (element.toString().contains(Usernammm)) {
        return false;
      }
    }
    return true;
  }
}
