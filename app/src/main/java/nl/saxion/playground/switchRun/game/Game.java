package nl.saxion.playground.switchRun.game;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import nl.saxion.playground.switchRun.game.UI.Image;
import nl.saxion.playground.switchRun.game.UI.Menu;
import nl.saxion.playground.switchRun.game.collision.CollisionManager;
import nl.saxion.playground.switchRun.game.entities.GameEntity;
import nl.saxion.playground.switchRun.game.entities.Player;
import nl.saxion.playground.switchRun.game.level.Background;
import nl.saxion.playground.switchRun.game.level.InfiniteLevel;
import nl.saxion.playground.switchRun.game.level.Level;
import nl.saxion.playground.switchRun.game.level.Tile;
import nl.saxion.playground.switchRun.game.sound.Sound;
import nl.saxion.playground.switchRun.game.sound.SoundService;
import nl.saxion.playground.switchRun.lib.Entity;
import nl.saxion.playground.switchRun.lib.GameModel;
import nl.saxion.playground.switchRun.lib.GameView;

import static android.content.Context.MODE_PRIVATE;

/**
 * The main Game class
 */
@SuppressWarnings({"WeakerAccess"})
public class Game extends GameModel {

    private static final String SCORE_FILE_NAME = "score.txt";

    private Context context;
    private GameView view;

    private Camera camera;
    public Level level;
    private Player player;

    private Background background;

    private CollisionManager collisionManager;

    private boolean started = false;
    private Menu menu;

    public Game(Context context, GameView view) {
        this.view = view;
        this.context = context;

        this.collisionManager = new CollisionManager();
    }

    public void loadDeathMenu()
    {
        removeEntity(player);
        resetLevel();
        camera = new Camera(this);
        new Menu(this);
        player = new Player(this, Tile.TILE_SIZE, 6f * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
        level = new InfiniteLevel(this);
    }

    /**
     * Creates the objects in the level
     */
    @Override
    public void start() {
        Log.v("Game", "Creating Background...");
        this.background = new Background(this);
        addEntity(background);
        Log.v("Game", "Creating camera...");
        this.camera = new Camera(this);
        Log.v("Game", "Creating level...");
        this.level = new InfiniteLevel(this);
        Log.v("Game", "Creating player...");
        this.player = new Player(this, Tile.TILE_SIZE, 6f * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
        menu = new Menu(this);
        Log.d("ENTITIES", "" + getEntities(Image.class).size());
    }

    public void startGame() {
        Log.v("Game", "Adding player to scene...");
        addEntity(player);
        Log.v("Game","Playing background music...");
        SoundService.play(getContext(), Sound.Background, true);

        camera.start();
        Log.v("Game", "Done!");
    }

    @Override
    public void tick() {
        // Make sure the level can update it's segments.
        level.tick();
        // Handle player input.
        player.handleInput();

        // Update all entities.
        super.tick();

        camera.tick();
        // Check and resolve the collisions.
        collisionManager.resolve();
    }

    public Level getCurrentLevel() {
        return level;
    }

    public Player getCurrentPlayer() {
        return player;
    }

    public GameView getView() {
        return view;
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void addEntity(Entity entity) {
        super.addEntity(entity);

        if (entity instanceof GameEntity)
            collisionManager.add((GameEntity) entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        super.removeEntity(entity);

        if (entity instanceof GameEntity)
            collisionManager.remove((GameEntity) entity);
    }

    /**
     * Returns the width of the screen
     *
     * @return the width of the screen
     */
    @Override
    public float getWidth() {
        return 100f * actualWidth / Math.min(actualWidth, actualHeight);
    }

    /**
     * Returns the height of the screen
     *
     * @return the height of the screen
     */
    @Override
    public float getHeight() {
        return 100f * actualHeight / Math.min(actualWidth, actualHeight);
    }

    /**
     * Returns the context of the Application
     *
     * @return the context of the Application
     */

    public Context getContext() {
        return context;
    }

    public void resetLevel() {
        level.despawnLevel();
        level = new InfiniteLevel(this);
    }

    /**
     * Reads the highscores from internal storage and returns them
     *
     * @return the highscores
     */
    public ArrayList<Double> getScores() {
        ArrayList<Double> scores = new ArrayList<>();
        FileInputStream in = null;

        try {
            in = context.openFileInput(SCORE_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Double.valueOf(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return scores;
    }

    /**
     * Saves the score if it is a highscore
     *
     * @param score the new score
     */
    public void saveScore(double score) {
        FileOutputStream out = null;
        File saveFile = new File(SCORE_FILE_NAME);

        ArrayList<Double> scores = getScores();

        if (scores.size() >= 7) {
            int lowestIndex = 0;
            for (int index = 0; index < scores.size(); index++) {
                if (scores.get(index) < scores.get(lowestIndex)) {
                    lowestIndex = index;
                }
            }

            if (score > scores.get(lowestIndex)) {
                scores.set(lowestIndex, score);
            }
        } else {
            scores.add(score);
        }

        try {
            out = context.openFileOutput(SCORE_FILE_NAME, MODE_PRIVATE);
            for (double highScore : scores) {
                out.write((highScore + "\n").getBytes());
            }
            Toast.makeText(context, "Saved score:" + Math.round(score), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns true if the score is a new highscore
     *
     * @param score the new score
     * @return true if the score is a new highscore
     */
    public boolean isHighestScore(double score) {
        ArrayList<Double> scores = getScores();

        double highest = 0;
        for (double highscore : scores) {
            if (highscore > highest)
                highest = highscore;
        }

        return score > highest;
    }
}