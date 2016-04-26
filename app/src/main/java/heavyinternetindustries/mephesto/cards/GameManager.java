package heavyinternetindustries.mephesto.cards;

import android.media.MediaActionSound;

import java.util.ArrayList;

/**
 * Created by mephest0 on 24.04.16.
 */
public class GameManager {
    MainActivity activity;
    IRules rules;

    public GameManager(GameSetup setup, MainActivity activity, IRules rules) {
        this.activity = activity;
        this.rules = rules;
    }

    public void startGame() {
        System.out.println("GameManager.startGame");
        rules.update("");
    }
}
