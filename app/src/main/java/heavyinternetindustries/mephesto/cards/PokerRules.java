package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;

/**
 * Created by mephest0 on 24.04.16.
 */
public class PokerRules implements IRules {
    int tick;
    ArrayList<Deck> decks;

    public PokerRules() {
        tick = 0;
        decks = new ArrayList<>();
    }

    @Override
    public boolean isValidMove(Card card, Deck fromDeck, Deck toDeck) {
        return false;
    }

    @Override
    public void performMove(Card card, Deck fromDeck, Deck toDeck) {

    }

    @Override
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    @Override
    public int getTick() {
        return tick;
    }

    @Override
    public int update(String incoming) {
        if (tick == 0 && incoming.equals("")) {
            //deal cards
            tick++;
            ArrayList<Deck> players = new ArrayList<>();

            Deck t1, t2, t3, t4, t5; //table cards
            Deck burn; //burned cards

            Deck bets; //for UI purposes
            Deck unused; //all other cards

            Card card1, card2;
        } else {
            //do logic
        }

        return tick;
    }
}
