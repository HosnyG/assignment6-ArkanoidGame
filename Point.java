/**
 * A Point in coordinate plane .
 *
 * @author Ganaiem Hosny
 */
public class Point {

    private double x;
    private double y;


    /**
     * Constructor.
     *
     * @param x : x-coordinate.
     * @param y : y-coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * calculate the distance from this point to another.
     * using the Distance Formula.
     *
     * @param other calculate the distance between it and current point.
     * @return the distance between the two points.
     */
    public double distance(Point other) {
        double x1 = this.x;
        double x2 = other.getX();
        double y1 = this.y;
        double y2 = other.getY();

        //Distance Formula
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    /**
     * check if this point and other are equal.
     *
     * @param other the point to compare with.
     * @return true if they equal , false otherwise.
     */
    public boolean equals(Point other) {
        double x1 = this.x;
        double x2 = other.getX();
        double y1 = this.y;
        double y2 = other.getY();
        //two points are equal if their x and y are equal.
        if (x1 == x2 && y1 == y2) {
            return true;
        }
        return false;
    }

    /**
     * @return x-coordinate of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return y-coordinate of the point.
     */
    public double getY() {
        return this.y;
    }

}