package heavyinternetindustries.mephesto.cards;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mephest0 on 01.06.16.
 */
public class CardTestTest {
    Card s2 = new Card(Card.SPADES, Card._2);
    Card s3 = new Card(Card.SPADES, Card._3);
    Card s10 = new Card(Card.SPADES, Card._10);
    Card sj = new Card(Card.SPADES, Card._J);
    Card sq = new Card(Card.SPADES, Card._Q);
    Card sk = new Card(Card.SPADES, Card._K);
    Card sa = new Card(Card.SPADES, Card._A);
    Card ha = new Card(Card.HEARTS, Card._A);
    Card ca = new Card(Card.CLUBS, Card._A);

    @Test
    public void testTestPair() throws Exception {
        assertTrue("SA and HA is a pair", Card.isPair(sa, ha));
        assertFalse("SA and SK is not a pair", Card.isPair(sa, sk));
    }

    @Test
    public void testTestOfAKind() throws Exception {
        assertTrue("Of a kind, ha, sa, ca",
                Card.isOfAKind(ha, sa, ca));
        assertFalse("Of a kind, ha, sa, sk",
                Card.isOfAKind(ha, sa, sk));
    }

    @Test
    public void testTestStraight() throws Exception {
        assertTrue("Straight (ace low), sj, sq",
                Card.isStraightAceLow(sj, sq));
        assertTrue("Straight (ace low), s10, sj, sq, sk",
                Card.isStraightAceLow(s10, sj, sq, sk));
        assertTrue("Straight (ace low) out of order, s10, sj, sq, sk",
                Card.isStraightAceLow(sj, sq, s10, sk));
        assertFalse("Straight (ace low), s10, sj, sq, sk, sa(!!)",
                Card.isStraightAceLow(s10, sj, sq, sk, sa));
        assertTrue("Straight (ace low), sa, s2, s3",
                Card.isStraightAceLow(sa, s2, s3));
        assertTrue("Straight (ace high), sq, sk, sa",
                Card.isStraightAceHigh(sq, sk, sa));
        assertFalse("Straight (ace high), sa, s2, s3",
                Card.isStraightAceHigh(sa, s2, s3));
    }
}