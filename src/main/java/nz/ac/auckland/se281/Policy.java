package nz.ac.auckland.se281;

public abstract class Policy {

  protected int sumInsured;

  public Policy(int sumInsured) {
    this.sumInsured = sumInsured;
  }

  // all policies calculate base premium differently
  public abstract int calculateBasePremium(Profile loadedProfile);

  // all policies get a discount if client has 2 or more policies in database
  public int calculateDiscount(Profile profileOfPolicyOwner) {
    double discountAmount;
    // if the client has two policies, return premium with discount for two policies
    if (Integer.parseInt(profileOfPolicyOwner.getSizeOfArrayOfPolicies()) == 2) {
      discountAmount = calculateBasePremium(profileOfPolicyOwner) * 0.1;
      discountAmount = calculateBasePremium(profileOfPolicyOwner) - discountAmount;
      return (int) discountAmount;
    
    // if the client has 3 or more policies, return premium with discount for three or more policies
    } else if (Integer.parseInt(profileOfPolicyOwner.getSizeOfArrayOfPolicies()) > 2) {
      discountAmount = calculateBasePremium(profileOfPolicyOwner) * 0.2;
      discountAmount = calculateBasePremium(profileOfPolicyOwner) - discountAmount;
      return (int) discountAmount;
    
    // if client has 0 or 1 policy, dont apply a discount 
    } else {
      return Integer.parseInt(profileOfPolicyOwner.getSizeOfArrayOfPolicies());
    }
  }
}
