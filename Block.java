import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * A Block object, which has a shape(rectangular) and color.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private Color color;
    private Image blockImg = null;
    private int number; //initialize.
    private List<HitListener> hitListeners;
    private Color stroke;
    private Map<Integer, String> hashFill;


    /**
     * Constructor.
     * <p>
     * the block may be normal block or death block  or be ball producer block.
     * to be death block : probability of 0.025.
     * to be ball producer block : probability of 0.025
     *
     * @param rec      : Block's shape
     * @param stroke   : stroke color(border).
     * @param hashFill : background according to hit points.
     */
    public Block(Rectangle rec, Color stroke, Map<Integer, String> hashFill) {
        this.shape = rec;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<>();
        Random rand = new Random();
        int n = rand.nextInt(130) + 1;
        if (n == 30) { // probability of 1/130
            this.number = -2; //Death block;
        } else if (n == 10) { // probability of 1/130
            this.number = -3; //Live block
        } else { //normal
            this.number = -1;
        }
        this.hashFill = hashFill;


    }

    /**
     * Constructor.
     *
     * @param rec    : block shape.
     * @param color  : block color.
     * @param stroke :  stroke color.
     */
    public Block(Rectangle rec, Color color, Color stroke) {
        this.shape = rec;
        this.color = color;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<>();
        Random rand = new Random();
        int n = rand.nextInt(130) + 1;
        if (n == 30) { // probability of 1/130
            this.number = -2; //Death block;
        } else if (n == 10) { // probability of 1/130
            this.number = -3; //Live block
        } else { //normal
            this.number = -1;
        }
        this.hashFill = null;

    }

    /**
     * Constructor.
     *
     * @param rec : block's shape.
     * @param img : block's img.
     */
    public Block(Rectangle rec, Image img) {
        this.shape = rec;
        this.blockImg = img;
        this.color = null;
        this.stroke = Color.black;
        this.hitListeners = new ArrayList<>();
        this.number = -1;
        this.hashFill = null;
    }


    /**
     * Notify the block that we collided with it at collisionPoint with a given velocity.
     * if collided with it from below or above , turn the vertical direction.
     * if collided with it from left or right , turn the horizontal direction.
     * if collided with it from the it's angles , turn the vertical and horizontal directions.
     *
     * @param collisionPoint  : where an object hit the block.
     * @param currentVelocity : an object velocity.
     * @param hitter          : the ball that hit the block.
     * @return new velocity depends on where the object hits the block.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line upperLine = this.shape.getUpperLine();
        Line lowerLine = this.shape.getLowerLine();
        Line leftLine = this.shape.getLeftLine();
        Line rightLine = this.shape.getRightLine();
        double newDy = 0, newDx = 0;
        //decrease the number of the block when the ball collided with it.
        if (this.number >= 1) {
            this.number = this.number - 1;
            if (this.hashFill != null) { //each hit points result specific background of the block.
                if (this.hashFill.containsKey(this.number)) { //specific hit points.
                    String s = this.hashFill.get(this.number);
                    try { //check if this strings define a color
                        ColorsParser c = new ColorsParser();
                        Color newColor = c.colorFromString(s);
                        if (newColor != null) {
                            this.color = newColor;
                            this.blockImg = null;
                        } else {
                            Image img = null;
                            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
                            this.blockImg = img;
                        }

                    } catch (Exception e) { //if not define a color , define an image.
                        Image img = null;
                        try {
                            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
                            this.blockImg = img;
                        } catch (Exception ex) {
                            this.blockImg = null;
                        }
                    }
                } else { //default fill value.
                    String s = this.hashFill.get(0);
                    try {
                        ColorsParser c = new ColorsParser();
                        Color newColor = c.colorFromString(s);
                        if (newColor != null) {
                            this.color = newColor;
                            this.blockImg = null;
                        } else {
                            Image img = null;
                            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
                            this.blockImg = img;
                        }

                    } catch (Exception e) {
                        Image img = null;
                        try {
                            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
                            this.blockImg = img;
                        } catch (Exception ex) {
                            this.blockImg = null;
                        }
                    }

                }
            }

        }
        //block's angles.
        if (upperLine.onSegment(collisionPoint) && leftLine.onSegment(collisionPoint)
                || upperLine.onSegment(collisionPoint) && rightLine.onSegment(collisionPoint)
                || leftLine.onSegment(collisionPoint) && lowerLine.onSegment(collisionPoint)
                || rightLine.onSegment(collisionPoint) && lowerLine.onSegment(collisionPoint)) {
            newDy = -1 * currentVelocity.getDy();
            newDx = -1 * currentVelocity.getDx();

            // hit the block from above or below.
        } else if (upperLine.onSegment(collisionPoint) || lowerLine.onSegment(collisionPoint)) {
            newDy = -1 * currentVelocity.getDy();
            newDx = currentVelocity.getDx();
            // hit the block from left or right.
        } else if (leftLine.onSegment(collisionPoint) || rightLine.onSegment(collisionPoint)) {
            newDy = currentVelocity.getDy();
            newDx = -1 * currentVelocity.getDx();
        }

        //return the new velocity.
        Velocity newVelocity = new Velocity(newDx, newDy);
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * @return Block's shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * @param num : the number that will be draw on the middle of the block.
     */
    public void setNumber(int num) {
        this.number = num;
        if (num != -1) { //surrounding blocks
            if (this.hashFill != null) {
                if (this.hashFill.containsKey(num)) { //specific fill
                    String s = this.hashFill.get(num);
                    try { //check if the string define a color.
                        ColorsParser c = new ColorsParser();
                        Color newColor = c.colorFromString(s);
                        if (newColor != null) {
                            this.color = newColor;
                            this.blockImg = null;
                        } else {
                            Image img = null;
                            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
                            this.blockImg = img;
                        }

                    } catch (Exception e) {
                        Image img = null;
                        try { //block's image
                            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
                            this.blockImg = img;
                        } catch (Exception ex) {
                            this.blockImg = null;
                        }
                    }
                } else { //default fill
                    String s = this.hashFill.get(0);
                    try {
                        ColorsParser c = new ColorsParser();
                        Color newColor = c.colorFromString(s);
                        if (newColor != null) {
                            this.color = newColor;
                            this.blockImg = null;
                        } else {
                            Image img = null;
                            img = ImageIO.read(new File(s));
                            this.blockImg = img;
                        }

                    } catch (Exception e) {
                        Image img = null;
                        try {
                            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
                            this.blockImg = img;
                        } catch (Exception ex) {
                            this.blockImg = null;

                        }
                    }
                }
            }
        }
    }

    /**
     * draw the block on the given DrawSurface.
     *
     * @param surface : surface to draw the block on it.
     */
    public void drawOn(DrawSurface surface) {
        Point upperLeft = this.shape.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        Rectangle rec = this.shape;
        Point p1 = rec.getUpperLeft();

        //death block
        if (this.number == -2) {
            surface.setColor(Color.red);
            surface.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.black);
            surface.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.white);
            surface.drawText((int) p1.getX() + (int) (this.getWidth() / 11)
                    , (int) p1.getY() + (int) (this.getHeight() * 0.65), "DEATH", 9);
        } else if (this.number == -3) { //ball producer block
            surface.setColor(Color.green);
            surface.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.black);
            surface.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.white);
            surface.drawText((int) p1.getX() + (int) (this.getWidth() / 5)
                    , (int) p1.getY() + (int) (this.getHeight() * 0.65), "BALL", 9);

        } else { //normal block
            if (this.blockImg != null) {
                surface.drawImage(x, y, this.blockImg);
            } else if (this.color != null) {
                surface.setColor(this.color);
                surface.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());

            }
            if (this.stroke != null) {
                surface.setColor(this.stroke);
                surface.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            }


        }
    }

    /**
     * notify the block that time has passed.
     * @param dt : seconds passed since the last call, ignored here.
     */
    public void timePassed(double dt) {
    }

    /**
     * adding the block to specified game.
     *
     * @param g : the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * remove this block from the game.
     *
     * @param gameLevel : game .
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * add hit listener to the listeners.
     *
     * @param hl : hit listener will be added.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * remove hit listener from the listeners.
     *
     * @param hl : hit listener will be removed.
     */
    public void removeHitListener(HitListener hl) {
        try {
            this.hitListeners.remove(hl);
        } catch (Exception e) { //not found in the collection of the listeners.
            System.out.println("this listener is not in the block's hit listeners");
        }

    }

    /**
     * will be called whenever a hit() occurs.
     * and notifiers all of the registered HitListener objects by calling their hitEvent method
     *
     * @param hitter : ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * @return block points.
     */
    public int getHitPoints() {
        return this.number;
    }

    /**
     * @return block's width.
     */
    public double getWidth() {
        return this.shape.getWidth();
    }

    /**
     * @return block's height.
     */
    public double getHeight() {
        return this.shape.getHeight();
    }

    /**
     * @return block's Color.
     */
    public Color getColor() {
        if (this.color != null) {
            return this.color;
        }
        return null;
    }
}

