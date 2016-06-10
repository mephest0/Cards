package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bobby on 06.06.16.
 */
public class TestFullHouse extends HandFactory{

    @Test
    public void handHasFullHouse(){
        assertTrue(returnsKingAndSixInThatOrder());
        assertFalse(returnsSixAndKingInThatOrger());
    }

    private boolean returnsSixAndKingInThatOrger() {
        Card[] cards = Card.isFullHouse(kingKingKingSixSix.getHand());
        return cards[1].getValue() == Card._K && cards[0].getValue()==Card._6;
    }

    private boolean returnsKingAndSixInThatOrder() {
        Card[] cards = Card.isFullHouse(kingKingKingSixSix.getHand());
        return cards[0].getValue() == Card._K && cards[1].getValue()==Card._6;
    }
}
