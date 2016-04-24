package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;

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
}
