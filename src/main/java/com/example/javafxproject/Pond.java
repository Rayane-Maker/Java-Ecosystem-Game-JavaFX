package com.example.javafxproject;

import Mathf.Vector2Double;
import Mathf.Vector2Int;
import StringUtil.StringColor;

import java.util.stream.IntStream;

import java.io.RandomAccessFile;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import static java.lang.Math.random;

public class Pond {

    final static int ROW_COUNT_MIN = 3, ROW_COUNT_MAX = 8;
    final static int WATERLILIES_COUNT_MIN = 2, WATERLILIES_COUNT_MAX = 5;

    final static int WATERLILIES_CAPACITY_MIN = 3, WATERLILIES_CAPACITY_MAX = 5;

    final static int INSECT_TYPE_COUNT = 3;
    final static int FOOD_TYPE_COUNT = 2;
    final static int FOOD_COUNT_MIN = 1, FOOD_COUNT_MAX = 3;
    final static int FOOD_APPARITION_CHANCE = 50, INSECT_APPARITION_CHANCE = 100; //In percentage

    final static Double SPACE_BETWEEN_ROW = 5.0, SPACE_BETWEEN_WATERLILIES = 3.0;

    final static int MAX_TOUR_WITHOUT_EAT = 2;

    public static Row[] pond;

    public static Animal[] animals;

    final static String quitCommand = "p";

    final static String eatCommand = "e";
    static Amphibian player;
    static Row nextRow;
    static int score = 0;
    static  boolean canEat = true;

    static int noEatCount = 0;
    static  boolean canJump = true;
    static int scoreMultiplier = 10;

    static boolean gameFinished = false;


    /***************************************** Game Lifecycle ***********************************/
    private static boolean quitGame;
    public static void main(String[] args){
        onStart();

        while (!quitGame) {
            onLoop();
        }

    }


    /**
     * Game initializations
     */
    public static void onStart() {

        gamePresentation();

        /* // Generate and populate the pond (Automatic/Random approach) // */
        pond = GeneratePond();
        println(String.format("There are %d waterlilies rows", pond.length));

        //Ask the player for a name for its amphibian character
        println("\nEnter your name : \n");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        playerInit(userInput, new Vector2Int(0,0),  1f/12, 15);

    }

    /**
     * Game loop mechanic
     */
    public static void onLoop() {

        /* /////////////////////////// Updates and display indications  ////////////////////////////// */
        displayIndications();


        /* ////////////////////////////////// Player Actions ///////////////////////////////////////// */
        boolean validInput = false;
        //Scan user keyboard inputs
        while (!validInput) {
            Scanner scanner = new Scanner(System.in);

            //The player choose to move the player
            if (scanner.hasNextInt()) {
                if (canJump) {
                    int waterlilyChoice = scanner.nextInt();
                    if (waterlilyChoice <= nextRow.waterlilies.length && waterlilyChoice > 0) {
                        player.move(player.pondGridPosition.y + 1, waterlilyChoice - 1, pond);
                        canEat = true;
                        noEatCount++;
                        validInput = true;
                    } else {
                        println("Please enter a valid waterlily choice number");
                    }
                }else{
                    println("It's time to eat ! You cannot jump !");
                }
            } else {
                String userInput = scanner.nextLine();


                //The player choose to eat :
                if (Objects.equals(userInput.toLowerCase(), eatCommand) && canEat) {
                    eatAction();
                    canEat = false;
                    noEatCount = 0;
                    canJump = true;
                    validInput = true;
                }


                //The player choose to early quit the game :
                if (Objects.equals(userInput.toLowerCase(), quitCommand)) {
                    earlyQuitGame();
                    validInput = true;
                }
            }
        }

        //Check if the player has reached the max tour without eat
        if (noEatCount >= MAX_TOUR_WITHOUT_EAT) {
            canJump = false;
            canEat = true;
        }

        System.out.println(noEatCount);

        //Check if the player has reach the top of the pond
        if (player.pondGridPosition.y == pond.length - 1) {
            finishGame();
        }

        //Check if the player loose the game
        if (player.tongueSpeed == 0) {
            loseGame();
        }

    }


    ////////////////////////////////////////////  Functions definitions ////////////////////////////////////////////


    // ********************** Game start functions ************************** //

    /**
     * This method :
     * - display game information (Title screen, version, etc...)
     * - load and display the game manual
     */
    public static void gamePresentation() {

        println("\nJava Ecosystem Game (Console Version)\n");

        /* //Load Game Instructions // */
        StringBuilder instructions = new StringBuilder();
        if (readFile("gameManual.txt", instructions)) {
            println(instructions.toString());
        }
    }


    /**
     * Instantiate a player (Amphibian) and place it
     * @param userName
     *            The name of the player during the game.
     * @param pondGridPosition
     *            Waterlily position where the player will be at start
     * @param ageInYears
     *             Age of the amphibian player, affect its growing behavior.
     * @param tongueSpeed
     *             Tongue speed of the amphibian player. Affect the ability to catch a fast insect.
     */
    public static void playerInit(String userName, Vector2Int pondGridPosition, double ageInYears, int tongueSpeed) {
        player = new Frog(userName, ageInYears, tongueSpeed);
        player.move(pondGridPosition.y, pondGridPosition.x,  pond);
    }


