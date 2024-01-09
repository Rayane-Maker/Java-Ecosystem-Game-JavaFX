package com.example.javafxproject;

/**
 * <b>This class inherits from Animal class and simulates an insect.</b>
 * <p>
 * Insect class redefine :
 * <ul>
 * <li>eat() method.</li>
 * <li>grow() method.</li>
 * <li>toString().</li>
 * </ul>
 * </p>
 * <p>
 * Insect inherits directly from Animal class which inherits
 * from GameObject class.
 * </p>
 *
 *
 * @author Theo Thuiller
 * @version 1.0
 */

public class Insect extends Animal{

    //Constructors
    Insect(double mass, double speed){
        this(mass);
        this.speed = speed;
    }

    Insect(double mass){
        this();
        this.mass = mass;
    }

    Insect(){
        this.mass = 5;
        this.speed = 10;
    }


    //Setters
    public void setMass(double mass){
        this.mass = mass < 0 ? this.mass : mass;
    }
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


    @Override
    public void eat(Biologic food) {
        super.eat(food);
    }

    @Override
    public void grow(int _deltaMass) {
        super.grow(_deltaMass);
    }

}