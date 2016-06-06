package heavyinternetindustries.mephesto.cards;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mephest0 on 26.02.16.
 */
public class Card implements Comparable {
    public static int CLUBS = 0;
    public static int DIAMONDS = 1;
    public static int HEARTS = 2;
    public static int SPADES = 3;

    private static int _A_HIGH = 13;
    public static int _A = 0;
    public static int _2 = 1;
    public static int _3 = 2;
    public static int _4 = 3;
    public static int _5 = 4;
    public static int _6 = 5;
    public static int _7 = 6;
    public static int _8 = 7;
    public static int _9 = 8;
    public static int _10 = 9;
    public static int _J = 10;
    public static int _Q = 11;
    public static int _K = 12;

    public static int _ACE = _A;
    public static int _JACK = _J;
    public static int _QUEEN = _Q;
    public static int _KING = _K;

    private int value;

    public Card(int suit, int value) {
        this(suit * 13 + value);
    }

    public Card(int value) {
        this.value = value;
    }

    public int getValue() {
        return value % 13;
    }

    public int getSuit() {
        return value / 13;
    }

    private void setAceHigh() {
        if (value == _A) value = _A_HIGH;
    }

    private void setAceLow() {
        if (value == _A_HIGH) value = _A;
    }

    private Card copy() {
        return new Card(getSuit(), getValue());
    }

    /**
     * @param other
     * @return This card is higher than <code>other</code>
     */
    private boolean isHighCard(Card other) {
        if (this.getValue() == other.getValue()) {
            return this.getSuit() > other.getSuit();
        } else {
            return this.getValue() > other.getValue();
        }
    }

    /**
     * If there is a full house, this method returns two cards. The first card occurs three times, second occurs twice.
     * @param params
     * @return <code>null</code> if no full house, otherwise an array of two <code>Card</code>s
     */
    public static Card[] isFullHouse(Card ... params) {
        if (params.length <= 5) return null;
        Card three = null;
        Card two = null;

        for (Card i : params) {
            for (Card j : params) {
                for (Card k : params) {
                    if (i != j && j != k && k != i) { //no cards can be the same
                        if (isPair(i, j, k) != null) { //look for three of a kind
                            if (three == null) three = i; //no null-comparisons
                            if (three.compareTo(i) < 0) three = i; //save highest card
                        }
                    }
                }
            }
        }

        if (three != null) {
            for (Card i : params) {
                for (Card j : params) {
                    if (i != j) {
                        if (isPair(i, j)) {
                            if (two == null) two = i;
                            if (two.compareTo(i) < 0) two = i;
                        }
                    }
                }
            }

            if (two != null) return new Card[]{three, two};
        }

        return null;
    }

    /**
     * This method always returns the pair with the highest value. Ace high.
     * @param params
     * @return <code>null</code> if no pairs, otherwise one of the <code>Card</code>s in the pair
     */
    public static Card isPair(Card ... params) {
        Card ret = null;
        for (Card card : params) card.setAceHigh(); //set ace high

        for (Card i : params) {
            for (Card j : params) {
                if (isPair(i, j) && i != j) {
                    if (i.compareTo(ret) > 0) ret = i;
                }
            }
        }

        return ret;
    }

    /**
     * Ace is high
     * @param params
     * @return <code>null</code> if no pairs, otherwise one <code>Card</code> from each pair
     */
    public static Card[] isTwoPairs(Card ... params) {
        Card pair1 = null;
        Card pair2 = null;

        for (Card i : params) {
            for (Card j : params) {
                if (isPair(i, j) && i != j) {
                    if (i.compareTo(pair1) > 0) pair1 = i;
                }
            }
        }

        for (Card i : params) {
            for (Card j : params) {
                if (isPair(i, j) && i != j) {
                    if (i.compareTo(pair2) > 0 && i != pair1 && j != pair1) pair2 = i;
                }
            }
        }

        if (pair1 != null && pair2 != null)
            return new Card[]{pair1, pair2};

        return null;
    }

