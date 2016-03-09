package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;

/**
 * Created by mephest0 on 26.02.16.
 */
public interface IRules {
    boolean isValidMove(Card card, Deck fromDeck, Deck toDeck);
    void performMove(Card card, Deck fromDeck, Deck toDeck);
    ArrayList<Deck> getDecks();
    int lastTick();
    int update(String incomming);
}
