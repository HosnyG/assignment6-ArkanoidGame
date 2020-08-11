import biuoop.DrawSurface;

import java.awt.Color;

/**
 * A paddle is a rectangle that is controlled by the arrow keys.
 * moves according to the player key presses.
 *
 * @author Ganaiem Hosny
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle shape;
    private java.awt.Color color;
    private biuoop.KeyboardSensor keyboard;
    private double move;
    private double rightBoundary, leftBoundary;


    /**
     * Constructor.
     * create a new Paddle with rectangle shape,Keyboard sensor and move.
     *
     * @param rec      : paddle's shape.
     * @param color    : paddle's color.
     * @param keyboard : Keyboard Sensor.
     * @param move     : paddle's move.
     */
    public Paddle(Rectangle rec, java.awt.Color color, biuoop.KeyboardSensor keyboard, double move) {
        this.shape = rec;
        this.color = color;
        this.keyboard = keyboard;
        this.move = move;
    }

    /**
     * set the paddle's boundaries in order to move just between them.
     *
     * @param surroundingBlocksThickness : the width of the surrounding blocks on the screen.
     * @param screenWidth                : the of the screen that the paddle will be draw on it.
     */
    public void setBoundaries(double surroundingBlocksThickness, int screenWidth) {
        this.leftBoundary = surroundingBlocksThickness;
        this.rightBoundary = screenWidth - surroundingBlocksThickness;
    }

    /**
     * move the paddle to left.
     * according to it's move.
     *
     * @param dt : seconds passed since the last call.
     */
    public void moveLeft(double dt) {
        Point currUpperLeft = this.shape.getUpperLeft();
        Point newUpperLeft = new Point(currUpperLeft.getX() - (this.move * dt), currUpperLeft.getY());

        /*not exceed the boundaries.
        if one step will take them left to the left boundary ,
        moving them to left boundary.
         */
        if (newUpperLeft.getX() < leftBoundary) {
            newUpperLeft = new Point(leftBoundary, currUpperLeft.getY());
        }
        Rectangle newShape = new Rectangle(newUpperLeft, this.shape.getWidth(), this.shape.getHeight());
        this.shape = newShape;
    }

    /**
     * move the paddle to right.
     * according to it's move.
     *
     * @param dt : seconds passed since the last call.
     */
    public void moveRight(double dt) {
        Point currUpperLeft = this.shape.getUpperLeft();
        Point newUpperLeft = new Point(currUpperLeft.getX() + (this.move * dt), currUpperLeft.getY());

        /*not exceed the boundaries.
        if one step will take them right to the right boundary ,
        moving them to right boundary.
         */
        if (newUpperLeft.getX() + this.shape.getWidth() > rightBoundary) {
            newUpperLeft = new Point(rightBoundary - this.shape.getWidth(), currUpperLeft.getY());
        }
        Rectangle newShape = new Rectangle(newUpperLeft, this.shape.getWidth(), this.shape.getHeight());
        this.shape = newShape;
    }

    /**
     * move the paddle right or left according to user keyboard press.
     * using keyboard sensor.
     *
     * @param dt : seconds passed since the last call.
     */
    public void timePassed(double dt) {
        if (this.keyboard.isPressed(keyboard.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        if (this.keyboard.isPressed(keyboard.RIGHT_KEY)) {
            this.moveRight(dt);
        }
    }

    /**
     * draw the paddle on the given DrawSurface.
     *
     * @param d : surface to draw the paddle on it.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Point upperLeft = this.shape.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        d.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
        d.setColor(Color.black);
        //frame
        d.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
    }

    /**
     * @return paddles shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Notify the paddle that we collided with it at collisionPoint with a given velocity.
     * if collided with it from below , turn the vertical direction.
     * if collided with it from left or right , turn the horizontal direction.
     * we divide the upper line of the paddle to five equal regions left to right.
     * <p>
     * if collided with region 1 bounce back with an angle of -60 degrees.
     * if collided with region 2 bounce back with an angle of -30 degrees.
     * if collided with region 3 turn the vertical direction.
     * if collided with region 4 bounce back with an angle of 30 degrees.
     * if collided with region 4 bounce back with an angle of 60 degrees
     *
     * @param hitter          : the ball that hit the paddle.
     * @param collisionPoint  where an object hit the paddle.
     * @param currentVelocity an object velocity.
     * @return new velocity depends on where the object hits the paddle.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //get the paddle lengths.
        Line upperLine = this.shape.getUpperLine();
        Line lowerLine = this.shape.getLowerLine();
        Line leftLine = this.shape.getLeftLine();
        Line rightLine = this.shape.getRightLine();
        double newDy, newDx;
        Velocity newVelocity = null; //initialize

        //if the object hit paddle from above.
        if (upperLine.onSegment(collisionPoint)) {
            double lineLength = upperLine.length();
            double regionLength = lineLength / 5; //divide to five equal regions.
            double x = upperLine.start().getX();
            double y = upperLine.start().getY();
            double currSpeed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                    + currentVelocity.getDy() * currentVelocity.getDy());
            double newSpeed = -1; //initialize

            Line[] regions = new Line[5];
            //initialize the regions , all are equal .
            for (int i = 0; i < regions.length; i++) {
                regions[i] = new Line(x + (regionLength * i), y, x + (regionLength * (i + 1)), y);
            }

            /*
            return a new velocity according to where the object hit the paddle.
            as long as an angle is acute , it the object gain more speed.
             */
            if (regions[0].onSegment(collisionPoint)) {
                newVelocity = Velocity.fromAngleAndSpeed(300, currSpeed + 0.2);
            } else if (regions[1].onSegment(collisionPoint)) {
                newVelocity = Velocity.fromAngleAndSpeed(330, currSpeed + 0.1);
            } else if (regions[2].onSegment(collisionPoint)) {
                newVelocity = new Velocity(currentVelocity.getDx(), -1 * currentVelocity.getDy());
            } else if (regions[3].onSegment(collisionPoint)) {
                newVelocity = Velocity.fromAngleAndSpeed(30, currSpeed + 0.1);
            } else if (regions[4].onSegment(collisionPoint)) {
                newVelocity = Velocity.fromAngleAndSpeed(60, currSpeed + 0.2);
            }

            //if the object hit the paddle from below , turn the vertical direction.
        } else if (lowerLine.onSegment(collisionPoint)) {
            newDy = -1 * currentVelocity.getDy();
            newDx = currentVelocity.getDx();
            newVelocity = new Velocity(newDx, newDy);

            //if the object hit the paddle from right or left , turn the horizontal direction.
        } else if (leftLine.onSegment(collisionPoint) || rightLine.onSegment(collisionPoint)) {
            newDy = currentVelocity.getDy();
            newDx = -1 * currentVelocity.getDx();
            newVelocity = new Velocity(newDx, newDy);
        }

        return newVelocity;
    }


    /**
     * adding the paddle to specified game.
     *
     * @param g : the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * remove this paddle from game.
     *
     * @param gameLevel : the game .
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

}