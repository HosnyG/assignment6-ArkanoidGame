import java.util.ArrayList;
import java.util.List;

/**
 * A Rectangle that aligned with the axes.
 *
 * @author : Ganaiem Hosny
 */
class Rectangle {

    private Point upperLeft;
    private double width;
    private double height;
    private Line upperLine, lowerLine, leftLine, rightLine;

    /**
     * Constructor.
     * Create a new rectangle with location and width/height,
     * and set his lengths.
     *
     * @param upperLeft : rectangle's upper left point.
     * @param width     : rectangle width.
     * @param height    : rectangle height.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.setRectangleLines();
    }

    /**
     * Set rectangle lines(lengths).
     * by calculations on rectangle's upper left point.
     */
    private void setRectangleLines() {
        double x = upperLeft.getX();
        double y = upperLeft.getY();
        this.upperLine = new Line(x, y, x + this.width, y);
        this.lowerLine = new Line(x, y + this.height, x + this.width, y + this.height);
        this.leftLine = new Line(x, y, x, y + this.height);
        this.rightLine = new Line(x + this.width, y, x + this.width, y + this.height);
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     * using line's intersection functions.
     *
     * @param line : the line to find intersection points between it and this rectangle.
     * @return List of intersection points with the specified line
     */
    public java.util.List intersectionPoints(Line line) {
        Line[] rectanglesLines = {upperLine, lowerLine, leftLine, rightLine};
        List intersectionPointsList = new ArrayList(); //initialize the list.
        //get the intersection point between the specified line and every rectangle's lengths if there is.
        for (int i = 0; i < rectanglesLines.length; i++) {
            if (rectanglesLines[i].isIntersecting(line)) {
                intersectionPointsList.add(rectanglesLines[i].intersectionWith(line));
            }
        }

        /*if there are more than two intersection points ,
         so there are two equal points, and according to
         the intersectionPointsList array , the third point
         and up are equal to the first or the second point,
         so we can remove them.
          */
        if (intersectionPointsList.size() == 3) {
            intersectionPointsList.remove(2); //remove third point.
        } else if (intersectionPointsList.size() == 4) {
            intersectionPointsList.remove(3); //remove 4th point.
            intersectionPointsList.remove(2); //remove third point.
        }
        //check if the two points we have are equal , if yes,remove one.
        if (intersectionPointsList.size() == 2) {
            Point p1 = (Point) intersectionPointsList.get(0);
            Point p2 = (Point) intersectionPointsList.get(1);
            if (p1.equals(p2)) {
                intersectionPointsList.remove(1);
            }
        }
        return intersectionPointsList;
    }

    /**
     * @return rectangle's width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return rectangle's height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return rectangle's upper left point.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return rectangle's upper line.
     */
    public Line getUpperLine() {
        return this.upperLine;
    }

    /**
     * @return rectangle's lower line.
     */
    public Line getLowerLine() {
        return this.lowerLine;
    }

    /**
     * @return rectangle's left line.
     */
    public Line getLeftLine() {
        return this.leftLine;
    }

    /**
     * @return rectangle's right line.
     */
    public Line getRightLine() {
        return this.rightLine;
    }

}