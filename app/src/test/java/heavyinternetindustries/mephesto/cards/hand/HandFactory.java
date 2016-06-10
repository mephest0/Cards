package heavyinternetindustries.mephesto.cards.hand;

import org.junit.Before;

import heavyinternetindustries.mephesto.cards.Card;

/**
 * Created by Bobby on 06.06.16.
 */
public class HandFactory {



    protected Hand aceAceFiveTwoFour;
    protected Hand kingKingKingSixSix;
    protected Hand sevenSevenTenTenFive;
    protected Hand aceTwoThreeFourFive;
    protected Hand aceKingQueenJackTen;
    protected Hand twoFourSixEightAceTenJack;
    protected Hand aceAceAceAceKing;
    protected Hand tenTenTenTenKingJackTen;


    protected Hand makeStraightFlushHighAce() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.HEARTS, Card._A));
        hand.addCard(new Card(Card.HEARTS, Card._K));
        hand.addCard(new Card(Card.HEARTS, Card._Q));
        hand.addCard(new Card(Card.HEARTS, Card._J));
        hand.addCard(new Card(Card.HEARTS, Card._10));
        return hand;
    }


    protected Hand makeStraightFlushLowAceHandWithHeartsAceTwoThreeFourFive() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.HEARTS, Card._A));
        hand.addCard(new Card(Card.HEARTS, Card._2));
        hand.addCard(new Card(Card.HEARTS, Card._3));
        hand.addCard(new Card(Card.HEARTS, Card._4));
        hand.addCard(new Card(Card.HEARTS, Card._5));
        return hand;
    }

    protected Hand makeTwoPairsHandWithTwoTensAndTwoSevens() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.SPADES, Card._7));
        hand.addCard(new Card(Card.CLUBS, Card._7));
        hand.addCard(new Card(Card.DIAMONDS, Card._10));
        hand.addCard(new Card(Card.SPADES, Card._10));
        hand.addCard(new Card(Card.CLUBS, Card._5));
        return hand;
    }


    protected Hand makeFullHouseHandWithThreeKingsAndTwoSixes() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.CLUBS, Card._K));
        hand.addCard(new Card(Card.DIAMONDS, Card._K));
        hand.addCard(new Card(Card.SPADES, Card._K));
        hand.addCard(new Card(Card.CLUBS, Card._6));
        hand.addCard(new Card(Card.DIAMONDS, Card._6));
        return hand;
    }

    protected Hand makePairHandWithTwoAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.SPADES, Card._A));
        hand.addCard(new Card(Card.HEARTS, Card._A));
        hand.addCard(new Card(Card.DIAMONDS, Card._5));
        hand.addCard(new Card(Card.SPADES, Card._2));
        hand.addCard(new Card(Card.HEARTS, Card._4));
        return hand;
    }

    protected Hand makeFourOfAKindWithAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.DIAMONDS, Card._A));
        hand.addCard(new Card(Card.SPADES, Card._A));
        hand.addCard(new Card(Card.HEARTS, Card._A));
        hand.addCard(new Card(Card.CLUBS, Card._A));
        hand.addCard(new Card(Card.SPADES, Card._K));
        return hand;
    }

    protected Hand makeFourOfAKindWithTen() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.SPADES, Card._10));
        hand.addCard(new Card(Card.CLUBS, Card._10));
        hand.addCard(new Card(Card.HEARTS, Card._10));
        hand.addCard(new Card(Card.DIAMONDS, Card._10));
        hand.addCard(new Card(Card.CLUBS, Card._K));
        hand.addCard(new Card(Card.HEARTS, Card._J));
        hand.addCard(new Card(Card.HEARTS, Card._10));
        return hand;
    }

    protected Hand makeHandWithTwoFourSixEightAceTenJack() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.SPADES, Card._2));
        hand.addCard(new Card(Card.HEARTS, Card._4));
        hand.addCard(new Card(Card.DIAMONDS, Card._6));
        hand.addCard(new Card(Card.SPADES, Card._8));
        hand.addCard(new Card(Card.DIAMONDS, Card._A));
        hand.addCard(new Card(Card.HEARTS, Card._10));
        hand.addCard(new Card(Card.SPADES, Card._J));
        return hand;
    }

    @Before
    public void setup() {
        aceAceFiveTwoFour = makePairHandWithTwoAces();
        kingKingKingSixSix = makeFullHouseHandWithThreeKingsAndTwoSixes();
        sevenSevenTenTenFive = makeTwoPairsHandWithTwoTensAndTwoSevens();
        aceTwoThreeFourFive = makeStraightFlushLowAceHandWithHeartsAceTwoThreeFourFive();
        aceKingQueenJackTen = makeStraightFlushHighAce();
        aceAceAceAceKing = makeFourOfAKindWithAces();
        tenTenTenTenKingJackTen = makeFourOfAKindWithTen();
        twoFourSixEightAceTenJack = makeHandWithTwoFourSixEightAceTenJack();
    }
}
