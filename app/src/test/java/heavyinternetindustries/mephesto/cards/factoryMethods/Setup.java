package heavyinternetindustries.mephesto.cards.factoryMethods;

import java.util.Random;

/**
 * Created by Bobby on 10.06.16.
 */
public class Setup {

    static Random random;

    protected static void setup() {
        DeckFactory.createDecks();
        random = new Random();
    }
}
