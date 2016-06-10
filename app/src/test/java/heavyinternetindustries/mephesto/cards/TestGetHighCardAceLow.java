package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Bobby on 06.06.16.
 */
public class TestGetHighCardAceLow extends HandFactory{

    @Test
    public void aceIsLowestDoesNotSendNullWithTheCorrectInputt(){
        assertNotNull(Card.getHighCardAceLow(twoFourSixEightAceTenJack.getHand()));
    }

}