    /**
     * Ace high
     * @param params
     * @return <code>null</code> if no straight flush, otherwise the highest <code>Card</code> in straight
     */
    public static Card isStraightFlush(Card ... params) {
        if (isFlush(params))
            return isStraightAceHigh(params);
        return null;
    }

    /**
     * Ace counts as 1.
     * @param params
     * @return highest card in series
     */
    public static Card getHighCardAceLow(Card ... params) {
        if (params.length > 0) {
            int maxIndex = 0;

            for (int i = 0; i < params.length; i++) {
                if (!params[maxIndex].isHighCard(params[i])) maxIndex = i;
            }

            return params[maxIndex];
        }

        return null; //WTF
    }

    /**
     * Ace counts as 14.
     * @param params
     * @return highest card in series
     */
    public static Card getHighCardAceHigh(Card ... params) {
        for (Card card : params) card.setAceHigh();

        if (params.length > 0) {
            int maxIndex = 0;

            for (int i = 0; i < params.length; i++) {
                if (!params[maxIndex].isHighCard(params[i])) maxIndex = i;
            }

            return params[maxIndex];
        }

        return null; //WTF
    }

    public static boolean isPair(Card card1, Card card2) {
        return (card1.getValue() % 13 == card2.getValue() % 13);
    }

    /**
     *
     * @param params Any number of cards to check
     * @return <code>true</code> if <bold>all</bold> cards have the same <bold>value</bold>
     */
    public static boolean isOfAKind(Card ... params) {
        for (int i = 0; i < params.length - 1; i++) {
            if (!isPair(params[i], params[i + 1]))
                return false;
        }

        return true;
    }

    /**
     *
     * @param params
     * @return <code>true</code> if all <code>Card</code>s are same suit
     */
    public static boolean isFlush(Card ... params) {
        if (params.length < 1) return false;

        int suit = params[0].getSuit();

        for (Card card : params)
            if (card.getSuit() != suit)
                return false;

        return true;
    }

    /**
     * Checks if cards are sequential, with no doubles. Ace counts as 1. This method takes any number of cards
     * @param params Cards to check
     * @return Highest card in straight
     */
    public static Card isStraightAceLow(Card ... params) {
        ArrayList<Card> list = new ArrayList<>();
        for (Card card : params) list.add(card);

        Collections.sort(list);

        for (int i = 0; i < list.size() - 2; i++) {
            if (list.get(i).getValue() - list.get(i + 1).getValue() != -1) return null; //TODO
        }

        return null;
    }

    /**
     * Checks if cards are sequential, with no doubles. Ace counts as 14. This method takes any number of cards
     * @param params Cards to check
     * @return Highest card in straight
     */
    public static Card isStraightAceHigh(Card ... params) {
        ArrayList<Card> list = new ArrayList<>();
        for (Card card : params) list.add(card.copy()); //Copies card, to set ace high

        for (Card card : list) card.setAceHigh();

        Collections.sort(list);

        for (int i = 0; i < list.size() - 2; i++) {
            if (list.get(i).getValue() - list.get(i + 1).getValue() != -1) return null; //TODO
        }

        return null;
    }

    /**
     * Ace high and low
     * @param params
     * @return Highest card in straight, <code>null</code> if there is not a straight
     */
    public static Card isStraight(Card ... params) {
        Card ret = isStraightAceHigh(params);
        if (ret == null) ret = isStraightAceLow(params);

        return ret;
    }

    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> ret = new ArrayList<>();

        for (int suit : new int[]{CLUBS, DIAMONDS, HEARTS, SPADES})
            for (int value : new int[]{_A, _K, _Q, _J, _10, _9, _8, _7, _6, _5, _4, _3, _2})
                ret.add(new Card(suit, value));

