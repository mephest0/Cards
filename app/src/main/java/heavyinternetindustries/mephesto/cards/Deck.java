package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mephest0 on 26.02.16.
 */
public class Deck {
    private ArrayList<Card> cards;
    private String position;
    private boolean faceUp;
    private String description;

    public Deck(String position, boolean faceUp) {
        this.position = position;
        this.faceUp = faceUp;
        description = "";
        cards = new ArrayList<>();
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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
