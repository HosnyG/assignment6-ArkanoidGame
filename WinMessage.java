import biuoop.DrawSurface;

import java.awt.Color;

/**
 * win screen class.
 */
public class WinMessage implements Animation {
    private int score;

    /**
     * Constructor.
     *
     * @param score : player score.
     */
    public WinMessage(int score) {
        this.score = score;
    }

    /**
     * draw win message screen.
     * @param d : given surface.
     * @param dt : seconds passed since the last call;
     */
    public void doOneFrame(DrawSurface d, double dt) {
        int screenWidth = 800;
        int screenHeight = 600;
        d.setColor(new Color(0.902f, 0.902f, 0.980f));
        d.fillRectangle(0, 0, screenWidth, screenHeight);
        d.setColor(new Color(0.196f, 0.804f, 0.196f));
        d.drawText((int) (screenWidth / 4.2), screenHeight / 2, "You Win !", 100);
        d.drawText((int) (screenWidth / 7.3), (int) (screenHeight * 0.66), "Your score is "
                + this.score, 80);
        d.drawText((int) (screenWidth * 0.01), (int) (screenHeight * 0.98), "Press space to exit", 20);
    }

    /**
     *
     * @return false.
     */
    public boolean shouldStop() {
        return false;
    }
}
