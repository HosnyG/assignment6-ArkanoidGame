import biuoop.DrawSurface;

import java.awt.Color;

/**
 * A ball (circle) in frame.
 *
 * @author : Ganaiem Hosny
 */
public class Ball implements Sprite {

    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private double rightBoundary, leftBoundary, bottomBoundary, topBoundary;
    private GameEnvironment gameEnvironment;

    /**
     * Constructor.
     * given the center point,radius and color.
     *
     * @param center : ball's center point.
     * @param r      :  ball's radius.
     * @param color  : ball's color.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    /**
     * Constructor.
     * given the x and y coordinates of the center point of the ball,
     * radius and color.
     *
     * @param x     : x-coordinate of the ball's center point.
     * @param y     : y-coordinate of the ball's center point.
     * @param r     :  ball's radius.
     * @param color : ball's color.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        Point centerPoint = new Point(x, y);
        this.center = centerPoint;
        this.radius = r;
        this.color = color;
    }

    /**
     * @return x-coordinate of the ball's center point.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return y-coordinate of the ball's center point.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return ball's radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return ball's color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface : draw surface to draw the ball on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * set velocity to the ball.
     * given the velocity.
     *
     * @param v : the velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * set velocity to the ball.
     * given the change in position on the `x` and the `y` axes.
     *
     * @param dx : change in position on the x axis.
     * @param dy : change in position on the y axis.
     */
    public void setVelocity(double dx, double dy) {
        Velocity v = new Velocity(dx, dy);
        this.velocity = v;
    }

    /**
     * @return ball's velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * set the frame's boundaries that the ball resides
     * between them.
     *
     * @param top    : frame's top boundary.
     * @param bottom : frame's bottom boundary.
     * @param right  : frame's  right boundary.
     * @param left   :  frame's left boundary.
     */
    public void setBoundaries(double top, double bottom, double right, double left) {
        this.topBoundary = top;
        this.bottomBoundary = bottom;
        this.rightBoundary = right;
        this.leftBoundary = left;
    }

    /**
     * set the ball's GameEnvironment.
     *
     * @param environment : game environment.
     */
    public void setEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }

    /**
     * move the the ball one step according to it's velocity.
     * guided by the obstacles in the game environment.
     * - make sure that the ball will not exceeded the screen boundaries.
     * -  compute the ball trajectory.
     * - Checking if moving on this trajectory will hit anything,
     * If no, then move the ball to the end of the trajectory.
     * Otherwise:
     * - moving the ball to "almost" the hit point.
     * - notify the hit object that a collision occurred.
     * - update the velocity to the new velocity returned.
     *
     * @param dt : seconds passed since the last call.
     */
    public void moveOneStep(double dt) {

        //restrict it from the right and left
        if (this.center.getX() >= this.rightBoundary || this.center.getX() <= this.leftBoundary) {
            this.setVelocity(-(this.velocity.getDx()), this.velocity.getDy()); //Changing the horizontal direction
            this.center = this.velocity.applyToPoint(this.center);
            return;
        }
        //restrict it from above and below
        if (this.center.getY() >= this.bottomBoundary || this.center.getY() <= this.topBoundary) {
            this.setVelocity((this.velocity.getDx()), -(this.velocity.getDy())); //Changing the vertical direction.
            this.center = this.velocity.applyToPoint(this.center);
            return;
        }
        Velocity scaledVelocity = this.velocity.scale(dt);
        Point targetPoint = scaledVelocity.applyToPoint(this.center);

        //starting at current location and ending where the velocity will take the ball if no collisions will occur.
        Line trajectory = new Line(this.center, targetPoint);
        //get information about the closest collision will occur if the ball move one step.
        CollisionInfo collision = this.gameEnvironment.getClosestCollision(trajectory);
        if (collision == null) { // there is no collision , so move one step according to velocity.
            this.center = trajectory.end();
            //there will be a collision if the ball move one step according to its velocity.
        } else {
            Point collisionPoint = collision.collisionPoint();
            Collidable collisionObject = collision.collisionObject();
            //get collision object's shape.
            Rectangle rec = collisionObject.getCollisionRectangle();
            //constant
            Double c = 0.0001;
            //get object lengths.
            Line upperLine = rec.getUpperLine();
            Line lowerLine = rec.getLowerLine();
            Line rightLine = rec.getRightLine();
            Line leftLine = rec.getLeftLine();

            /*
            moving the ball to "almost" the hit point
             */
            if (upperLine.onSegment(collisionPoint)) {
                this.center = new Point(collisionPoint.getX(), collisionPoint.getY() - c);
            }
            if (lowerLine.onSegment(collisionPoint)) {
                this.center = new Point(collisionPoint.getX(), collisionPoint.getY() + c);
            }
            if (rightLine.onSegment(collisionPoint)) {
                this.center = new Point(collisionPoint.getX() + c, collisionPoint.getY());
            }
            if (leftLine.onSegment(collisionPoint)) {
                this.center = new Point(collisionPoint.getX() - c, collisionPoint.getY());
            }
            //update the velocity.
            Velocity newVelocity = collisionObject.hit(this, collisionPoint, this.velocity);
            this.velocity = newVelocity;

        }
    }

    /**
     * notify the ball that time has passed.
     * @param dt : seconds passed since the last call.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * add the ball to the specified game.
     *
     * @param g : the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * remove this ball from given game.
     *
     * @param g : game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

}