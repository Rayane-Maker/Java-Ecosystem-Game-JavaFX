package com.example.javafxproject.GameClasses;

import Mathf.Vector2Double;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Objects;

public class Tongue extends GameObject {

    String TONGUE_IMG_URL = "/tongue.png";

    public Tongue(){
        super();
        this.size = new Vector2Double(6,1);
        if (!Objects.equals(TONGUE_IMG_URL, "")) {
            setImage(new Image(TONGUE_IMG_URL));
        }

    }

}
