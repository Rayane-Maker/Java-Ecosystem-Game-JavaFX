package Mathf;

public class Vector2Double {
    public Double x;
    public Double y;



    public Vector2Double(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public Vector2Double(int x, int y){
        this.x = (double) x;
        this.y = (double) y;
    }

    public Double getX(){
        return this.x;
    }

    public Double getY(){
        return this.y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public static Double distance (Vector2Double vec1, Vector2Double vec2){
        return Math.sqrt((vec1.x - vec2.x) * (vec1.x - vec2.x) + (vec1.y - vec2.y) * (vec1.y - vec2.y));
    }
}
