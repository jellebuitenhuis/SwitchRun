package nl.saxion.playground.switchRun.game.UI;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.lib.GameView;

public class Scoreboard extends Image implements MenuItem {

    private ArrayList<Double> scores;
    private Paint paint;

    /**
     * The initializer
     *
     * @param game     the Game that the GameEntity will be part of
     * @param x        the x position the GameEntity should start at
     * @param y        the y position the GameEntity should start at
     * @param width    the width of the GameEntity
     * @param height   the height of the GameEntity
     * @param resource the id of the drawable the GameEntity should use
     */
    public Scoreboard(Game game, float x, float y, int width, int height, int resource) {
        super(game, x, y, width, height, resource);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(8);
        scores = game.getScores();

        //Sort the scores in descending order
        Collections.sort(scores, new Comparator<Double>() {
            @Override
            public int compare(Double aDouble, Double t1) {
                return (int) Math.signum(t1.doubleValue() - aDouble.doubleValue());
            }
        });
    }

    /**
     * Draws the highscores in the correct positions
     * @param gv the gameview to draw to
     */
    @Override
    public void draw(GameView gv) {
        super.draw(gv);

        for (int index = 0; index < scores.size(); index++) {
            int score = (int)Math.round(scores.get(index));
            gv.getCanvas().drawText((index + 1) + ": " + score, position.x + 4, position.y + (index + 1) * 8, paint);

        }
    }
}
