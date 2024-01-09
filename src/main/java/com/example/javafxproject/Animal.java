package com.example.javafxproject;

/**
 * <b>This class inherits from Biologic class and simulates an animal.</b>
 * <p>
 * Animal class define :
 * <ul>
 * <li>eat() method.</li>
 * <li>grow() method.</li>
 * <li>toString().</li>
 * </ul>
 * </p>
 * <p>
 * Firefly inherits directly from Biologic class which inherits
 * from GameObject class.
 * </p>
 *
 *
 * @author Theo Thuiller
 * @version 1.0
 */

public class Animal extends Biologic {
    protected String name;

    protected double mass;
    protected double speed;

    public Waterlily currentWaterlily;

    public  Animal(){
        super(); // Call parent class constructor using super()
        this.name = "Anonymous";
    }

    //setters
    public void setMass(double mass){this.mass = mass < 0 ? this.mass : mass;}

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    //Getters
    public double getMass(){
        return this.mass;
    }
    public double getSpeed() {
        return this.speed;
    }
    public double getNutriscore(){
        return this.nutriscore;
    }

    //Specifics methods
    /**
     * moves the animal from its current waterlily to the targeted one
     * waterlilies are contained in the pond
     * @param rowID
     * @param waterlilyID
     * @param pond
     */
    public void move(int rowID, int waterlilyID, Row[] pond) {

        Waterlily targetWaterlily = pond[rowID].waterlilies[waterlilyID];

        //Update Waterlily
        if (currentWaterlily != null) {
            currentWaterlily.removeAnimal(this);
        }

        targetWaterlily.addAnimal(this);
        currentWaterlily = targetWaterlily;

    }

    /**
     * Try to eat a biologic food.
     * It can be an animal (dead or not) or  a static food.
     * @param food
     *            The biologic element to try to feed the animal.
     */
    public void eat(Biologic food){
    }

    /**
     * Grows the animal by increasing it key
     * characteristic by delta.
     * Each child class instances has its own
     * implementation of how growing affect the
     * animal ability.
     * @param _delta
     *            Drive how grow the animal.
     */
    public void grow(int _delta){

    }




    public boolean isDead(){
        return this.mass <= 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
