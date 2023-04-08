package nz.ac.auckland.se281;

public class CarPolicy extends Policy {

  private String makeAndModel;
  private String licensePlate;
  private Boolean mechanicalBreakdown;

  public CarPolicy(
      int sumInsured, String makeAndModel, String licensePlate, Boolean mechanicalBreakdown) {
    super(sumInsured);
    this.makeAndModel = makeAndModel;
    this.licensePlate = licensePlate;
    this.mechanicalBreakdown = mechanicalBreakdown;
  }

  @Override
  public int calculateBasePremium(Profile loadedProfile) {
    double basePremium = 0;

    // if the client is over 25
    if (Integer.parseInt(loadedProfile.getAgeProfileClass()) < 25) {
      basePremium = (0.15 * sumInsured);
    } else if (Integer.parseInt(loadedProfile.getAgeProfileClass()) >= 25) {
      basePremium = (0.1 * sumInsured);
    }
    if (mechanicalBreakdown == true) {
      basePremium = basePremium + 80;
    }
    return (int) basePremium;
  }

  // @Override
  // public String toString() {
  //     // TODO Auto-generated method stub
  //     return "sum Insured =" + sumInsured + " make and model = " + makeAndModel + "License plate
  // = " + licensePlate + "mechanical breakdown = " + mechanicalBreakdown;
  // }
}
