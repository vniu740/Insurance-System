package nz.ac.auckland.se281;

public class LifePolicy extends Policy{

    public LifePolicy(int sumInsured) {
        super(sumInsured);
    }

    @Override
    public int calculateBasePremium(Profile loadedProfile) {
       // calculate the basePremium by percentage of sum insured (get profile age / 100 + 1 as a percentage and multiply by sumInsured)
       return (int)((1 + (Integer.parseInt(loadedProfile.getAgeProfileClass()) / 100)) / 100) * this.sumInsured;
    }
    

}
