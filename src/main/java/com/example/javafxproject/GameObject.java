package com.example.javafxproject;

import Mathf.Vector2Double;
import Mathf.Vector2Int;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class GameObject {
    public Vector2Double position;
    public double rotation;

    public double screenHeight;

    public Image img;
    public ImageView imageView;

    GameObject(){
        position = new Vector2Double(0.0,0.0);
    }

    public void setImage(Image img, Pane paneSupport, Double screenHeight, Vector2Int size){
        this.screenHeight = screenHeight;
        imageView = new ImageView(img);
        imageView.setPreserveRatio(false);
        imageView.setFitWidth(size.x);
        imageView.setFitWidth(size.y);

        imageView.setX(position.x);
        imageView.setY(screenHeight - position.y);
        imageView.setPickOnBounds(true); // allows click on transparent areas
        paneSupport.getChildren().add(imageView);
    }

    public void updateGraphic(){
        imageView.setX(position.x);
        imageView.setY(this.screenHeight - position.y);
    }

}
