package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CheckAvailability;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * InventoryService is in charge of the book inventory and stock.
 * Holds a reference to the {@link Inventory} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class InventoryService extends MicroService
{
    private int tick = 1;

    private int endTick;
    //private  CheckAvailability book;
    private Inventory inventory;


    public InventoryService ( int serviceNum, CountDownLatch c )
    {
        super( "inv" + serviceNum, c );
        inventory = Inventory.getInstance();
    }

    @Override
    protected void initialize ()
    {
        subscribeEvent( CheckAvailability.class, ev -> {
            System.out.println( "check "+ev.getBookName() );
            int price = -1;
            synchronized ( inventory )
            {

                price = inventory.checkAvailabiltyAndGetPrice( ev.getBookName() );

                if ( ev.getCredit() >= price )
                {
                    inventory.take( ev.getBookName() );

                }
            }
            complete( ev, price );

        } );
        subscribeBroadcast( TickBroadcast.class, ev -> {
            tick = ev.getTick();
            endTick = ev.getEndTick();

            if ( tick == endTick )
            {
                terminate();
            }
        } );


        c.countDown();
    }

}
