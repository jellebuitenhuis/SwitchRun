package nl.saxion.playground.switchRun.game.entities;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import nl.saxion.playground.switchRun.R;
import nl.saxion.playground.switchRun.game.Camera;
import nl.saxion.playground.switchRun.game.DimensionType;
import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.game.UI.Button;
import nl.saxion.playground.switchRun.game.UI.TouchListener;
import nl.saxion.playground.switchRun.game.collision.CollisionDirection;
import nl.saxion.playground.switchRun.game.level.Tile;
import nl.saxion.playground.switchRun.game.sound.Sound;
import nl.saxion.playground.switchRun.game.sound.SoundService;
import nl.saxion.playground.switchRun.lib.GameModel;
import nl.saxion.playground.switchRun.lib.GameView;

import nl.saxion.playground.switchRun.game.animation.Animation;

/**
 * The player class
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class Player extends MovingEntity {
    @SuppressWarnings("unused")
    private static final String TAG = "GameLogger";

    private static final float JUMP_VELOCITY = -0.8f;
    private static final int TOTAL_SWAP_COOLDOWN = 90;

    protected static float STANDARD_MAX_RUN_VELOCITY = 0.6f;

    private static final float START_RUN_VELOCITY = 0.4f;
    protected static float MAX_RUN_VELOCITY = STANDARD_MAX_RUN_VELOCITY;

    private static final float TIME_TO_MAX_VELOCITY = 60.0f;

    private float distanceToCamera;
    private boolean dead = false;
    private long deathTime = 0;
    private long deathCooldown = 500;
    private boolean gameOver = false;
    protected boolean grounded = false;

    //a boolean that makes sure the velocity can only be increased once
    private boolean once = false;

    private int swapCooldown = 0;
    final Camera camera = game.getCamera();

    protected PowerUp playerPowerUp;
    private Button button;

    private double score = 0;
    private Paint scorePaint = new Paint();

    private Animation animation;

    /**
     * The initializer
     *
     * @param game   the Game the player will be a part of
     * @param x      the x position the player will start at
     * @param y      the y position the player will start at
     * @param width  the width of the player
     * @param height the height of the player
     */
    public Player(Game game, float x, float y, int width, int height) {
        super(game, x, y, width, height, R.drawable.character_single, DimensionType.Red);

        distanceToCamera = camera.position.x - position.x;
        velocity.x = START_RUN_VELOCITY;
        camera.velocity.x = velocity.x;

        //init the color
        scorePaint.setColor(Color.RED);

        animation = new Animation(game.getView().getBitmapFromResource(R.drawable.character),
                3, 1, Tile.TILE_SIZE, Tile.TILE_SIZE);
        animation.addAnimation("jump", 0, new int[] { 1 });
        animation.addAnimation("walk", 0, new int[] { 0, 1, 2 });

        animation.setAnimation("walk");
    }

    /**
     * Executes all the logic for the player
     */
    @Override
    public void tick() {

        // Update the GameEntity
        super.tick();
        //checks if the player is dead, if the player is dead nothing in the tick method should be run.
        if (!dead)
        {
            // Set the camera to focus a bit in front of the player.
            camera.center.x = (game.getWidth() / 4);
            camera.center.y = (game.getHeight() / 3 * 2);

            //statement that  checks whether the player is further from the camera then intended
            if((camera.position.x - position.x) > distanceToCamera)
            {
                if (!once){
                    velocity.x *= 1.05f;
                    once = true;
                }
            }
            else
            {
                once = false;
                playerMovement();
            }


            swapCooldown--;
            grounded = false;

            playerInView();
            playerFalling();

            setScore(position.x / 5);

            if(playerPowerUp != null) {
                playerPowerUp.position.x = this.position.x-20;
                playerPowerUp.position.y = camera.position.y-50;
            }
        }
        else if(!gameOver)
        {
            camera.velocity.x = 0f;
            gameOver();
        }

    }

    /**
     * method that handles the game over state of the game
     */
    private void gameOver()
    {
        deathTime = System.currentTimeMillis();
        int buttonWidth = 15;
        int buttonHeight = 15;
        final Button gameOverButton = new Button(game, game.getWidth()-buttonWidth, game.getHeight() - buttonHeight, buttonWidth, buttonHeight, R.drawable.restart);
        gameOverButton.addOnTouchListener(new TouchListener() {
            @Override
            public void onTouch() {
                if(System.currentTimeMillis() > deathTime+deathCooldown)
                {
                    goToMenu(gameOverButton);
                }
            }
        });
        game.addEntity(gameOverButton);
        button = gameOverButton;
        gameOver = true;
    }

    private void goToMenu(Button button)
    {
        game.removeEntity(button);
        game.loadDeathMenu();
    }

    /**
     * method that handles the gravity of the player.
     */
    private void playerFalling() {
        // If the player is falling...
        if(!grounded && camera.target.y < collision.bottom()) {
            // ..set the camera to look at the players feet.
            camera.target.y = collision.bottom();
            camera.target.x = position.x;
            // Focus the camera on the player.
        }
    }

    /**
     * method that checks if the player is in view if he's not in view the player is declared dead.
     */
    private void playerInView() {
        // Makes sure the player stays in the screen
        if (position.y > 125f || position.x < (camera.position.x - (game.getWidth() / 3))) {
            Log.d("resetGame", "oooof the player died");
            dead = true;
            camera.target.y = 20f;
            game.saveScore(getScore());
        }
    }

    /**
     * handles the movement of the player and the camera
     */
    private void playerMovement() {
        float deltaTime = 1.f / game.ticksPerSecond();
        // Calculate how far away the velocity is from the max.
        float dx = MAX_RUN_VELOCITY - velocity.x;
        // Slowly ramp up velocity to the max.
        velocity.x += dx * (deltaTime / TIME_TO_MAX_VELOCITY);
        // Cap the velocity.
        if(velocity.x >= MAX_RUN_VELOCITY) {
            velocity.x = MAX_RUN_VELOCITY;
        }
        camera.velocity.x = velocity.x;
    }

    /**
     * Handles the user input.
     * Jumps if the user touches the right side of the screen,
     * swaps if the user touches the left side of the screen.
     */
    public void handleInput() {
        for (GameModel.Touch touch : game.touches) {
            if(dead && ((touch.x < game.getWidth()-30) || (touch.y < game.getHeight()-30)) )  {
                if(System.currentTimeMillis() > deathTime+deathCooldown)
                {
                    resetGame();
                }
            }
            else if (touch.x > game.getWidth() / 2) {
                jump();
            } else if (touch.x < game.getWidth() / 2 && touch.y > game.getHeight() / 2) {
                if (swapCooldown <= 0 && touch.y > game.getHeight() / 2) {
                    swapDimension();

                    swapCooldown = TOTAL_SWAP_COOLDOWN;
                }
            } else if (playerPowerUp != null && !playerPowerUp.using) {
                playerPowerUp.usePowerUp(this);
            }
        }
    }

    /**
     * handles the resetting of the game after the death of the character
     */
    private void resetGame()
    {
        Log.d("restart", "i'm about to restart the game");
        if (dimension == DimensionType.Blue)
        {
            swapDimension();
        }
        once = false;
        dead = false;
        playerPowerUp = null;
        camera.position.x = 10;
        velocity.x = START_RUN_VELOCITY;
        Player.MAX_RUN_VELOCITY = Player.STANDARD_MAX_RUN_VELOCITY;
        camera.velocity.x = velocity.x;
        super.setPosition(10,60);

        SoundService.play(game.getContext(), Sound.Death);
        gameOver = false;
        if(button != null)
        {
            game.removeEntity(button);
        }
        game.resetLevel();
    }

    /**
     * Makes the player jump
     */
    public void jump() {
        if (grounded) {
            velocity.y = JUMP_VELOCITY;
            grounded = false;

            animation.setAnimation("jump");
            SoundService.play(game.getContext(), Sound.Jump);
        }
    }

    /**
     * Swaps the dimension the player is in
     */
    private void swapDimension() {
        DimensionType newDimension = (dimension != DimensionType.Red) ? DimensionType.Red : DimensionType.Blue;
        Log.d("Player", "Swapping dimension from " + this.dimension + " to " + newDimension);

        game.getCurrentLevel().setDimension(newDimension);
        dimension = newDimension;

        // changes the color of scoreboard to the color of the dimension
        if (scorePaint.getColor() == Color.RED){
            scorePaint.setColor(Color.BLUE);
        } else {
            scorePaint.setColor(Color.RED);
        }

        SoundService.play(game.getContext(), Sound.SwapDimension);
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    float getLeft() {
        return super.getLeft() - 2f;
    }

    @Override
    public String toString() {
        return "Player: position: " + getPosition() + "  - velocity: " + getVelocity();
    }

    @Override
    public boolean isAffectedByGravity() {
        return true;
    }

    @Override
    public boolean isPickupable() {
        return false;
    }

    @Override
    public void onCollisionWith(GameEntity other, CollisionDirection direction) {
        final Camera camera = game.getCamera();

        if(other instanceof PowerUp)
        {
            SoundService.play(game.getContext(), Sound.PickupPowerup);
            PowerUp powerUp = (PowerUp) other;
            playerPowerUp = powerUp;
            other = null;
        }
        else if(direction == CollisionDirection.Left || direction == CollisionDirection.Right) {
            SoundService.play(game.getContext(), Sound.WallBump);
        }
        else if(other instanceof Tile && direction == CollisionDirection.Down) {
            // Set the camera to look at the top of the tile.
            camera.target.y = other.getCollisionRectangle().top();
            velocity.y = 0;
            grounded = true;
            animation.setAnimation("walk");
        }
    }

    @Override
    public boolean isCollidableWith(GameEntity other) {
        return (super.isCollidableWith(other) || other.isPickupable());
    }

    /**
     * Returns the score of the player
     * @return double
     */
    public double getScore(){
        return this.score;
    }

    /**
     * Adds a double to the score of the plater
     * @param score double
     */
    public void addScore(double score){
        this.score += score;
    }

    /**
     * sets a double to the score of the plater
     * @param score double
     */
    public void setScore(double score){
        this.score = score;
    }

    /**
     * Resets the score
     * returns true if score is reset
     * returns false if score is not reset
     * @return boolean
     */
    public boolean resetScore(){
        score = 0;
        return (score == 0);
    }


    @Override
    public void draw(GameView gameView) {

        final Camera camera = game.getCamera();

        if (gameOver) {
            Paint gameOverlay = new Paint();
            gameOverlay.setColor(Color.BLACK);
            gameOverlay.setAlpha(100);
            gameView.getCanvas().drawRect(0,0,game.getWidth(),game.getHeight(),gameOverlay);

            Paint gameOverPaint = new Paint();
            gameOverPaint.setTextSize(18);
            gameOverPaint.setColor(Color.parseColor("#c3e619"));
            gameOverPaint.setTextAlign(Paint.Align.CENTER);
            gameView.getCanvas().drawText("Highscore: " + Math.round(score) ,game.getWidth()/2,game.getHeight()/2 , gameOverPaint);
        }
        else
        {
            gameView.getCanvas().drawText("" + Math.round(score),10,10 , scorePaint);
            animation.draw(gameView, camera.calculateScreenX(position.x), camera.calculateScreenY(position.y), 1.f / game.ticksPerSecond());
        }
    }
}
