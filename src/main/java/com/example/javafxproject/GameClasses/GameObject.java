package com.example.javafxproject.GameClasses;

import Mathf.Vector2Double;
import Mathf.Vector2Int;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class GameObject {
    protected Vector2Double position;
    private double rotation;

    public double screenHeight;

    public ImageView imageView;

    public Pane pane;

    GameObject(){
        position = new Vector2Double(0.0,0.0);
        pane = new Pane();
    }

    public void setImage(Image img, Pane paneSupport, Double screenHeight, Vector2Int size){
        this.screenHeight = screenHeight;
        imageView = new ImageView(img);
        imageView.setPreserveRatio(false);
        imageView.setFitWidth(size.x);
        imageView.setFitHeight(size.y);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setPickOnBounds(true); // allows click on transparent areas
        pane.getChildren().add(imageView);
        updatePanePosition();
        
        paneSupport.getChildren().add(pane);
    }

    public void setX(Double x){
        this.position.setX(x);
        updatePanePosition();
    }

    public void setY(Double y){
        this.position.setX(y);
        updatePanePosition();
    }

    public Double getX(){
        return this.position.x;
    }

    public Double getY(){
        return this.position.y;
    }

    public void setPosition(Double x, Double y){
        this.position.x = x;
        this.position.y = y;
        updatePanePosition();
    }

    public Vector2Double getPosition(){
        return this.position;
    }

    public void updateAllGraphic(){
        updatePanePosition();
        updatePaneRotation();
    }

    private void updatePanePosition(){
        if (imageView != null) {
            pane.setTranslateX(position.x - imageView.getFitWidth() / 2.0);
            pane.setTranslateY(screenHeight - position.y + imageView.getFitWidth() / 2.0);
        }
    }

    private void updatePaneRotation(){
        if (imageView != null) {
            pane.setRotate(rotation);
        }
    }
    
}
