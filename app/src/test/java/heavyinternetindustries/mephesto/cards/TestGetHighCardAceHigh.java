package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bobby on 06.06.16.
 */
public class TestGetHighCardAceHigh extends HandFactory{

    @Test
    public void aceIsLowestDoesNotSendNullWithTheCorrectInputt(){
        assertNotNull(Card.getHighCardAceHigh(twoFourSixEightAceTenJack.getHand()));
    }

    @Test
    public void aceIsLowestDoesSendNullWithIncorrectInput(){
    }

    @Test
    public void aceIsTheHighestCard(){
        assertTrue(Card.getHighCardAceHigh(twoFourSixEightAceTenJack.getHand()).getValue()==Card._A);
    }

    @Test
    public void doesNotInverseTheClassification(){
        assertTrue(Card.getHighCardAceHigh(twoFourSixEightAceTenJack.getHand()).getValue()==Card._8);
    }


}
