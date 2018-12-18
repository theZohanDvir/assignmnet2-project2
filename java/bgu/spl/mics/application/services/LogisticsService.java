package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.GetVehicle;
import bgu.spl.mics.application.messages.ReturnVehicle;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Logistic service in charge of delivering books that have been purchased to customers.
 * Handles {@link DeliveryEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LogisticsService extends MicroService
{
    private int tick = 1;
    private TimeUnit t = TimeUnit.MILLISECONDS;
    private int endTick;
    private int speed;

    public LogisticsService ( int serviceNum, CountDownLatch c )
    {
        super( "Logistic " + serviceNum, c );
        // TODO Implement this
    }

    @Override
    protected void initialize ()
    {
        subscribeEvent( DeliveryEvent.class, ev -> {
            System.out.println( "del Ev" );
            Future f = sendEvent( new GetVehicle() );
            DeliveryVehicle mazda = (DeliveryVehicle) f.get( ( endTick - tick ) * speed, t );
            System.out.println( "vehicle aquired" );
            mazda.deliver( "fgh", 5 );
            System.out.println( "deliver Complete" );
            sendEvent( new ReturnVehicle( mazda ) );
            complete( ev, null );
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
