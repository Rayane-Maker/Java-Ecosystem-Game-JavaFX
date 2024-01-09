package com.example.javafxproject;

/**
 * <b>This class inherits from Amphibian class and simulates a Frog.</b>
 * <p>
 * Frog class redefine :
 * <ul>
 * <li>eat() method.</li>
 * <li>grow() method.</li>
 * <li>toString().</li>
 * </ul>
 * </p>
 * <p>
 * Firefly inherits directly from Amphibian class which inherits
 * from Animal class.
 * </p>
 *
 *
 * @author Theo Thuiller
 * @version 1.0
 */

public class Frog extends Amphibian{

    //Attributes

    public boolean isFroglet;



    //Constructors
    Frog(String _name, double _ageInYears, double _tongueSpeed){
        super(_name, _ageInYears);
        this.tongueSpeed = _tongueSpeed;
    }
    Frog(String _name, double ageInYears){
        super(_name);
        this.age = (int)(ageInYears * 12);
    }

    Frog(String _name){
        super(_name);
        this.tongueSpeed = 5;
    }


    //Setters
    public static void setSpecies(String _species) {
        species = _species;
    }

    //Getters
    public static String getSpecies() {
        return species;
    }


    //Specific methods
    /**
     * The growth of the animal impacts its age and its tongue's speed
     *
     * @param _deltaAge
     *            Drive how grow the animal.
     */
    @Override
    public void grow(int _deltaAge){
        super.grow(_deltaAge);

        //Make age change impact tongue speed performance
        float speedIncrFactor = 1;
        float speedDecrFactor = 1;
        if (_deltaAge > 0) {
            tongueSpeed += age < 12 ? _deltaAge * speedIncrFactor :
                    age > 30 ? _deltaAge * speedDecrFactor : 0;
        }else{
            tongueSpeed += _deltaAge; //Venimous animal impact negatively tongueSpeed
        }
        //Increase age
        age += _deltaAge;

        //Constrain tongue speed
        tongueSpeed = tongueSpeed < 0 ? 0 : tongueSpeed;

        //Update froglet status
        isFroglet = age > 1 && age < 7;
    }

    public void grow(){
        grow(1);
    }

    @Override
    public void eat(Biologic food){
        super.eat(food);
    }

    /**
     * dispays information the frog's state (age and tongue's speed)
     * @return
     */
    @Override
    public String toString() {
        //return super.toString();
        if (isFroglet){
            return String.format("My name is %s and I’m a rare froglet! I’m %d months old and my tongue has a speed of %.2f.", name, age, tongueSpeed);
        }

        return String.format("My name is %s and I’m a rare frog. I’m %d months old and my tongue has a speed of %.2f.", name, age, tongueSpeed);
    }




}