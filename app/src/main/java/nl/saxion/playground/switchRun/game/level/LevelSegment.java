package nl.saxion.playground.switchRun.game.level;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.entities.Dash;
import nl.saxion.playground.switchRun.game.entities.SlowFall;
import nl.saxion.playground.switchRun.game.math.Vector2;

/**
 * The class representing a segment in our `Level`. A segment is a part of a level, so we can have a bit of
 * randomness but not too much.
 */
public class LevelSegment {
    /**
     * The tiles that are in this segment.
     */
    private Tile[][] tiles;
    /**
     * A boolean indicating whether this segment is shown on screen.
     */
    private boolean spawned = false;
    /**
     * The position on the screen of this segment.
     */
    private Vector2 position = new Vector2();

    /**
     * Creates a segment with the given parameters.
     * @param width The width of the segment in tiles.
     * @param height The height of the segment in tiles.
     */
    public LevelSegment(int width, int height) {
        this.tiles = new Tile[height][width];
    }

    /**
     * Replaces or sets a tile in the segment.
     * @param x The tile x of the tile
     * @param y The tile y of the tile
     * @param tile The tile which will be put there.
     */
    public void setTile(int x, int y, Tile tile) {
        tiles[y][x] = tile;
    }

    /**
     * Sets the position of the segment.
     * @param x The x position.
     * @param y The y position.
     */
    public void setPosition(float x, float y) {
        this.position.set(x, y);

        updateTilePositions();
    }

    /**
     * Shows this segment on the screen.
     * @param game A reference to the game wanting to show this segment.
     */
    public void spawn(Game game) {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                final Tile tile = tiles[y][x];

                if (tile != null) {
                    game.addEntity(tiles[y][x]);
                }
            }
        }

        this.spawned = true;
    }

    /**
     * Removes this segment from the screen.
     * @param game A reference to the game wanting to hide this segment.
     */
    public void despawn(Game game) {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                final Tile tile = tiles[y][x];

                if (tile != null) {
                    game.removeEntity(tiles[y][x]);
                }
            }
        }

        this.spawned = false;
    }

    /**
     * A boolean indicating whether the segment is shown on screen.
     * @return
     */
    public boolean isSpawned() {
        return spawned;
    }

    /**
     * Loads a segment from a file.
     * @param game A reference to the game.
     * @param path A path to a file containing segment data.
     * @return
     */
    public static LevelSegment load(Game game, String path) {
        List<List<Integer>> tiles = loadRaw(game, path);
        LevelSegment segment = new LevelSegment(tiles.get(0).size(), tiles.size());

        Log.v("LevelSegment", "load(): Created segment of " + segment.getWidth() + "x" + segment.getHeight());

        for (int y = 0; y < tiles.size(); y++) {
            for (int x = 0; x < tiles.get(y).size(); x++) {
                // Convert from int to DimensionType
                DimensionType type = DimensionType.values()[tiles.get(y).get(x)];

                if (type != DimensionType.None) {
                    if (type == DimensionType.SlowFall) {
                        segment.setTile(x, y, new SlowFall(game, segment, x, y));
                    } else if (type == DimensionType.Dash) {
                        segment.setTile(x, y, new Dash(game, segment, x, y));
                    } else {
                        segment.setTile(x, y, new Tile(game, segment, type, x, y));
                    }
                }
            }
        }

        Log.v("LevelSegment", "load(): Constructed tiles from the data.");

        return segment;
    }

    /**
     * Loads a segment as a double array of numbers.
     * @param game A reference to the game.
     * @param path A path to the file containing segment data.
     * @return The numbers that were read from the file.
     */
    public static List<List<Integer>> loadRaw(Game game, String path) {
        List<List<Integer>> tiles = new ArrayList<>();

        // Reads the file and saves it to the array
        Scanner scanner = null;
        try {
            scanner = new Scanner(game.getContext().getAssets().open(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tileX = 0, tileY = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            tileX = 0;
            String[] row = line.split(",");
            List<Integer> tileRow = new ArrayList<>();

            //Log.v("LevelSegment", "loadRaw(): Found row with " + row.length + " tiles at Y " + tileY + ".");

            for (String number : row) {
                try {
                    tileRow.add(Integer.parseInt(number));
                } catch (NumberFormatException e) {
                    tileRow.add(0);
                }

                tileX++;
            }

            tiles.add(tileRow);
            tileY++;
        }

        Log.v("LevelSegment", "loadRaw(): Loaded raw data of \"" + path + "\" as " + tileX + "x" + tileY + " array.");

        return tiles;
    }

    /**
     * Sets the current dimension for this segment.
     * @param dimension The dimension to set it to.
     */
    public void setDimension(DimensionType dimension) {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                final Tile tile = tiles[y][x];

                if (tile != null)
                    tile.makeVisibleFor(dimension);
            }
        }
    }

    /**
     * Updates all positions of the tiles.
     */
    private void updateTilePositions() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                // Don't try to adjust positions of null tiles.
                if (tile == null)
                    continue;

                tile.getPosition().set(position.x + tile.getLocalX(), tile.getLocalY());
            }
        }
    }

    /**
     * Calculate the amount of pixels this segment is in the X axis.
     *
     * @return The amount of pixels this segment is in the X axis.
     */
    public int getLocalWidth() {
        return getWidth() * Tile.TILE_SIZE;
    }

    /**
     * Calculate the amount of pixels this segment is in the Y axis.
     *
     * @return The amount of pixels this segment is in the Y axis.
     */
    public int getLocalHeight() {
        return getHeight() * Tile.TILE_SIZE;
    }

    /**
     * Retrieves the amount of tiles in the Y axis in this segment.
     *
     * @return The aount of tiles in the Y axis in this segment.
     */
    public int getHeight() {
        return tiles.length;
    }

    /**
     * Retrieves the amount of tiles in the X axis in this segment.
     *
     * @return The aount of tiles in the X axis in this segment.
     */
    public int getWidth() {
        return tiles[0].length;
    }

    public Tile getTile(int localTileX, int localTileY) {
        try {
            return tiles[localTileY][localTileX];
        } catch(ArrayIndexOutOfBoundsException e) {
            // Invalid indices in the array.
            return null;
        }
    }

    /**
     * Returns the position of the segment.
     * @return The position.
     */
    public Vector2 getPosition() {
        return position;
    }
}
