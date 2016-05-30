package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mephest0 on 26.02.16.
 */
public class Deck {
    public static final String PLAYER = "player_";
    public static final String FLOP1 = "flop_1";
    public static final String FLOP2 = "flop_2";
    public static final String FLOP3 = "flop_3";
    public static final String TURN = "turn";
    public static final String RIVER = "river";
    public static final String BURN = "burn";
    public static final String UNUSED = "unused";

    private ArrayList<Card> cards;
    private String position;
    private boolean faceUp;


    public Deck(String position){
        this.position = position;
        faceUp = !isFacedDown();
        cards = new ArrayList<>();
    }

    private boolean isFacedDown() {
        return position.equals(BURN) || position.equals(UNUSED);
    }

    public String getPosition() {
        return position;
    }

    /**
     * Removes all cards from deck
     */
    public void removeAll() {
        cards.clear();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCard(ArrayList<Card> more) {
        cards.addAll(more);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Moves a single card to <code>deck</code>
     * @param deck Deck to move card to
     */
    public void moveCard(Deck deck) {
        Card card = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        deck.addCard(card);
    }
}
