/**
 * A CollisionInfo class.
 * information about collision.
 *
 * @author : Ganaiem Hosny
 */

public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructor.
     *
     * @param collisionPoint  : the point at which the collision occurs.
     * @param collisionObject : the collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Get the collision Point.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Get the collision object.
     *
     * @return the collidable object involved in the collisions.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}