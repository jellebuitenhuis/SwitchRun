package nl.saxion.playground.switchRun.game.level;


import android.util.Log;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;

/**
 * The manager that handles some specific level stuff that did not fit into the `Level` class.
 */
public class LevelManager {
    /**
     * The amount of segments in our assets folder.
     */
    public static final int SEGMENT_COUNT = 7;

    /**
     * Creates a new segment from the assets folder.
     * @param game A reference to the game creating the level.
     * @param index The index which segment to take from the asset folder.
     * @return The `LevelSegment` that was created.
     */
    public static LevelSegment createPremadeSegment(Game game, int index) {
        String path = "segment_" + index + ".txt";

        Log.v("LevelManager", "Started loading level segment with index " + index);

        return LevelSegment.load(game, path);
    }

    /**
     * Creates a simple start segment so players have time to get focussed on the game.
     * @param game A reference to the game wanting to create the segment.
     * @return A segment containing a start section of our game.
     */
    public static LevelSegment createStartSegment(Game game) {
        // Create a default segment of 20x20
        LevelSegment segment = new LevelSegment(20, 20);

        int y = 9;
        for (int x = 0; x < segment.getWidth(); x++) {
            segment.setTile(x, y, new Tile(game, segment, DimensionType.Both, x, y));
        }

        Log.v("LevelManager", "Generated start segment of size " + segment.getWidth() + "x" + segment.getHeight());

        return segment;
    }

}
