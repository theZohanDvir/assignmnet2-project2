package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.GetVehicle;
import bgu.spl.mics.application.messages.ReturnVehicle;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ResourceService is in charge of the store resources - the delivery vehicles.
 * Holds a reference to the {@link ResourceHolder} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link MoneyRegister}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ResourceService extends MicroService
{
    private int tick = 1;
    private int endTick;
    private int speed;
    private TimeUnit t = TimeUnit.MILLISECONDS;
    private ResourcesHolder resourcesHolder;

    public ResourceService ( int serviceNum, CountDownLatch c )
    {
        super( "Resource" + serviceNum, c );
        resourcesHolder = ResourcesHolder.getInstance();
        System.out.println( this.getName() + " cosnturct" );
    }

    @Override
    protected void initialize ()
    {
        System.out.println( this.getName() + " init" );
        subscribeEvent( GetVehicle.class, ev -> {
            complete( ev, resourcesHolder.acquireVehicle().get() );

        } );
        subscribeEvent( ReturnVehicle.class, ev -> {
            resourcesHolder.releaseVehicle( ev.getMazda() );
        } );
        subscribeBroadcast( TickBroadcast.class, ev -> {
            tick = ev.getTick();
            speed = ev.getSpeed();
            endTick = ev.getEndTick();
            if ( tick == endTick )
                terminate();
        } );
        c.countDown();
    }

}
