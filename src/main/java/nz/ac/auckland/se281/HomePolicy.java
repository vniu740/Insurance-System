package nz.ac.auckland.se281;

public class HomePolicy extends Policy {

  private String address;
  private boolean rental;

  public HomePolicy(int sumInsured, String address, boolean rental) {
    super(sumInsured);
    this.address = address;
    this.rental = rental;
  }

  @Override
  public int calculateBasePremium(Profile loadedProfile) {
    if (this.rental == true) {
      return (int) (0.02 * sumInsured);
    } else {
      return (int) (0.01 * sumInsured);
    }
  }
}
