package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;

/**
 * Created by mephest0 on 26.02.16.
 */
public interface IRules {
    /**
     * Checks if moving a card between two decks is a valid move
     * @param card
     * @param fromDeck
     * @param toDeck
     * @return
     */
    boolean isValidMove(Card card, Deck fromDeck, Deck toDeck);

    /**
     * Performs move. This method should also check that the move is valid
     * @param card
     * @param fromDeck
     * @param toDeck
     */
    void performMove(Card card, Deck fromDeck, Deck toDeck);

    /**
     * Gets a list of the decks in play
     * @return
     */
    ArrayList<Deck> getDecks();

    /**
     * @return last tick for game logic
     */
    int getTick();

    /**
     * Update game from incoming data
     * @param incoming
     * @return tick
     */
    int update(CardsMessage incoming);

    /**
     * @return A CardsMessage representation of the state of the game
     */
    CardsMessage getMessage();
}
