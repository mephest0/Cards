package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by Bobby on 06.06.16.
 */
public class PairTest extends HandFactory{

    @Test
    public void handHasPair(){
        assertTrue(Card.isPair(aceAceFiveTwoFour.getHand()).getValue() ==Card._A);
    }

}
