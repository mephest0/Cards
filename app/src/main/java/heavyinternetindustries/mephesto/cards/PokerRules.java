package heavyinternetindustries.mephesto.cards;

import java.util.ArrayList;

/**
 * Created by mephest0 on 24.04.16.
 */
public class PokerRules implements IRules {
    int tick;
    ArrayList<Deck> decks;
    GameSetup setup;

    Deck unused, burn, t1, t2, t3, t4, t5;

    public PokerRules(GameSetup setup) {
        tick = 0;
        decks = new ArrayList<>();
        this.setup = setup;
    }

    @Override
    public boolean isValidMove(Card card, Deck fromDeck, Deck toDeck) {
        return false;
    }

    @Override
    public void performMove(Card card, Deck fromDeck, Deck toDeck) {

    }

    @Override
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    @Override
    public int getTick() {
        return tick;
    }

    @Override
    public int update(CardsMessage incoming) {
        if (gameHasNotStarted(incoming)) {
            setUpGame();
        } else {
            decks = parseDecks(incoming);

        }
        return tick;
    }

    private ArrayList<Deck> parseDecks(CardsMessage incoming) {
        String[] deckStrings = incoming.getState().split(CardsMessage.MESSAGE_DECK_SEPARARATOR);

        ArrayList<Deck> listOfDecks = new ArrayList<>();

        for (String deckString : deckStrings) {
            if (deckString.length() == 0) continue;
            listOfDecks.add(makeDeck(deckString));
        }
        return listOfDecks;
    }

    private Deck makeDeck(String deckString) {
        String deckName = deckString.substring(0,
                deckString.indexOf(CardsMessage.MESSAGE_DECK_DATA_START));

        Deck deck = new Deck(deckName);
        String[] cardData = getCardDataFromDeckStrings(deckString);

        for (String cardString : cardData) {
            if (cardString.length() == 0) continue;
            deck.addCard(makeCard(cardString));
        }

        return deck;
    }

    private Card makeCard(String cardString) {
        int separatorIndex = cardString.indexOf(CardsMessage.MESSAGE_VALUE_SUIT_SEPARATOR);
        int valueStart = separatorIndex + CardsMessage.MESSAGE_VALUE_SUIT_SEPARATOR.length();

        int suit = Integer.parseInt(cardString.substring(0, separatorIndex));
        int value = Integer.parseInt(cardString.substring(valueStart));

        return new Card(suit, value);
    }

    private boolean gameHasNotStarted(CardsMessage incoming) {
        return tick == 0 && incoming == null;
    }

    private String[] getCardDataFromDeckStrings(String string) {
        int start = string.indexOf(CardsMessage.MESSAGE_DECK_DATA_START) + CardsMessage.MESSAGE_DECK_DATA_START.length();
        int end = string.indexOf(CardsMessage.MESSAGE_DECK_DATA_STOP);
        return string.substring(start, end).split(CardsMessage.MESSAGE_CARD_SEPARATOR);
    }


    private void setUpGame() {
        //deal cards
        tick++;

        ArrayList<Deck> playerDecks = new ArrayList<>();
        int i = 0;
        if (setup.getPlayers() != null)
            for (String player : setup.getPlayers())
                playerDecks.add(new Deck(Deck.PLAYER + i++));


        t1 = new Deck(Deck.FLOP1);
        t2 = new Deck(Deck.FLOP2);
        t3 = new Deck(Deck.FLOP3);
        t4 = new Deck(Deck.TURN);
        t5 = new Deck(Deck.RIVER);

        burn = new Deck(Deck.BURN);

        unused = new Deck(Deck.UNUSED); //all other cards

        //add whole deck to this deck and shuffle
        unused.addCard(Card.buildDeck());
        unused.shuffle();

        decks.add(t1);
        decks.add(t2);
        decks.add(t3);
        decks.add(t4);
        decks.add(t5);
        decks.add(burn);
        decks.add(unused);

        for (Deck deck : playerDecks)
            decks.add(deck);

        for (Deck deck : playerDecks) {
            unused.moveCard(deck);
            unused.moveCard(deck);
        }
    }

    @Override
    public CardsMessage getMessage() {
        return new CardsMessage("",
                getTick(),
                setup.getYou(),
                getState(),
                getChanges(),
                getExtra());
    }

    private String getState() {
        StringBuilder builder = new StringBuilder();

        for (Deck deck : decks) {
            //add name of deck
            builder.append(deck.getPosition());
            builder.append(CardsMessage.MESSAGE_DECK_DATA_START);

            for (Card card : deck.getCards()) {
                //add card
                builder.append(card.getSuit() + CardsMessage.MESSAGE_VALUE_SUIT_SEPARATOR + card.getValue());
                builder.append(CardsMessage.MESSAGE_CARD_SEPARATOR);
            }

            builder.append(CardsMessage.MESSAGE_DECK_DATA_STOP + CardsMessage.MESSAGE_DECK_SEPARARATOR);
        }

        return builder.toString();
    }

    private String getChanges() {
        return "";
    }

    private String getExtra() {
        //player turn

        //who started round
        return "";
    }


}
