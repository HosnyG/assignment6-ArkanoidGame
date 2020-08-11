
/**
 * A velocity class.
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author : Ganaiem Hosny
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     * specify the velocity in terms and angle and a speed.
     * conversion to radians.
     *
     * @param angle : degree.
     * @param speed : perform steps.
     * @return the velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //To radians.
        double dx = Math.sin(Math.PI * angle / 180) * speed;
        double dy = Math.cos(Math.PI * angle / 180) * speed * -1;

        return new Velocity(dx, dy);
    }

    /**
     * Constructor.
     * given the dx and dy.
     *
     * @param dx : change in position on the x axis.
     * @param dy : change in position on the y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

    }

    /**
     * @return change in position on the x axis.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return change in position on the y axis.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p : point that will be changed.
     * @return (x + dx, y + dy) while p=(x,y).
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx;
        double newY = p.getY() + this.dy;
        Point newPoint = new Point(newX, newY);
        return newPoint;
    }

    /**
     * new velocity according to the dt.
     * @param dt : seconds passed since the last call;
     * @return new velocity.
     */
    public Velocity scale(double dt) {
        return new Velocity(dx * dt, dy * dt);
    }
}