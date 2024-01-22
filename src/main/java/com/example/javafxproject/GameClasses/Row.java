package com.example.javafxproject.GameClasses;

import Mathf.Vector2Double;
import javafx.scene.image.Image;

import static com.example.javafxproject.GameClasses.Frog.FROG_IMG_URL;

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
