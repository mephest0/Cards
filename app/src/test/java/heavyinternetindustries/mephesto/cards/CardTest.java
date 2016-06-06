package heavyinternetindustries.mephesto.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by mephest0 on 01.06.16.
 */
public class CardTest {
    Card s2 = new Card(Card.SPADES, Card._2);
    Card s3 = new Card(Card.SPADES, Card._3);
    Card s10 = new Card(Card.SPADES, Card._10);
    Card sj = new Card(Card.SPADES, Card._J);
    Card sq = new Card(Card.SPADES, Card._Q);
    Card sk = new Card(Card.SPADES, Card._K);
    Card sa = new Card(Card.SPADES, Card._A);
    Card ha = new Card(Card.HEARTS, Card._A);
    Card ca = new Card(Card.CLUBS, Card._A);

    Hand pairHand;
    Hand fullHouse;
    Hand twoPairs;
    Hand straightFlushLowAce;
    Hand straightFlushHighAce;
    Hand aceHigh;

    @Before
    public void setup(){
        pairHand = makePairHand();
        fullHouse = makeFullHouseHand();
        twoPairs = makeTwoPairsHand();
        straightFlushLowAce = makeStraightFlushLowAceHand();
        straightFlushHighAce = makeStraightFlushHighAceHand();
    }

    private Hand makeStraightFlushHighAceHand() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.HEARTS,Card._A));
        hand.addCard(new Card(Card.HEARTS,Card._K));
        hand.addCard(new Card(Card.HEARTS,Card._Q));
        hand.addCard(new Card(Card.HEARTS,Card._J));
        hand.addCard(new Card(Card.HEARTS,Card._10));
        return hand;
    }

    private Hand makeStraightFlushLowAceHand() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.HEARTS,Card._A));
        hand.addCard(new Card(Card.HEARTS,Card._2));
        hand.addCard(new Card(Card.HEARTS,Card._3));
        hand.addCard(new Card(Card.HEARTS,Card._4));
        hand.addCard(new Card(Card.HEARTS,Card._5));
        return hand;
    }

    private Hand makeTwoPairsHand() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.SPADES,Card._7));
        hand.addCard(new Card(Card.CLUBS,Card._7));
        hand.addCard(new Card(Card.DIAMONDS,Card._10));
        hand.addCard(new Card(Card.SPADES,Card._10));
        hand.addCard(new Card(Card.CLUBS,Card._5));
        return hand;
    }


    private Hand makeFullHouseHand() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.CLUBS, Card._K));
        hand.addCard(new Card(Card.DIAMONDS,Card._K));
        hand.addCard(new Card(Card.SPADES,Card._K));
        hand.addCard(new Card(Card.CLUBS,Card._6));
        hand.addCard(new Card(Card.DIAMONDS,Card._6));
        return hand;
    }

    private Hand makePairHand() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.SPADES, Card._5));
        hand.addCard(new Card(Card.HEARTS, Card._A));
        hand.addCard(new Card(Card.DIAMONDS, Card._5));
        hand.addCard(new Card(Card.SPADES,Card._2));
        hand.addCard(new Card(Card.HEARTS,Card._4));
        return hand;
    }
    /**
     * Full House -
     * IsTwoPairs -
     * IsStraightFlush -
     * GetHighCardAce -
     * GetLowCardAce
     * IsFlush
     * IsStraight
     *
     * @return
     */


    @Test
    public void testIfHandHasPair(){
        assertTrue(isPairHand());
    }

    private boolean isPairHand() {
        Card card = Card.isPair(pairHand.getHand());
        return card.getValue()==Card._5;
    }

    @Test
    public void testIfHandHasFullHouse(){
        assertTrue(hasFullHouse());
    }

    private boolean hasFullHouse() {
        Card[] cards = Card.isFullHouse(fullHouse.getHand());
        return cards[0].getValue() == Card._K && cards[1].getValue()==Card._6;
    }

    @Test
    public void hasTwoPairs(){
        assertTrue(handHasTwoPairs());
    }

    private boolean handHasTwoPairs() {
        Card[] cards = Card.isTwoPairs(twoPairs.getHand());
        return cards[0].getValue()==Card._10 && cards[1].getValue()==Card._7;
    }

    @Test
    public void hasStraightFlush(){
        assertTrue(hasStraightFlushHigh());
        assertTrue(hasStraightFlushLow());
    }

    private boolean hasStraightFlushLow() {
        Card card = Card.isStraightFlush(straightFlushLowAce.getHand());
        return card.getValue() == Card._5;
    }

    private boolean hasStraightFlushHigh() {
        Card card = Card.isStraightFlush(straightFlushHighAce.getHand());
        return card.getValue() == Card._A;
    }

    class Hand {
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

        private void shuffleHand(){
            long seed = System.nanoTime();
            Collections.shuffle(cardsOnHand, new Random(seed));
            Collections.shuffle(cardsOnHand,new Random(seed));
        }
    }
}