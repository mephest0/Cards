package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import heavyinternetindustries.mephesto.cards.hand.HandFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bobby on 06.06.16.
 */
public class FourOfAKind extends HandFactory{

    @Test
    public void fourOfAKindDoesNotSendNullAtTheWrongTimeWithAce(){
        assertNotNull(Card.isOfAKind(aceAceAceAceKing.getHand()));

    }

    @Test
    public void fourOfAKindDoesNotSendNullAtTheWrongTimeWithTen() {
        assertNotNull(Card.isOfAKind(tenTenTenTenKingJackTen.getHand()));
    }

    @Test
    public void fourOfAKindWithAceAndSevenCards(){
        assertTrue(Card.isOfAKind(aceAceAceAceKing.getHand())== true);
    }

    @Test
    public void fourOfAKindWithTenAndSevenCards(){
        assertTrue(Card.isOfAKind(tenTenTenTenKingJackTen.getHand())== true);
    }

    @Test
    public void sendsFalseIfThereIsNotFourOfAKind(){
        assertTrue(Card.isOfAKind(aceKingQueenJackTen.getHand())==false);
    }
}
