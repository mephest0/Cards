package heavyinternetindustries.mephesto.cards;

import android.media.MediaActionSound;

import java.util.ArrayList;

/**
 * Created by mephest0 on 24.04.16.
 */
public class GameManager {
    MainActivity activity;
    IRules rules;
    GameSetup setup;

    public GameManager(GameSetup setup, MainActivity activity, IRules rules) {
        this.activity = activity;
        this.rules = rules;
        this.setup = setup;
    }

    public void startGame() {
        System.out.println("GameManager.startGame");
        rules.update(null);

        updateNetwork();
    }

    public void updateNetwork() {
        CardsMessage updateMessage = rules.getMessage();

        ArrayList<CardsMessage> outgoingMessages = updateMessage.addressTo(setup.getPlayers());

        for (CardsMessage outgoingMessage : outgoingMessages)
            activity.sendMessage(outgoingMessage);
    }

    public void updateFromNetwork(CardsMessage message) {
        rules.update(message);
    }
}
