package nl.saxion.playground.switchRun.game.level;

import android.util.Log;

import nl.saxion.playground.switchRun.game.Game;

/**
 * The class that represents our Infinite level in our game. The infinite level keeps spawning segments
 * untill the player has died.
 */
public class InfiniteLevel extends Level {

    private int lastIndex = -1;
    private int counter = 0;

    public InfiniteLevel(Game game) {
        super(game);
    }

    @Override
    public void tick() {
        // Handle all base level logic.
        super.tick();

        if(getSegmentCount() < 3) {
            Log.v("InfiniteLevel", "Not enough segments. Spawning in more!");

            addSegment( LevelManager.createPremadeSegment(game, randomSegment()));
        }

    }

    /**
     * Returns a new segment index for the level to spawn.
     * @return A segment index to spawn.
     */
    private int randomSegment()
    {
        while(true)
        {
            int index = (int)Math.floor(Math.random() * LevelManager.SEGMENT_COUNT);
            if (index != lastIndex)
            {
                lastIndex = index;
                counter = 1;
                return index;
            }
            else if (counter < 2)
            {
                counter++;
                return index;
            }
        }
    }

}
