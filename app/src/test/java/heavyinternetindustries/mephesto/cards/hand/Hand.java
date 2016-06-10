package heavyinternetindustries.mephesto.cards.hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import heavyinternetindustries.mephesto.cards.Card;

/**
 * Created by Bobby on 06.06.16.
 */
public class Hand {
    private ArrayList<Card> cardsOnHand;

    public Hand(){
        cardsOnHand = new ArrayList();
    }

    public void addCard(Card card){
        cardsOnHand.add(card);
    }

    public Card[] getHand (){
        shuffleHand();
        Card[] cards = new Card[cardsOnHand.size()];
        return (cardsOnHand.toArray(cards));
    }

    public ArrayList<Card> getHandArrayList(){
        return cardsOnHand;
    }

    private void shuffleHand(){
        long seed = System.nanoTime();
        Collections.shuffle(cardsOnHand, new Random(seed));
        Collections.shuffle(cardsOnHand,new Random(seed));
    }

    public void removeCard(int position){
        cardsOnHand.remove(position);
    }

    public Card getCard(int position){
        return cardsOnHand.get(position);
    }

    private static boolean cardSameSuit(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit();
    }

    private static boolean cardSameValue(Card card1, Card card2) {
        return card1.getValue() == card2.getValue();
    }

    public static boolean cardEquals(Card card1, Card card2) {
        return cardSameSuit(card1, card2) && cardSameValue(card1, card2);
    }

    public int getCardsLeft(){
        return cardsOnHand.size();
    }

    public Card takeCard(int cardPosition){
        Card card = cardsOnHand.get(cardPosition);
        cardsOnHand.remove(cardPosition);
        return card;
    }
}
