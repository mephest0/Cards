package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bobby on 06.06.16.
 */
public class TestTwoPair extends HandFactory{

    @Test
    public void hasTwoPairs(){
        assertNotNull(Card.isTwoPairs(sevenSevenTenTenFive.getHand()));
        assertTrue(returnsTenAndSevenInThatOrder());
        assertFalse(returnsSevenAndTenInThatOrder());
    }

    private boolean returnsSevenAndTenInThatOrder() {
        Card[] cards = Card.isTwoPairs(sevenSevenTenTenFive.getHand());
        return cards[1].getValue()==Card._10 && cards[0].getValue()==Card._7;
    }

    private boolean returnsTenAndSevenInThatOrder() {
        Card[] cards = Card.isTwoPairs(sevenSevenTenTenFive.getHand());
        return cards[0].getValue()==Card._10 && cards[1].getValue()==Card._7;
    }
}
