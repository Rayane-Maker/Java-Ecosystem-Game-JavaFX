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

    public static Vector2Double add(Vector2Double a, Vector2Double b) {
        return new Vector2Double(a.x + b.x, a.y + b.y);
    }

    public static Vector2Double subtract(Vector2Double a, Vector2Double b) {
        return new Vector2Double(a.x - b.x, a.y - b.y);
    }

    public static Vector2Double multiply(Vector2Double v, double scalar) {
        return new Vector2Double(v.x*scalar, v.y*scalar);
    }
    public static Vector2Double divide(Vector2Double v, double scalar) {
        return new Vector2Double(v.x/scalar, v.y/scalar);
    }

    public Vector2Double add(Vector2Double v) {
        return new Vector2Double(x + v.x, y + v.y);
    }

    public Vector2Double subtract(Vector2Double v) {
        return new Vector2Double(x - v.x, y - v.y);
    }

    public Vector2Double multiply(double scalar) {
        return new Vector2Double(this.x * scalar, this.y * scalar);
    }


    public Vector2Double divide(double scalar) {
        return new Vector2Double(this.x / scalar, this.y / scalar);
    }

    public Double getMagnitude (){
        return Math.sqrt(x*x + y*y);
    }

    public static Double distance (Vector2Double v1, Vector2Double v2){
        return Math.sqrt((v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y) * (v1.y - v2.y));
    }
}
