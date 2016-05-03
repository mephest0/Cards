package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by mephest0 on 24.04.16.
 */
public class GameSetup {
    String nameOfTheGame, host, you;

    public GameSetup(String nameOfTheGame, ArrayList<String> players, String host, String you) {
        this.nameOfTheGame = nameOfTheGame;
        this.host = host;
        this.you = you;
    }

    public ArrayList<String> getPlayers() {
        return null;
    }

    public String getYou() {
        return you;
    }

    public boolean isGroupOwner() {
        return host.equals(you);
    }
}
