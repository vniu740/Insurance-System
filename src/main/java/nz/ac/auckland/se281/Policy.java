package nz.ac.auckland.se281;

public abstract class Policy {

    protected int sumInsured;

    public Policy(int sumInsured) {
        this.sumInsured = sumInsured;
    }

    // all policies calculate base premium differently
    public abstract int calculateBasePremium(Profile loadedProfile);
    
}
