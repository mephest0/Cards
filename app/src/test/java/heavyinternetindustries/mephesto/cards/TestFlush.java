package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by Bobby on 06.06.16.
 */
public class TestFlush extends HandFactory{

    @Test
    public void isFlush(){
        assertTrue(Card.isFlush(aceKingQueenJackTen.getHand()));
    }
}
