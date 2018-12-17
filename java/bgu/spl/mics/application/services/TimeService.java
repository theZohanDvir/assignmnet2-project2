package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService
{
    private int speed;
    private int duration;

    public TimeService ( int speed, int duration, CountDownLatch c )
    {
        super( "TimerService ", c );
        this.duration = duration;
        this.speed = speed;
        System.out.println( this.getName() + " cosnturct" );
        // TODO Implement this
    }

    @Override
    protected void initialize ()
    {
        System.out.println( this.getName() + " init" );
        Timer T = new Timer();
        //int  finalI =1;
        T.schedule( new TimerTask()
        {
            int i = 1;

            @Override

            public void run ()
            {


                if ( i <= duration )
                {
                    sendBroadcast( new TickBroadcast( i, duration, speed ) );
                    i++;
                    System.out.println( "Current Tick: " + i );
                }
                else
                    terminate();
            }

        }, 0, speed );


    }

}
