package nz.ac.auckland.se281;

public abstract class Policy {

  protected int sumInsured;

  public Policy(int sumInsured) {
    this.sumInsured = sumInsured;
  }

  // all policies calculate base premium differently
  public abstract int calculateBasePremium(Profile loadedProfile);

  // all policies get a discount if client has 2 policies in database
  public int calculateDiscountFor2Policies(Profile profileOfPolicyOwner) {
    double discountAmount;
    discountAmount = calculateBasePremium(profileOfPolicyOwner) * 0.1;
    discountAmount = calculateBasePremium(profileOfPolicyOwner) - discountAmount;
    return (int) discountAmount;
  }
}
