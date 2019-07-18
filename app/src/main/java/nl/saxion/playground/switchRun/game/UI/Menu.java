package nl.saxion.playground.switchRun.game.UI;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nl.saxion.playground.switchRun.R;
import nl.saxion.playground.switchRun.game.Game;

public class Menu {

    private final Game game;
    private Set<MenuItem> menuItems = new HashSet<>();

    public Menu(Game game) {
        this.game = game;

        //Create the start button
        Button startButton = new Button(game, game.getWidth() / 2 - 20, game.getHeight() / 2 - 10, 40, 40, R.drawable.play_button);
        startButton.addOnTouchListener(new TouchListener() {
            @Override
            public void onTouch() {
                Menu.this.game.startGame();
                Menu.this.close();
            }
        });
        addItem(startButton);

        Image logo = new Image(game, game.getWidth() / 2 - 37.5f, 15, 75, 15, R.drawable.logo);
        addItem(logo);

        Scoreboard scoreboard = new Scoreboard(game, 5, 30, 30, 60, R.drawable.scoreboard);
        addItem(scoreboard);
    }

    private void close() {
        for (MenuItem item : menuItems) {
            game.removeEntity(item.getInstance());
        }
    }

    private void addItem(MenuItem item) {
        menuItems.add(item);
        game.addEntity(item.getInstance());
    }

    public Set<MenuItem> getMenuItems() {
        return Collections.unmodifiableSet(menuItems);
    }

}
