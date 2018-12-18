package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.OrderBookEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * APIService is in charge of the connection between a client and the store.
 * It informs the store about desired purchases using {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class APIService extends MicroService
{

    private TimeUnit t = TimeUnit.MILLISECONDS;
    private int tick = 0;
    private int endTick;
    private int speed;
    private List<OrderBookEvent> listOrders;
    private List<OrderBookEvent> sentEvent = new ArrayList<>();
    private HashMap<OrderBookEvent, Future> eventFutureHashMap= new HashMap<>();

    public APIService ( int serviceNum, List<OrderBookEvent> listOrders, CountDownLatch c )
    {
        super( "APIService" + serviceNum, c );
        // TODO Implement this

        this.listOrders = listOrders;
    }

    @Override
    protected void initialize ()
    {
        subscribeBroadcast( TickBroadcast.class, ev -> {
            tick = ev.getTick();
            endTick = ev.getEndTick();
            speed = ev.getSpeed();
            if ( tick == endTick )
            {

                terminate();
            }
            else
            {
                for ( OrderBookEvent bookEvent : listOrders )
                {
                    if ( bookEvent.getTick() == tick )
                    {
                        System.out.println( "book sent "+bookEvent.getBookName() );
                        Future f = sendEvent( bookEvent );
                        sentEvent.add( bookEvent );
                     //   listOrders.remove( bookEvent );
                        eventFutureHashMap.put( bookEvent, f );
                    }
                }
              //  for (OrderBookEvent OBE :sentEvent )
             //   {
             //       if(listOrders.contains( OBE ))
                   //     listOrders.remove( OBE );
             //   }
                for ( OrderBookEvent e : sentEvent )
                {
                    OrderReceipt orderReceipt = (OrderReceipt) eventFutureHashMap.get( e ).get( ( endTick - tick ) * speed, t );
                    if ( orderReceipt != null )
                    {
                        e.getCustomer().adReceipt( orderReceipt );
                        System.out.println("sent de "+e.getBookName() +getName());
                        sendEvent( new DeliveryEvent() );
                        System.out.println( "delivery"+getName() );
                    }

                }
            sentEvent.clear();

            }
        } );
        c.countDown();
    }
}