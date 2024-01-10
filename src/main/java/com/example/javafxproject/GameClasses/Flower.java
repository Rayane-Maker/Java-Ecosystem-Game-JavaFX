package com.example.javafxproject.GameClasses;

/**
 * <b>This class inherits from StaticFood class and simulates a flower.</b>
 * <p>
 * </p>
 * <p>
 * Flower inherits directly from StaticFood class which inherits
 * from GameObject class.
 * </p>
 *
 *
 * @author Theo Thuiller
 * @version 1.0
 */

public class Flower extends StaticFood {

    static int DEFAULT_NUTRISCORE = 4;

    public Flower() {
        super(DEFAULT_NUTRISCORE); // Call parent class constructor using super()
    }

    Flower(int nutriscore) {
        super(nutriscore); // Call parent class constructor using super()
    }

    public void displayInfo() {
        System.out.println("I am a Flower and my nutriscore worth" + nutriscore + "!");
    }
}
