package nz.ac.auckland.se281;

public class LifePolicy extends Policy {

  public LifePolicy(int sumInsured) {
    super(sumInsured);
  }

  @Override
  public int calculateBasePremium(Profile loadedProfile) {
    double basePremium;
    // // calculate the basePremium by percentage of sum insured (get profile age / 100 + 1 as a
    // // percentage and multiply by sumInsured)
    basePremium = (Integer.parseInt(loadedProfile.getAgeProfileClass()) / 100.0);
    basePremium = basePremium + 1;
    basePremium = basePremium / 100.0;
    basePremium = basePremium * sumInsured;

    return (int) basePremium;
  }
}
