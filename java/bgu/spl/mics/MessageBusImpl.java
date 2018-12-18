package bgu.spl.mics;

import bgu.spl.mics.application.services.ResourceService;
import bgu.spl.mics.application.services.SellingService;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus
{
    //    private ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue<Class<? extends Event>>> mapEvent = new ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue<Class<? extends Event>>>();
    //    private ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue<Class<? extends Broadcast>>> mapBroadcast = new ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue<Class<? extends Broadcast>>>();
    private ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue<Class<? extends Message>>> mapMessage = new ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue<Class<? extends Message>>>();
    private ConcurrentHashMap<Class<? extends Message>, LinkedBlockingQueue<Pair<MicroService, LinkedBlockingQueue<Message>>>> ultraDataBase2 = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Event, Future> backToTheFuture = new ConcurrentHashMap<Event, Future>();

    // UltaDataBase holds the
    private static class SingletonHolder
    {
        private static MessageBusImpl instance = new MessageBusImpl();
    }
    private MessageBusImpl(){

    }
    public static MessageBusImpl getInstance ()
    {
        return SingletonHolder.instance;
    }


    @Override
    public <T> void subscribeEvent ( Class<? extends Event<T>> type, MicroService m )
    {
        // TODO Auto-generated method stub
        if ( mapMessage.containsKey( m.getClass() ) )
        {
            mapMessage.get( m.getClass() ).add( type );
        }
        else
        {
            LinkedBlockingQueue<Class<? extends Message>> q = new LinkedBlockingQueue<Class<? extends Message>>();
            q.add( type );
            mapMessage.put( m.getClass(), q );
        }
    }

    @Override
    public void subscribeBroadcast ( Class<? extends Broadcast> type, MicroService m )
    {

        if ( mapMessage.containsKey( m.getClass() ) )
        {
            mapMessage.get( m.getClass() ).add( type );
        }
        else
        {
            LinkedBlockingQueue<Class<? extends Message>> q = new LinkedBlockingQueue<Class<? extends Message>> ();
            q.add( type );
            mapMessage.put( m.getClass(), q );
            return;
        }


    }

    @Override
    public <T> void complete ( Event<T> e, T result )
    {
            try {
                backToTheFuture.get( e ).resolve( result );
            }
            catch (NullPointerException ex){
               System.out.println(( this.backToTheFuture.size()) +" "+backToTheFuture.size()+" "+backToTheFuture.containsKey(e)+"!!!");
            }


    }

    @Override
    public void sendBroadcast ( Broadcast b )
    {
        for ( Pair<MicroService, LinkedBlockingQueue<Message>> pair : ultraDataBase2.get( b.getClass() ) )
        {
            try
            {
                pair.getValue().put( b );
            } catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public <T> Future<T> sendEvent ( Event<T> e )
    {
        Future future = new Future();
        //if (backToTheFuture == null)
        //{
        //  Pair<>	backToTheFuture = new
        //}
        backToTheFuture.put( e, future );

        try
        {
            LinkedBlockingQueue msQ = ultraDataBase2.get( e.getClass() );
            if ( msQ == null )
            {
                return null;
            }
            Pair<MicroService, LinkedBlockingQueue<Message>> tmp = ultraDataBase2.get( e.getClass() ).take();

            tmp.getValue().put( e );
            ultraDataBase2.get( e.getClass() ).put( tmp );

        } catch ( InterruptedException e1 )
        {
            e1.printStackTrace();
        }


        return future;
    }

    @Override
    public void register ( MicroService m )
    {
        if(m.getClass()== ResourceService.class){
            int i=0;
            i++;
        }
        LinkedBlockingQueue<Message> q = new LinkedBlockingQueue<Message>();
        for ( Class<? extends Message> messageType : mapMessage.get( m.getClass() ) )
        {
            Pair<MicroService, LinkedBlockingQueue<Message>> tmpPair = new Pair( m, q );
            try
            {
               if ( ultraDataBase2.get( messageType ) == null ) {
                    ultraDataBase2.put(messageType, new LinkedBlockingQueue<Pair<MicroService, LinkedBlockingQueue<Message>>>());
                }
                ultraDataBase2.get( messageType ).put( tmpPair );
            }  catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
        }
        // LinkedBlockingQueue <Message> q = new LinkedBlockingQueue<>();
        //Pair <MicroService,LinkedBlockingQueue<Message>>tmpPair = new Pair(m,q);
        //ultraDataBase.get(m.getClass()).add(tmpPair);

    }

    @Override
    public void unregister ( MicroService m )
    {


    //    Class<? extends Message> e = mapMessage.get( m ).peek();
        System.out.println("WTF "+m.getName());
     //   ultraDataBase2.get( e.getClasses() ).clear();

    }

    @Override
    public Message awaitMessage ( MicroService m ) throws InterruptedException
    {

        for ( Pair<MicroService, LinkedBlockingQueue<Message>> pair : ultraDataBase2.get( mapMessage.get( m.getClass() ).peek() ) )
        {
            if ( pair.getKey() == m )
            {
                return pair.getValue().take();
            }
        }
        System.out.println("Null Returned + awaitMessage "+m.getName());
        return null;
    }
}
