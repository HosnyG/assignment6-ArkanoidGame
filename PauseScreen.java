import biuoop.DrawSurface;
import java.awt.Color;

/**
 * PauseScreen class.
 * pause the screen and display a appropriate message on the screen.
 */
public class PauseScreen implements Animation {

    /**
     * draw pause message on the screen.
     *
     * @param d : given surface.
     * @param dt : seconds passed since the last call;
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.darkGray);
        d.drawCircle(400, 300, 105);
        d.setColor(Color.white);
        d.fillCircle(400, 300, 104);
        d.setColor(Color.blue);
        d.fillCircle(400, 300, 96);
        d.setColor(Color.gray);
        d.fillCircle(400, 300, 93);
        d.setColor(Color.black);
        d.fillCircle(400, 300, 90);
        d.setColor(Color.blue);
        d.fillRectangle(350, 242, 40, 115);
        d.fillRectangle(410, 242, 40, 115);
        d.setColor(Color.BLUE);
        d.drawText(247, 472, "Press space to continue", 30);
        d.setColor(Color.black);
        d.drawText(245, 470, "Press space to continue", 30);

    }

    /**
     * @return true if pressed continue , flase otherwise.
     */
    public boolean shouldStop() {
        return false;
    }
}