package com.example.javafxproject.GameClasses;

import Mathf.Vector2Double;
import com.example.javafxproject.GameApplication;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;


public class GameObject {
    protected Vector2Double position;
    protected Vector2Double localPosition;

    public GameObject[] children = new GameObject[0];

    public GameObject parent = null;
    private Vector2Double oldPosition;
    protected Vector2Double currentSpeed;

    protected double rotation;

    protected Vector2Double size;

    public double screenHeight;

    public ImageView imageView;

    public Pane pane;

    public Double globTimeStep = 0.01;

    GameObject(){
        position = new Vector2Double(0.0,0.0);
        rotation = 0;
        pane = new Pane();
        Vector2Double DEFAULT_SIZE = new Vector2Double(0, 0);
        this.size = new Vector2Double(0,0);
        setSize(DEFAULT_SIZE.x, DEFAULT_SIZE.y);
        String defaultURLTexture = "";
        if (!Objects.equals(defaultURLTexture, "")) {
            setImage(new Image(defaultURLTexture));
        }
    }

    public void setSize(Double x, Double y){
        this.size.x = x;
        this.size.y = y;
        updateAllGraphic();
    }

    public void setImage(Image img){
        if (imageView != null) {
            pane.getChildren().remove(imageView);
        }imageView = new ImageView(img);
        imageView.setPreserveRatio(false);
        imageView.setFitWidth(size.x);
        imageView.setFitHeight(size.y);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setPickOnBounds(true); // allows click on transparent areas
        setSize(size.x, size.y);
        pane.getChildren().add(imageView);
        updateAllGraphic();
        GameApplication.AddGameObject(this);
    }

