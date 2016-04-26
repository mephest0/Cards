package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;

/**
 * Created by mephest0 on 24.04.16.
 */
public class PokerRules implements IRules {
    int tick;
    ArrayList<Deck> decks;
    GameSetup setup;

    Deck unused, burn, t1, t2, t3, t4, t5;

    public PokerRules(GameSetup setup) {
        tick = 0;
        decks = new ArrayList<>();
        this.setup = setup;
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
            setUpGame();

        } else {
            //do logic
        }

        return tick;
    }

    private void setUpGame() {
        //deal cards
        tick++;

        ArrayList<Deck> playerDecks = new ArrayList<>();
        int i = 0;
        for (String player : setup.getPlayers())
            playerDecks.add(new Deck("player_" + i++, true));


        t1 = new Deck("T_2", true);
        t2 = new Deck("T_3", true);
        t3 = new Deck("T_4", true);
        t4 = new Deck("T_5", true);
        t5 = new Deck("T_6", true);

        burn = new Deck("T_1", false);

        unused = new Deck("T_7", false); //all other cards

        //add whole deck to this deck and shuffle
        unused.addCard(Card.buildDeck());
        unused.shuffle();

        decks.add(t1);
        decks.add(t2);
        decks.add(t3);
        decks.add(t4);
        decks.add(t5);
        decks.add(burn);
        decks.add(unused);

        for (Deck deck : playerDecks)
            decks.add(deck);

        for (Deck deck : playerDecks) {
            unused.moveCard(deck);
            unused.moveCard(deck);
        }
    }
}
