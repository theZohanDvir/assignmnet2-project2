package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Future;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Passive object representing the resource manager.
 * You must not alter any of the given public methods of this class.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class ResourcesHolder
{
    private static ResourcesHolder resourcesHolder = null;
    private LinkedBlockingQueue<DeliveryVehicle> deliveryVehicles = new LinkedBlockingQueue<>(  );


    /**
     * Retrieves the single instance of this class.
     */
    private static class SingletonHolder
    {
        private static ResourcesHolder instance = new ResourcesHolder();
    }

    public static ResourcesHolder getInstance ()
    {
        return SingletonHolder.instance;

    }

    /**
     * Tries to acquire a vehicle and gives a future object which will
     * resolve to a vehicle.
     * <p>
     *
     * @return {@link Future<DeliveryVehicle>} object which will resolve to a
     * {@link DeliveryVehicle} when completed.
     */
    public Future<DeliveryVehicle> acquireVehicle ()
    {
        System.out.println( "try vic" );
        try
        {
            DeliveryVehicle ferarri = deliveryVehicles.take();
            System.out.println( "taken" );
            Future f = new Future();
            f.resolve( ferarri );
            return f;
        } catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Releases a specified vehicle, opening it again for the possibility of
     * acquisition.
     * <p>
     *
     * @param vehicle {@link DeliveryVehicle} to be released.
     */
    public void releaseVehicle ( DeliveryVehicle vehicle )
    {
        try
        {
            deliveryVehicles.put( vehicle );// put???
        } catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Receives a collection of vehicles and stores them.
     * <p>
     *
     * @param vehicles Array of {@link DeliveryVehicle} instances to store.
     */
    public void load ( DeliveryVehicle[] vehicles )
    {
        for ( DeliveryVehicle ferrari : vehicles )
        {
            try
            {
                deliveryVehicles.put( ferrari );
            } catch ( InterruptedException e )
            {
                e.printStackTrace();
            }

        }


    }

}
