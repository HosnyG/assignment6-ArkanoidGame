import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

/**
 * graphical representation of the scores.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;

    /**
     * Constructor.
     *
     * @param scores : highScores table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    /**
     * draw highScores table screen.
     *
     * @param d  : given surface.
     * @param dt : seconds passed since the last call;
     */
    public void doOneFrame(DrawSurface d, double dt) {
        Image img = null;
        try { //get image.
            img = ImageIO.read(ClassLoader.getSystemClassLoader()
                    .getResourceAsStream("menu_images/highScores.png"));
            d.drawImage(0, 0, img); //draw the image
        } catch (IOException e) { //image not found
            System.out.println("high scores image not found!");
        }
        d.setColor(Color.black);
        d.drawText(290, 55, "High Scores", 40);
        d.drawText(70, 125, "Player Name", 30);
        d.drawText(630, 125, "Score", 30);
        d.drawText(60, 140, "_________________________________________________", 25);
        List<ScoreInfo> highScores = this.scores.getHighScores();
        Color fontColor = new Color(0.698f, 0.133f, 0.133f);
        d.setColor(fontColor);

        for (int i = 0; i < highScores.size(); i++) { //draw the player and their scores.
            d.drawText(70, 180 + (i * 35), highScores.get(i).getName(), 30);
            d.drawText(630, 180 + (i * 35), String.valueOf(highScores.get(i).getScore()), 30);

        }

        d.setColor(Color.BLUE);
        d.drawText(247, 572, "Press space to continue", 30);
        d.setColor(Color.black);
        d.drawText(245, 570, "Press space to continue", 30); //stroke


    }

    /**
     * @return false.
     */
    public boolean shouldStop() {
        return false;
    }

}
