package com.example.javafxproject.GameClasses; /**
 * <b>This class inherits from GameObject and simulates a waterlily.</b>
 * <p>
 * Fly class redefine :
 * <ul>
 * <li>eat() method.</li>
 * <li>grow() method.</li>
 * <li>toString().</li>
 * </ul>
 * </p>
 * <p>
 * Fly inherits directly from Insect class which inherits
 * from Animal class.
 * </p>
 *
 *
 * @author Theo Thuiller
 * @version 1.0
 */

import Mathf.Vector2Double;
import Mathf.Vector2Int;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Objects;

public class Waterlily extends GameObject {
    private Boolean isFree;
    private int capacity;
    private int occupentCount;
    private StaticFood[] foods;
    private Animal[] animals;

    private Vector2Int pondGridPosition;

    //GFX
    final static String WATERLILIES_IMG_URL = "/waterlily_from_high.png";
    final static Vector2Double WATERLILY_IMG_SIZE = new Vector2Double(40,40);


    public Waterlily(int capacity, Vector2Int pondGridPosition) {
        super(); // Call parent class constructor using super()
        this.isFree = true;
        this.capacity = capacity;
        this.occupentCount = 0;
        this.foods = foods;
        this.pondGridPosition = pondGridPosition;
        this.animals = animals;
        this.size = WATERLILY_IMG_SIZE;
        if (!Objects.equals(WATERLILIES_IMG_URL, "")) {
            setImage(new Image(WATERLILIES_IMG_URL));
        }
        pane.setOnMousePressed((MouseEvent e) -> {
            System.out.println("Wat clicked !");
            RotateTransition rotate = new RotateTransition(Duration.seconds(1), pane);
            rotate.setToAngle(180);
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.setAutoReverse(true);
            rotate.setCycleCount(2);
            rotate.play();
        });
    }

    public Vector2Int getPondGridPosition() {
        return this.pondGridPosition;
    }

    public Animal[] getAnimals() {
        return animals;
    }



    public void displayInfo() {
        System.out.println("I am a Waterlily, I have" + this.occupentCount + "Amphibians on me, and my capacity is" + this.capacity + "!");
    }

    public boolean isFree() {
        return this.capacity > this.occupentCount;
    }


    public String[] listenAnimals() {
        String[] res = new String[0];
        if (this.animals != null) {
            res = new String[animals.length];
            for (int i = 0; i < animals.length; i++) {
                res[i] = animals[i].toString(); //+ "("+pondGridPosition.x+")"; Uncomment to verify that animal voices are well shuffled in game
            }
        }
        return res;
    }

    /**
     * allows to add food (class StaticFood) to the waterlily (attribute "foods")
     * if food is already on the waterlily, extends the length of attribute "foods" and adds food
     * else creates simply new food
     * @param food
     */
    public void addFood(StaticFood food) {
        StaticFood[] newStaticFoods;

        if (this.foods != null) {
            newStaticFoods = new StaticFood[this.foods.length + 1];
            newStaticFoods[0] = food;

            for (int i = 1; i < newStaticFoods.length; i++) {
                newStaticFoods[i] = this.foods[i-1];
            }
        }else{
            newStaticFoods = new StaticFood[1];
            newStaticFoods[0] = food;
        }
        this.foods = newStaticFoods;
    }

    /**
     * if there is enough space on the waterlily, adds an animal
     * increments attribute "occupantCount"
     * put back already present animals and adds a new animal (class Animal)
     * updates animal's position
     * @param animal
     * @return
     */
    public boolean addAnimal(Animal animal) {
        if (isFree()) {
            this.occupentCount++;
            Animal[] newAnimals = new Animal[this.occupentCount];
            newAnimals[0] = animal;

            if (animals != null) {
                for (int i = 1; i < occupentCount; i++){
                    newAnimals[i] = this.animals[i-1];
                }
            }
            this.animals = newAnimals;

            animal.pondGridPosition = this.pondGridPosition;
            return true;
        }
        return false;
    }

    /**
     * if there is an animal (class Animal) on the waterlily, removes it from the attribute "animals"
     * creates an array of Animal without the removed animal an put it back in the attribute "anumals"
     * @param animal
     * @return
     */
    public boolean removeAnimal(Animal animal) {
        if (this.occupentCount > 0) {
            this.occupentCount--;
            Animal[] newAnimals = new Animal[this.occupentCount];

            for (int i = 0; i < occupentCount; i++){
                if (animal != this.animals[i]){
                    newAnimals[i] = this.animals[i];
                }
            }
            this.animals = newAnimals;
            return true;
        }
        return false;
    }

}
