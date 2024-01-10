package com.example.javafxproject.GameClasses;

/**
 * <b>This class inherits from StaticFood class and simulates a poo.</b>
 * <p>
 * </p>
 * <p>
 * Poo inherits directly from StaticFood class which inherits
 * from GameObject class.
 * </p>
 *
 *
 * @author Theo Thuiller
 * @version 1.0
 */
public class Poo extends StaticFood {

    static int DEFAULT_NUTRISCORE = 8;
    public Poo() {
        super(DEFAULT_NUTRISCORE); // Call parent class constructor using super()
    }

    Poo(int nutriscore) {
        super(nutriscore); // Call parent class constructor using super()
    }

    public void displayInfo() {
        System.out.println("I am a Poo and my nutriscore worth" + nutriscore + "!");
    }
}
