package nl.saxion.playground.switchRun.game.level;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.entities.Player;

/**
 * The class representing the levels in our game.
 */
public abstract class Level {
    /**
     * The maximum amount of segments that are drawn in the game.
     */
    public static final int MAX_DRAW_SEGMENTS = 3;

    protected Game game;
    // The list of segments currently in memory.
    private List<LevelSegment> segments = new LinkedList<>();
    // Which dimension the map currently shows.
    private DimensionType dimension = DimensionType.Red;

    public Level(Game game) {
        this.game = game;
        // A level always starts with a start segment.
        addSegment( LevelManager.createStartSegment(game) );
    }

    public void tick() {
        final Player player = game.getCurrentPlayer();
        final float halfScreenWidth = game.getWidth() / 2;

        // Check if the player is still inside the first segment.
        LevelSegment currentSegment = segments.get(0);
        // If the player is out of reach from the segment...
        if(player.getPosition().x > currentSegment.getPosition().x + currentSegment.getLocalWidth() + halfScreenWidth) {
            // ... despawn the segment and remove it from memory.
            currentSegment.despawn(game);
            segments.remove(0);

            Log.v("Level", "Despawned segment at index 0.");
        }

        // Check our current segments if they have spawned yet.
        for(int i = 0; i < Math.min(segments.size(), MAX_DRAW_SEGMENTS); i++) {
            LevelSegment segment = segments.get(i);

            if(segment != null && segment.isSpawned() == false) {
                segment.spawn(game);

                Log.v("Level", "Spawned segment at index " + i + " for player to see");
            }
        }
    }

    /**
     * Adds a new segment to the level.
     * @param segment The segment that will be added to the level.
     */
    protected void addSegment(LevelSegment segment) {
        segments.add(segment);
        // Update all segments to the same dimension.
        setDimension(this.dimension);
        // Apply the correct positioning to all tiles in the segment.
        updatePosition(segment);

        Log.v("Level", "Added new segment to the level of size " + segment.getWidth() + "x" + segment.getHeight());
    }

    /**
     * Sets the current dimension that this level is showing on screen.
     * @param dimension The dimension to show on screen.
     */
    public void setDimension(DimensionType dimension) {
        this.dimension = dimension;

        for(LevelSegment segment : segments)
            segment.setDimension(dimension);
    }

    /**
     * Updates the position of a segment so it lines up with all the other segments.
     * @param segment The segment which position will be updated.
     */
    private void updatePosition(LevelSegment segment) {
        float x = 0;

        int index = segments.indexOf(segment);
        // Only check the previous segment if there actually are segments.
        if(index > 0) {
            LevelSegment previous = segments.get(index - 1);
            // Calculate the x based on the segment before this one.
            x = previous.getPosition().x + previous.getLocalWidth();
        }

        segment.setPosition(x, 0);
    }

    /**
     * The amount of segments currently in memory.
     * @return The amount of segments.
     */
    public int getSegmentCount() {
        return segments.size();
    }

    /**
     * Despawns every segment in our level.
     */
    public void despawnLevel()
    {
        for (LevelSegment segment: segments)
        {
            segment.despawn(game);
        }
    }
}
