package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.OrderBookEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

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

    TimeUnit t = TimeUnit.MILLISECONDS;
    int tick = 0;
    int endTick;
    int speed;
    List<OrderBookEvent> listOrders;
    List<OrderBookEvent> sentEvent;
    HashMap<OrderBookEvent, Future> eventFutureHashMap;

    public APIService ( int serviceNum, List<OrderBookEvent> listOrders,CountDownLatch c )
    {
        super( "APIService" + serviceNum,c );
        // TODO Implement this

        this.listOrders = listOrders;
        System.out.println( this.getName()+" cosnturct" );
    }

    @Override
    protected void initialize ()
    {
        System.out.println( this.getName()+" init" );
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
                    if ( bookEvent.getTick() <= tick )
                    {
                        Future f = sendEvent( bookEvent );
                        sentEvent.add( bookEvent );
                        listOrders.remove( bookEvent );
                        eventFutureHashMap.put( bookEvent, f );

                    }
                }
                for ( OrderBookEvent e : sentEvent )
                {
                    OrderReceipt orderReceipt = (OrderReceipt) eventFutureHashMap.get( e ).get( ( endTick - tick ) * speed, t );
                    if ( orderReceipt != null )
                    {
                        e.getCustomer().adReceipt( orderReceipt );
                        sendEvent( new DeliveryEvent() );
                    }

                }


            }
        } );
        c.countDown();
    }
}