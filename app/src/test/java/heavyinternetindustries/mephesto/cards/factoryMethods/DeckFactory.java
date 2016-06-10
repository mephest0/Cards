package heavyinternetindustries.mephesto.cards.factoryMethods;

import java.util.ArrayList;
import java.util.Random;

import heavyinternetindustries.mephesto.cards.Card;
import heavyinternetindustries.mephesto.cards.hand.Hand;

/**
 * Created by Bobby on 08.06.16.
 */
public class DeckFactory {

    private static Random random = new Random();
    private static ArrayList<Card> cardDeck;
    private static Hand clubsDeck;
    private static Hand diamondsDeck;
    private static Hand heartsDeck;
    private static Hand spadesDeck;

    public static Hand clubs(){
        return clubsDeck;
    }

    public static Hand diamonds(){
        return diamondsDeck;
    }

    public static Hand hearts(){
        return heartsDeck;
    }

    public static Hand spades(){
        return spadesDeck;
    }


    public static void createDecks() {
        makeNewDeck();
        for (int i = 0; i < 13; i++) {
            clubsDeck.addCard(getClubsCard(i));
            diamondsDeck.addCard(getDiamonsCard(i));
            heartsDeck.addCard(getHeartCard(i));
            spadesDeck.addCard(getSpadesCard(i));
        }

    }

    private static void makeNewDeck() {
        cardDeck = Card.buildDeck();
        clubsDeck = new Hand();
        diamondsDeck = new Hand();
        heartsDeck = new Hand();
        spadesDeck = new Hand();
    }

    private static Card getClubsCard(int position) {
        return cardDeck.get(position);
    }

    private static Card getDiamonsCard(int position) {
        return cardDeck.get(position + 13);
    }

    private static Card getHeartCard(int position) {
        return cardDeck.get(26 + position);
    }

    private static Card getSpadesCard(int position) {

        return cardDeck.get(39 + position);
    }

    public static Card takeRandomCard(){
        Hand deck = getRandomUnusedDeck();
        return deck.takeCard(deck.getCardsLeft());

    }

    public static Hand getRandomUnusedDeck(){
        ArrayList<Integer> indexes= createIndexList();
        int number = random.nextInt(indexes.size());

        switch (indexes.get(number)){
            case 0:{ indexes.remove(number);
                return clubsDeck;
            }
            case 1:{indexes.remove(number);
                return diamondsDeck;
            }
            case 2:{indexes.remove(number);
                return heartsDeck;
            }
            case 3:{indexes.remove(number);
                return spadesDeck;
            }
        }
        return null;
    }

    private static ArrayList<Integer> createIndexList(){
        ArrayList<Integer> indexes = new ArrayList<>(4);
        for (int i=0; i<4; i++){
            indexes.add(i);
        }
        return indexes;
    }
}
