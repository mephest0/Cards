package heavyinternetindustries.mephesto.cards.factoryMethods;


import heavyinternetindustries.mephesto.cards.hand.Hand;

/**
 * Created by Bobby on 08.06.16.
 */
public class CreateFourOfAKind extends Setup{



    /**
     *
     * @param numberOfCardsOnHand can only be bigger than 4;
     * @return Hand of cards with four equal cards.
     */
    public static Hand getFourOfAKindWith(int numberOfCardsOnHand){
        setup();
        Hand hand = getOnlyFourOfAKind();

        int index = numberOfCardsOnHand - 4;
        for (int i=0; i<index; i++){
            hand.addCard(DeckFactory.takeRandomCard());
        }
        return hand;
    }


    private static Hand getOnlyFourOfAKind() {
        Hand hand = new Hand();
        int randomValue = random.nextInt(13);
        addCards(hand,randomValue);
        removeCardsFromDeck(randomValue);
        return hand;
    }

    private static void removeCardsFromDeck(int randomValue) {
        DeckFactory.clubs().removeCard(randomValue);
        DeckFactory.spades().removeCard(randomValue);
        DeckFactory.hearts().removeCard(randomValue);
        DeckFactory.diamonds().removeCard(randomValue);
    }

    private static void addCards(Hand hand, int randomValue) {
        hand.addCard(DeckFactory.clubs().getCard(randomValue));
        hand.addCard(DeckFactory.diamonds().getCard(randomValue));
        hand.addCard(DeckFactory.hearts().getCard(randomValue));
        hand.addCard(DeckFactory.spades().getCard(randomValue));
    }
}