    /**
     * This method generates an array of waterlilies rows
     * that embody the pond itself in terms of content :
     * - Waterlilies (quantities and position)
     * - Insects (quantities and distribution on waterlilies)
     * - Static Foods (quantities and distribution on waterlilies)
     * They are all generated in a random way.
     */
    public static Row[] GeneratePond() {
        //Rows of waterlilies
        int rowsCount = (int) rand(ROW_COUNT_MIN, ROW_COUNT_MAX);
        Row[] rows = new Row[rowsCount];
        for (int rowId = 0; rowId < rowsCount; rowId++) {

            //Row waterlilies generation
            int waterliliesCount = (int) rand(WATERLILIES_COUNT_MIN, WATERLILIES_COUNT_MAX + 1);
            Waterlily[] waterlilies = new Waterlily[waterliliesCount];
            double posY = rowId * SPACE_BETWEEN_ROW;;
            for (int wId = 0; wId < waterliliesCount; wId++) {
                double posX = (wId + (waterliliesCount - 1) / 2.0) * SPACE_BETWEEN_WATERLILIES;

                waterlilies[wId] = new Waterlily((int) rand(WATERLILIES_CAPACITY_MIN, WATERLILIES_CAPACITY_MAX), new Vector2Int(wId, rowId));


                //Static food generation
                if (rand(0, 100) <= FOOD_APPARITION_CHANCE && rowId != 0) {
                    int foodCount = (int) rand(FOOD_COUNT_MIN, FOOD_COUNT_MAX);

                    for (int foodId = 0; foodId < foodCount; foodId++) {
                        Random rnd = new Random();
                        int foodType = rnd.nextInt(FOOD_TYPE_COUNT);
                        StaticFood staticFood = switch (foodType) {
                            case 0 -> new Flower();
                            case 1 -> new Poo();
                            default -> new StaticFood(2);
                        };
                        waterlilies[wId].addFood(staticFood);
                    }

                }


                /*Insect generation, only one insect is generated per waterlily */
                Insect insect;
                if (rand(0, 100) <= INSECT_APPARITION_CHANCE && rowId != 0) {
                    Random rnd = new Random();
                    int insectType = rnd.nextInt(INSECT_TYPE_COUNT);
                    switch (insectType) {
                        case 0:
                            insect = new Fly(4, rand(1,20));
                            break;
                        case 1:
                            insect = new Bee(5, rand(1,4));
                            break;
                        case 2:
                            insect = new Dragonfly(3, rand(1,8));
                            break;
                        default:
                            insect = new Fly();
                    }
                    insect.position = new Vector2Double(posX, posY);
                    waterlilies[wId].addAnimal(insect);
                }


                //Set positions (for graphic version)
                waterlilies[wId].position.x = posX;
                waterlilies[wId].position.y = posY;

            }

            //Populate row of generated waterlilies
            rows[rowId] = new Row(waterlilies);

            //Set position (for graphic version)
            rows[rowId].position.x = 0.0;
            rows[rowId].position.y = rowId * SPACE_BETWEEN_ROW;

        }
        return rows;
    }



    // ************************* In game loop functions ************************** //
    /**
     * This method is meant to display at each game tour :
     * - The player state
     * - The player observation about what animals are in front of him
     * - The possibilities of actions that the player can do for this tour
     */
    public static void displayIndications() {
        displayPlayerState();
        displayPlayerObservations();
        displayActionsPossibilities();
    }

    public static void displayPlayerState(){
        System.out.print(StringColor.ANSI_GREEN);
        System.out.print(player);
        println(String.format("\nI am at the row number %d. I hear voices coming from the next row: \n", player.pondGridPosition.y + 1));
        System.out.print(StringColor.ANSI_RESET);
    }

    public static void displayPlayerObservations(){
        String[][] allVoices = player.observeForward(pond);
        for (String[] waterlilyVoices : allVoices) {
            for (String voice : waterlilyVoices) {
                System.out.print(StringColor.ANSI_BLUE);
                println(voice);
                System.out.print(StringColor.ANSI_RESET);
            }
        }
    }


    public static void displayActionsPossibilities(){
        println("\nEnter 'e' to try to eat insects on your waterlily. You can enter one of the following waterlily number to pass and jump on the corresponding waterlily : ");
        nextRow = pond[player.pondGridPosition.y + 1];
        IntStream.rangeClosed(1, nextRow.waterlilies.length).forEachOrdered((i) -> System.out.printf(".%d   ", i));
        println("");
    }



    //Player actions
    public static void eatAction(){
        Animal[] animals = player.currentWaterlily.getAnimals();
        if (animals.length > 1) {

            Animal animalToEat = null;
            for (Animal animal : animals) {
                if (animal != player) {
                    animalToEat = animal;

                    if (rand(0, 100) < 40) {
                        break;
                    }
                }
            }

            if (animalToEat != null) {
                score += animalToEat.isDead() ? 0 : animalToEat.nutriscore;
                player.eat(animalToEat);
            }
        }
    }



    public static void earlyQuitGame(){
        quitGame = true;
    }

    public static void finishGame(){
        println(String.format("Your score : %d", score));
        quitGame = true;
    }

    public static void loseGame(){
        println(String.format("Your are dead ! Score : %d", score));
        quitGame = true;
    }



    static void println(String s){
        System.out.println(s);
    };

    //Custom rand function allowing range from min and max
    public static double rand(double min, double max){
        double f = random()/Math.nextDown(1.0);
        return min*(1.0 - f) + max*f;
    }

    public static boolean readFile(String path, StringBuilder output) {
        boolean success = false;
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            String line;
            while((line = file.readLine()) != null) {
                output.append(line);
                output.append("\n");
            }
            success = true;
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
