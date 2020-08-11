import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * A SpriteCollection class.
 * hold a collection of sprites.
 *
 * @author : Ganaiem Hosny
 */
public class SpriteCollection {
    private List sprites;

    /**
     * Constructor.
     * initialize the sprites list.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList();
    }


    /**
     * Add sprite to the sprites collection.
     *
     * @param s sprite to be add to the collection.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }


    /**
     * Call timePassed() on all sprites.
     * @param dt : seconds passed since the last call;
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = (Sprite) sprites.get(i);
            sprite.timePassed(dt); //call timePassed.
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d : a surface to draw the sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = (Sprite) sprites.get(i);
            sprite.drawOn(d); //call drawOn
        }
    }

    /**
     * remove sprite from this collection.
     *
     * @param s : sprite will be removed.
     */
    public void removeSprite(Sprite s) {
        try { //if this sprite is in the collection.
            this.sprites.remove(s);
        } catch (Exception e) {
            System.out.println("this sprite is not in the collection!");
        }
    }
}