package nz.ac.auckland.se281;

public class CarPolicy extends Policy{
    
    private String makeAndModel;
    private String licensePlate;
    private Boolean mechanicalBreakdown;
    
    public CarPolicy(int sumInsured, String makeAndModel, String licensePlate, Boolean mechanicalBreakdown) {
        super(sumInsured);
        this.makeAndModel = makeAndModel;
        this.licensePlate = licensePlate;
        this.mechanicalBreakdown = mechanicalBreakdown;
    }

    // @Override
    // public String toString() {
    //     // TODO Auto-generated method stub
    //     return "sum Insured =" + sumInsured + " make and model = " + makeAndModel + "License plate = " + licensePlate + "mechanical breakdown = " + mechanicalBreakdown;
    // }

    

}
