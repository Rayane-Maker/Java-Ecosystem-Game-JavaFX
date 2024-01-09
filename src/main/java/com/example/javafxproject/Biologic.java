package com.example.javafxproject; /**
 * <b>This class inherits from GameObject class and regroups all objects that can be eaten.</b>
 *
 * @author Theo Thuiller
 * @version 1.0
 */
import Mathf.Vector2Int;

public class Biologic extends GameObject{
    public Vector2Int pondGridPosition;
    protected int nutriscore;

    public Biologic(){
        super();
        pondGridPosition = new Vector2Int(0, 0);
    }

    public void setNutriscore(int nutriscore){this.nutriscore = nutriscore < 0 ? this.nutriscore : nutriscore;}

}
