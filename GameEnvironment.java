import java.util.ArrayList;
import java.util.List;

/**
 * A GameEnvironment will be a collection of objects that a Ball can collide with .
 *
 * @author Ganaiem Hosny
 */
public class GameEnvironment {

    private List collidables;

    /**
     * Constructor.
     * initialize the list (collection) of collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList();
    }

    /**
     * Add collidable to the collidables collection.
     *
     * @param c : collidable to be add to the collection.
     */
    public void addCollidable(Collidable c) {

        this.collidables.add(c);
    }

    /**
     * @return collidables collection.
     */
    public List getCollection() {
        return this.collidables;
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory : the path of the object(Ball).
     * @return collision's information, point and the object.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point startPoint = trajectory.start();
        Point closestPoint = null; //initialize.
        Collidable collisionObject = null; //initialize.
        Point[] collisionPoints = new Point[collidables.size()];

        /*
        get the closest intersection point To the start of trajectory for
        each collidable.
         */
        for (int i = 0; i < collidables.size(); i++) {
            Collidable currCollidable = (Collidable) collidables.get(i);
            Rectangle shape = currCollidable.getCollisionRectangle();
            collisionPoints[i] = trajectory.closestIntersectionToStartOfLine(shape);
        }


        // get the closest point to the start of the trajectory line.
        for (int i = 0; i < collisionPoints.length; i++) {
            //there is no intersection
            if (collisionPoints[i] == null) {
                continue;
            }
            //temporary closest point
            if (closestPoint == null) {
                closestPoint = collisionPoints[i];
                collisionObject = (Collidable) collidables.get(i);
                continue;
            }
            //if there are point more closest
            if (collisionPoints[i].distance(startPoint) < closestPoint.distance(startPoint)) {
                closestPoint = collisionPoints[i];
                collisionObject = (Collidable) collidables.get(i);
            }
        }
        //there is no any intersection for each collidables.
        if (closestPoint == null) {
            return null;
        }

        //keep the intersection point and the collidable object.
        CollisionInfo collision = new CollisionInfo(closestPoint, collisionObject);
        return collision;

    }

    /**
     * remove given collidable from this environment.
     *
     * @param c : given collidable.
     */
    public void removeCollidable(Collidable c) {
        try { //if this collection contain this collidable .
            this.collidables.remove(c);
        } catch (Exception e) {
            System.out.println("this collidable is not in the GameEnvironment");
        }
    }

}