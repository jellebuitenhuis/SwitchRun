package nl.saxion.playground.switchRun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nl.saxion.playground.switchRun.game.Game;
import nl.saxion.playground.switchRun.lib.GameView;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        gameView = new GameView(this);
        setContentView(gameView);

        game = new Game(this, gameView);
        gameView.setGame(game);

    }

    /**
     * Pauses the game
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.setGame(null);
    }

    /**
     * Resumes the game
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.setGame(game);
    }

}
