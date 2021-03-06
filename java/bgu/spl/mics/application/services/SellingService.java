package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CheckAvailability;
import bgu.spl.mics.application.messages.OrderBookEvent;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Selling service in charge of taking orders from customers.
 * Holds a reference to the {@link MoneyRegister} singleton of the store.
 * Handles {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class SellingService extends MicroService
{   private  int counter=0;
    private int tick = 1;
    private int speed;
    private TimeUnit t = TimeUnit.MILLISECONDS;
    private MoneyRegister moneyRegister;
    private int endTick = 1000;

    public SellingService ( int serviceNum, CountDownLatch c )
    {
        super( "Sell" + serviceNum, c );
        moneyRegister = MoneyRegister.getInstance();

    }

    @Override
    protected void initialize ()
    {
        subscribeEvent( OrderBookEvent.class, ev -> {
            counter++;
            System.out.println( "order recived "+ev.getBookName()  );
            int prossTick = tick;
            System.out.println( "check" );
            Future f = sendEvent( new CheckAvailability( ev.getBookName(), ev.getCustomer().getAvailableCreditAmount() ) );
            int result = (int) f.get( ( endTick - tick ) * speed, t );
            System.out.println("aftercheck "+ev.getBookName() +" "+ result);
            //create constractor Orderricipt
            if ( ev.getCustomer().getAvailableCreditAmount() +300>= result )
            {
                System.out.println("AFTER IF");
                moneyRegister.chargeCreditCard( ev.getCustomer(), result );
                OrderReceipt r = new OrderReceipt( this.getName(), result, ev.getBookName(), tick, ev.getTick(), prossTick );
                moneyRegister.file( r );
                System.out.println("receipt");
                this.complete( ev, r );
                System.out.println( this.getName() + " complete " +ev.getBookName() +tick);
            }
            complete(ev,new OrderReceipt(this.getName(), result, ev.getBookName(), tick, ev.getTick(), prossTick ));
        } );
        subscribeBroadcast( TickBroadcast.class, ev -> {
            tick = ev.getTick();
            endTick = ev.getEndTick();
            speed = ev.getSpeed();
            if ( tick == endTick )
                terminate();
        } );
        c.countDown();
    }

}
