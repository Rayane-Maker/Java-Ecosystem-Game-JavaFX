package com.example.javafxproject.GameClasses;

/**
 * <b>This class inherits from GameObject class and simulates a row of waterlilies.</b>
 *
 * @author Theo Thuiller
 * @version 1.0
 */

public class Row extends GameObject{

    public Waterlily[] waterlilies;

    public Row(Waterlily[] waterlilies) {
        super();
        this.waterlilies = waterlilies;
    }
}
