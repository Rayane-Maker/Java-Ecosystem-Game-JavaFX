package com.example.javafxproject.GameClasses;

/**
 * <b>This class inherits from Biologic class and simulates non living food.</b>
 * <p>
 * </p>
 * <p>
 * Flower inherits directly from Biologic class which inherits
 * from GameObject class.
 * </p>
 *
 *
 * @author Theo Thuiller
 * @version 1.0
 */
public class StaticFood extends Biologic {
    protected int nutriscore;

    public StaticFood(int nutriscore) {
        super(); // Call parent class constructor using super()
        this.nutriscore = nutriscore;
    }

    public void displayInfo() {
        System.out.println("I am eatable and my nutriscore is : " + this.nutriscore);
    }
}

