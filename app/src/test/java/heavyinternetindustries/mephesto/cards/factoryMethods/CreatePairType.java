package heavyinternetindustries.mephesto.cards.factoryMethods;

import heavyinternetindustries.mephesto.cards.hand.Hand;

/**
 * Created by Bobby on 08.06.16.
 */
public class CreatePairType extends Setup{

    public static Hand getPair(int numberOfPairs, int sizeOfHand,int numberOfAKind){
        setup();
        DeckFactory.createDecks();
        switch (numberOfPairs){
            case 1: return createOnePair(sizeOfHand,numberOfAKind);
            case 2:return createTwoPairs(sizeOfHand);
        }
        return null;
    }


    private static Hand createOnePair(int sizeOfHand, int numberOfAKind) {
        Hand hand;
        int cardValue = random.nextInt(13);

        hand = addCards(numberOfAKind,cardValue);
        return hand;
    }

    private static void removeCardsFromDeck(int cardValue) {

    }

    private static Hand createTwoPairs(int sizeOfHand){
        return null;
    }

    private static Hand addCards(int numberOfAKind, int cardNumber){
        Hand hand = new Hand();

        for (int i=0; i<numberOfAKind; i++){
            hand.addCard(DeckFactory.getRandomUnusedDeck().getCard(cardNumber));
        }
        return hand;
    }

}
