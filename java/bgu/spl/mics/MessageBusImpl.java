package bgu.spl.mics;

import javafx.util.Pair;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */

public class MessageBusImpl implements MessageBus
{
    // manage messages for each microService
    private ConcurrentHashMap<Class<MicroService>, LinkedBlockingQueue<Pair<MicroService, LinkedBlockingQueue<Message>>>> massegeManeger;
    // Connect which broadcast go to which microService
    private ConcurrentHashMap<Class<? extends Broadcast>, LinkedBlockingQueue<MicroService>> subsBroadcast;
    // Connect which event go to which microService
    private ConcurrentHashMap<Class<? extends Event>, LinkedBlockingQueue<MicroService>> subsEvent;

    private static MessageBusImpl messageBus = null;

    public static MessageBusImpl getInctance ()
    {
        if ( messageBus == null )
            messageBus = new MessageBusImpl();
        return messageBus;

    }

    @Override
    public <T> void subscribeEvent ( Class<? extends Event<T>> type, MicroService m )
    {
        if ( subsEvent.contains( type ) )
        {
            subsEvent.get( type ).add( m );
        }
        else
        {
            LinkedBlockingQueue<MicroService> que = new LinkedBlockingQueue();
            que.add( m );
            subsEvent.put( type, que );
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void subscribeBroadcast ( Class<? extends Broadcast> type, MicroService m )
    {
        if ( subsBroadcast.contains( type ) )
            subsBroadcast.get( type ).add( m );
        else
        {
            LinkedBlockingQueue<MicroService> que = new LinkedBlockingQueue();
            que.add( m );
            subsBroadcast.put( type, que );

        }
        // TODO Auto-generated method stub

    }

    @Override
    public <T> void complete ( Event<T> e, T result )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendBroadcast ( Broadcast b )
    {
        // TODO Auto-generated method stub

    }


    @Override
    public <T> Future<T> sendEvent ( Event<T> e )
    {

        MicroService m = subsEvent.get( e.getClass() ).peek();
        try
        {
            Pair<MicroService, LinkedBlockingQueue<Message>> temp = massegeManeger.get( m.getClass() ).take();
            temp.getValue().put( e );
            massegeManeger.get( m.getClass() ).put( temp );
        } catch ( InterruptedException e1 )
        {
            e1.printStackTrace();
        }


        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void register ( MicroService m )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregister ( MicroService m )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Message awaitMessage ( MicroService m ) throws InterruptedException
    {
        // TODO Auto-generated method stub
        return null;
    }


}
