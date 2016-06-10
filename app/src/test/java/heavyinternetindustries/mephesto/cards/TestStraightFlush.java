package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by Bobby on 06.06.16.
 */
public class TestStraightFlush extends HandFactory{

    @Test
    public void hasStraightFlush(){
        assertTrue(Card.isStraightFlush(aceKingQueenJackTen.getHand()).getValue()==Card._A);
        assertTrue(Card.isStraightFlush(aceTwoThreeFourFive.getHand()).getValue()==Card._5);
    }
}