    public void setParent(GameObject parent){
        if (this.parent != null) {
            this.parent.removeChild(this);
        }
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(GameObject child){
        GameObject[] newChildren = new GameObject[0];

        if (this.children != null) {
            newChildren = new GameObject[this.children.length + 1];
        }else {
          this.children = new GameObject[1];
        }
        newChildren[0] = child;


        for (int i = 1; i < this.children.length; i++){
            newChildren[i] = this.children[i-1];
        }

        this.children = newChildren;
    }

    public void removeChild(GameObject child) {
        if (this.children.length > 0) {
            GameObject[] newChildren = new GameObject[this.children.length - 1];

            for (int i = 0; i < this.children.length; i++){
                if (child != this.children[i]){
                    newChildren[i] = this.children[i];
                }
            }
            this.children = newChildren;
        }
    }

    public void setX(Double x){
        this.position.setX(x);
        updatePanePosition();
        updateChildrenTransform();
    }

    public void setY(Double y){
        this.position.setY(y);
        updatePanePosition();
        updateChildrenTransform();

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
        oldPosition = new Vector2Double(position.x, position.y);
        updatePanePosition();
        updateChildrenTransform();
    }

    public void setPosition(Vector2Double v){
        this.position.x = v.x;
        this.position.y = v.y;
        oldPosition = new Vector2Double(position.x, position.y);
        updatePanePosition();
        updateChildrenTransform();
    }

    public void setRotation(Double rot){
        this.rotation = rot;
        updateAllGraphic();
        updateChildrenTransform();
    }

    public void setRotation(int rot){
        this.rotation = rot;
        updateAllGraphic();
        updateChildrenTransform();
    }

    public Vector2Double getPosition(){
        return this.position;
    }



    public void smoothScaleTo(Vector2Double targetScale, double duration, boolean autoReverse, boolean fromBase){

        //Init smooth transformation
        Vector2Double departureScale = new Vector2Double(pane.getScaleX(),pane.getScaleY());
        System.out.println(departureScale.x);

        Vector2Double travel = Vector2Double.subtract(targetScale, departureScale);
        System.out.println(travel.x);

        Vector2Double speed = travel.divide(duration);
        double timeStep = globTimeStep;
        Vector2Double deltaScale = speed.multiply(timeStep);
        System.out.println(deltaScale.x);

        int maxStep = (int) (duration/timeStep);
        System.out.println(maxStep);

        smoothScaleCycle(departureScale, deltaScale, timeStep, duration, 0, maxStep, autoReverse, fromBase);
    }

    private void smoothScaleCycle(Vector2Double departureScale, Vector2Double deltaScale, Double timeStep, Double duration, int step, int maxStep, boolean autoReverse, boolean fromBase) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(timeStep), pane);
        scaleTransition.setToX(departureScale.x + deltaScale.x * (step + 1));
        scaleTransition.setToY(departureScale.y + deltaScale.y * (step + 1));
        scaleTransition.setInterpolator(Interpolator.LINEAR);
        scaleTransition.setAutoReverse(false);
        scaleTransition.setCycleCount(1);
        scaleTransition.play();
        scaleTransition.setOnFinished((actionEvent) ->
                {
                    // Update GameObject transform
                    if (fromBase){
                        double radAngle = Math.toRadians(rotation);
                        Vector2Double dir = new Vector2Double(Math.cos(radAngle), Math.sin(radAngle));
                        double transl = pane.getHeight() * deltaScale.y/2.0;
                        setPosition(position.add(dir.multiply(transl)));
                        System.out.println("transl");
                        System.out.println(transl);
                    }
                    //transformUpdate();

                    // Call for a next animation or quit if movement is finish
                    if (step < maxStep-1){
                        smoothScaleCycle(departureScale, deltaScale, timeStep, duration, step + 1, maxStep, autoReverse, fromBase);
                    }else {
                        scaleTransition.stop();
                        System.out.println(pane.getScaleX());

                        if (autoReverse) {
                            smoothScaleTo(new Vector2Double(1,1), duration, false, fromBase);
                        }
                        //transformUpdate();
                    }

                }
        );
    }

    public void smoothTranslateTo(Vector2Double targetPos, double duration){

        //Init smooth transformation
        Vector2Double departurePosition = position;
        Vector2Double travel = Vector2Double.subtract(targetPos, departurePosition);
        Vector2Double speed = travel.divide(duration);
        double timeStep = globTimeStep;
        Vector2Double deltaMove = speed.multiply(timeStep);
        int maxStep = (int) (duration/timeStep);
        smoothTranslateCycle(deltaMove, timeStep, 0, maxStep);
    }

    private void smoothTranslateCycle(Vector2Double deltaMove, Double timeStep, int step, int maxStep) {
        Vector2Double targetTmp = Vector2Double.add(position, deltaMove);
        TranslateTransition translate = new TranslateTransition(Duration.seconds(timeStep), pane);
        translate.setToX(targetTmp.x - imageView.getFitWidth() / 2.0);
        translate.setToY(-targetTmp.y + screenHeight + imageView.getFitWidth() / 2.0);
        translate.setInterpolator(Interpolator.EASE_BOTH);
        translate.setAutoReverse(false);
        translate.setCycleCount(1);
        translate.play();
        translate.setOnFinished((actionEvent) ->
                {
                    // Update GameObject transform
                    updatePositionToPane();
                    transformUpdate();

                    // Call for a next animation or quit if movement is finish
                    if (step < maxStep-1){
                        smoothTranslateCycle(deltaMove, timeStep, step + 1, maxStep);
                    }else {
                        translate.stop();
                        transformUpdate();
                    }

                }
        );
    }

    public void transformUpdate(){
        Vector2Double deltaPos = new Vector2Double(position.x - oldPosition.x, position.y - oldPosition.y);
        currentSpeed = deltaPos.divide(globTimeStep);
        oldPosition = new Vector2Double(position.x, position.y);
    }


    public void updateAllGraphic(){
        updatePanePosition();
        updatePaneSize();
        updatePaneRotation();
    }

    private void updatePositionToPane(){
        if (imageView != null) {
            position.x = pane.getTranslateX() + imageView.getFitWidth() / 2.0;
            position.y = -pane.getTranslateY() + screenHeight + imageView.getFitWidth() / 2.0;
            updateChildrenTransform();
        }
    }

    private void updatePanePosition(){
        if (imageView != null) {
            pane.setTranslateX(position.x - imageView.getFitWidth() / 2.0);
            pane.setTranslateY(screenHeight - position.y + imageView.getFitWidth() / 2.0);
        }
    }

    private void updatePaneSize(){
        if (imageView != null) {
            imageView.setPreserveRatio(false);
            imageView.setFitWidth(size.x);
            imageView.setFitHeight(size.y);
            pane.setMinSize(size.x, size.y);
            pane.setMaxSize(size.x, size.y);
        }
    }

    private void updatePaneRotation(){
        if (imageView != null) {
            pane.setRotate(rotation);
        }
    }

    private void updateChildrenTransform(){
        if (children != null) {
            for (GameObject child : children){
                Double cos = Math.cos(Math.toRadians(rotation));
                Double sin = Math.sin(Math.toRadians(rotation));
                Vector2Double rotatedVec = new Vector2Double(child.localPosition.x * cos - child.localPosition.y * sin, child.localPosition.x * sin + child.localPosition.y * cos);
                child.setPosition(this.position.add(rotatedVec));
                child.setRotation(rotation);
            }
        }
    }
    




}