        return ret;
    }

    /**
     *
     * @return Id for image resource for this <code>Card</code>
     */
    public int getResource() {
        return getResource(this);
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Card) {
            Card other = (Card) another;

            if (this.getValue() == other.getValue()) {
                return ((Integer)this.getSuit()).compareTo(other.getSuit());
            } else {
                return ((Integer)this.getValue()).compareTo(other.getValue());
            }
        }

        return 0;
    }


    /**
     *
     * @return Id for image resource for this <code>Card</code>
     */
    public static int getResource(Card card) {
        if (card.getSuit() == SPADES) {
            if (card.getValue() == _10) return R.drawable.s10;
            if (card.getValue() == _2) return R.drawable.s2;
            if (card.getValue() == _3) return R.drawable.s3;
            if (card.getValue() == _4) return R.drawable.s4;
            if (card.getValue() == _5) return R.drawable.s5;
            if (card.getValue() == _6) return R.drawable.s6;
            if (card.getValue() == _7) return R.drawable.s7;
            if (card.getValue() == _8) return R.drawable.s8;
            if (card.getValue() == _9) return R.drawable.s9;
            if (card.getValue() == _J) return R.drawable.sj;
            if (card.getValue() == _Q) return R.drawable.sq;
            if (card.getValue() == _K) return R.drawable.sk;
            if (card.getValue() == _A) return R.drawable.sa;
        } else if (card.getSuit() == CLUBS) {
            if (card.getValue() == _10) return R.drawable.q10;
            if (card.getValue() == _2) return R.drawable.q2;
            if (card.getValue() == _3) return R.drawable.q3;
            if (card.getValue() == _4) return R.drawable.q4;
            if (card.getValue() == _5) return R.drawable.q5;
            if (card.getValue() == _6) return R.drawable.q6;
            if (card.getValue() == _7) return R.drawable.q7;
            if (card.getValue() == _8) return R.drawable.q8;
            if (card.getValue() == _9) return R.drawable.q9;
            if (card.getValue() == _J) return R.drawable.qj;
            if (card.getValue() == _Q) return R.drawable.qq;
            if (card.getValue() == _K) return R.drawable.qk;
            if (card.getValue() == _A) return R.drawable.qa;
        } else if (card.getSuit() == HEARTS) {
            if (card.getValue() == _10) return R.drawable.h10;
            if (card.getValue() == _2) return R.drawable.h2;
            if (card.getValue() == _3) return R.drawable.h3;
            if (card.getValue() == _4) return R.drawable.h4;
            if (card.getValue() == _5) return R.drawable.h5;
            if (card.getValue() == _6) return R.drawable.h6;
            if (card.getValue() == _7) return R.drawable.h7;
            if (card.getValue() == _8) return R.drawable.h8;
            if (card.getValue() == _9) return R.drawable.h9;
            if (card.getValue() == _J) return R.drawable.hj;
            if (card.getValue() == _Q) return R.drawable.hq;
            if (card.getValue() == _K) return R.drawable.hk;
            if (card.getValue() == _A) return R.drawable.ha;
        } else if (card.getSuit() == DIAMONDS) {
            if (card.getValue() == _10) return R.drawable.d10;
            if (card.getValue() == _2) return R.drawable.d2;
            if (card.getValue() == _3) return R.drawable.d3;
            if (card.getValue() == _4) return R.drawable.d4;
            if (card.getValue() == _5) return R.drawable.d5;
            if (card.getValue() == _6) return R.drawable.d6;
            if (card.getValue() == _7) return R.drawable.d7;
            if (card.getValue() == _8) return R.drawable.d8;
            if (card.getValue() == _9) return R.drawable.d9;
            if (card.getValue() == _J) return R.drawable.dj;
            if (card.getValue() == _Q) return R.drawable.dq;
            if (card.getValue() == _K) return R.drawable.dk;
            if (card.getValue() == _A) return R.drawable.da;
        }

        System.out.println("UH OH! Could not resolve image resource for card:");
        System.out.println(card);
        return -1337;
    }
}
