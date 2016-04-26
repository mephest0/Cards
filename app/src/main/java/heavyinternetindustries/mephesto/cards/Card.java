package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;

/**
 * Created by mephest0 on 26.02.16.
 */
public class Card implements Comparable {
    public static int CLUBS = 0;
    public static int DIAMONDS = 1;
    public static int HEARTS = 2;
    public static int SPADES = 3;

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

    public static int _ACE = 0;
    public static int _JACK = 10;
    public static int _QUEEN = 11;
    public static int _KING = 12;

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

    public boolean isPair(Card other) {
        return isPair(this, other);
    }

    /**
     * @param other
     * @return This card is higher that <code>other</code>
     */
    private boolean isHighCard(Card other) {
        if (this.getValue() == other.getValue()) {
            return this.getSuit() > other.getSuit();
        } else {
            return this.getValue() > other.getValue();
        }
    }

    public static int indexOfHighCard(int number, Card ... params) {
        if (number > 0) {
            int maxIndex = 0;

            for (int i = 0; i < number; i++) {
                if (!params[maxIndex].isHighCard(params[i])) maxIndex = i;
            }

            return maxIndex;
        }

        return 0; //WTF
    }

    public static boolean isPair(Card card1, Card card2) {
        return (card1.getValue() % 13 == card2.getValue() % 13);
    }

    public static boolean isOfAKind(int number, Card ... params) {
        for (int i = 0; i < number  - 1; i++) {
            if (!isPair(params[i], params[i + 1]))
                return false;
        }

        return true;
    }

    public boolean isFlush(Card ... params) {
        if (params.length < 1) return false;

        int suit = params[0].getSuit();

        for (Card card : params)
            if (card.getSuit() != suit)
                return false;

        return true;

    }

    public static boolean isStraightAceLow() {
        //TODO
        return false;
    }

    public static boolean isStraightAceHigh() {
        //TODO
        return false;
    }

    public static boolean isStraightAceLowAndHigh() {
        //TODO
        return false;
    }

    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> ret = new ArrayList<>();

        for (int i = 0; i < 52; i++)
            ret.add(new Card(i));

        return ret;
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
}
