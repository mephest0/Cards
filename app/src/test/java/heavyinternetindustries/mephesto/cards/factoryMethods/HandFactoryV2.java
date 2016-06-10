package heavyinternetindustries.mephesto.cards.factoryMethods;

import java.util.ArrayList;
import java.util.Random;

import heavyinternetindustries.mephesto.cards.Card;
import heavyinternetindustries.mephesto.cards.factoryMethods.CreateFourOfAKind;
import heavyinternetindustries.mephesto.cards.hand.Hand;

/**
 * Created by Bobby on 06.06.16.
 */
public class HandFactoryV2 {

    /**
     *
     * @param sizeOfHand does return null if it is less than four
     * @return
     */
    public static Hand getHandWithFourOfAKind(int sizeOfHand){
        if (sizeOfHand>=4)
            return CreateFourOfAKind.getFourOfAKindWith(sizeOfHand);
        return null;
    }


    public static Hand getHandWithaPair(int sizeOfHand){

        return null;
    }

}
