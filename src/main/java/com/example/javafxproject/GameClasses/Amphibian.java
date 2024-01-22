package com.example.javafxproject.GameClasses;

import ArrayUtil.RandomOperations;
import Mathf.Vector2Double;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

import static com.example.javafxproject.GameApplication.eatAction;

public class Amphibian extends Animal implements Player {

    public int age; //In months

    static public String species;
    protected double tongueSpeed;

    public GameObject tongueObj;


    //Constructors
    Amphibian(String _name, double ageInYears) {
        this(_name);
        this.age = (int) (ageInYears * 12);
    }

    Amphibian(String _name) {
        super(); //Call parent class constructor using super()
        this.name = _name;
        this.age = 5;
        Amphibian.species = "Rare Pepe";
        tongueObj = new Tongue();
        tongueObj.setParent(this);
        tongueObj.localPosition = new Vector2Double(-35/2 + tongueObj.size.x/2,0.0);
        pane.setOnMousePressed((MouseEvent e) -> {
            System.out.println("Amphibian clicked !");
            tongueObj.smoothScaleTo(new Vector2Double(1.0,50.0), 0.2, true, false);
            setRotation(0);
            eatAction();
        });
    }

    public double getTongueSpeed() {
        return tongueSpeed;
    }

    public void setTongueSpeed(double tongueSpeed) {
        this.tongueSpeed = tongueSpeed;
    }

    @Override
    public void eat(Biologic food) {
        super.eat(food);

        if (food instanceof Insect insectFood) {

            //Early quit the method if the insect is dead
            if (insectFood.isDead()) {
                return;
            }

            //Kill the fly or make it stronger
            if (tongueSpeed > insectFood.speed) {
                Class<?> _class = insectFood.getClass();

                System.out.println(String.format("My name is %s and I have just eaten a %s", name, _class.getName()));
                grow((int) (insectFood.nutriscore * insectFood.mass));
                insectFood.setMass(0);

            } else {
                System.out.println(String.format("My name is %s and I failed to catch my food. Sniif !", name));
                insectFood.grow(1);
            }
        }
    }

    @Override
    public void grow(int _delta) {
        super.grow(_delta);
    }

    @Override
    public String toString() {
        return String.format("My name is %s and I’m a rare amphibian. I’m %d months old and my tongue has a speed of %.2f.", name, age, tongueSpeed);
    }

    @Override
    public void move(int rowID, int waterlilyID, Row[] pond, double duration) {
        super.move(rowID, waterlilyID, pond, duration);
        if (duration > 0.1) {
            smoothScaleTo(new Vector2Double(1.5, 1.5), duration / 2, true, false);
        }
    }

    /**
     * Listen for animals and insects forward and display theirs
     * responses on the console
     *
     * @param pond the animal observing its forward surroundings.
     */
    @Override
    public String[][] observeForward(Row[] pond) {
        String[][] rowResponses = new String[0][0];
        int pondCoordY = pondGridPosition.y;

        //If there is no more waterlilies row in front of him just leave here
        if (pondCoordY + 1 > pond.length - 1) {
            return rowResponses;
        }

        Waterlily[] waterlilies = pond[pondCoordY + 1].waterlilies;
        rowResponses = new String[waterlilies.length][0];

        for (int i = 0; i < waterlilies.length; i++) {
            rowResponses[i] = waterlilies[i].listenAnimals();
        }
        return (String[][]) RandomOperations.shuffle(rowResponses);
    }
}
