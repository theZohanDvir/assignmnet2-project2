package bgu.spl.mics;

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
public class MessageBusImpl implements MessageBus {


	//private ConcurrentHashMap<Class<? extends Event>, LinkedBlockingQueue <MicroService>> mapEvent;
	private ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue <Class<? extends Event>>> mapEvent;
	//private ConcurrentHashMap<Class<? extends Broadcast>, LinkedBlockingQueue <MicroService>> mapBroadcast;
	private ConcurrentHashMap<Class<? extends MicroService>, LinkedBlockingQueue <Class<? extends Broadcast>>> mapBroadcast;
	//private ConcurrentHashMap<Class <MicroService>, LinkedBlockingQueue<Pair<MicroService,LinkedBlockingQueue<Message>>>> ultraDataBase ;
	private ConcurrentHashMap<Class<? extends Message>,LinkedBlockingQueue<Pair<MicroService,LinkedBlockingQueue<Message>>>> ultraDataBase2;
	private ConcurrentHashMap<Event,Future> backToTheFuture;
	// UltaDataBase holds the
	private class SingletoneHolder{

		public MessageBusImpl instance=new MessageBusImpl();

	}
	public static MessageBusImpl getInstance() {

		return new MessageBusImpl();
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub

		if (mapEvent.contains(m.getClass()))
		{
			mapEvent.get(m.getClass()).add(type);
		}
		else
		{
			LinkedBlockingQueue<Class<? extends Event>> q = new LinkedBlockingQueue();
			q.add(type);
			mapEvent.put(m.getClass(), q);
		}

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {

		if (mapBroadcast.contains(m.getClass()))
		{
			mapBroadcast.get(m.getClass()).add(type);
		}
		else
		{
			LinkedBlockingQueue<Class<? extends Broadcast>> q = new LinkedBlockingQueue();
			q.add(type);
			mapBroadcast.put(m.getClass(), q);
		}


	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		backToTheFuture.get(e).resolve(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		for (Pair<MicroService,LinkedBlockingQueue<Message>> pair :ultraDataBase2.get(b.getClass())  )
		{
			try {
				pair.getValue().put(b);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public <T> Future<T> sendEvent(Event<T> e) {

		try {
			LinkedBlockingQueue msQ = ultraDataBase2.get(e.getClass());
			if (msQ == null)
			{
				return null;
			}
			Pair <MicroService,LinkedBlockingQueue<Message>> tmp =ultraDataBase2.get(e.getClass()).take();

			tmp.getValue().put(e);
			ultraDataBase2.get(e.getClass()).put(tmp);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Future future = new Future();
		//if (backToTheFuture == null)
		//{
		//  Pair<>	backToTheFuture = new
		//}
		backToTheFuture.put(e,future);
		return future;
	}

	@Override
	public void register(MicroService m) {

		for( Class<? extends Event> eventType :mapEvent.get(m.getClass()))
		{
			LinkedBlockingQueue <Message> q = new LinkedBlockingQueue<>();
			Pair <MicroService,LinkedBlockingQueue<Message>>tmpPair = new Pair(m,q);
			try {
				ultraDataBase2.get(eventType).put(tmpPair);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// LinkedBlockingQueue <Message> q = new LinkedBlockingQueue<>();
		//Pair <MicroService,LinkedBlockingQueue<Message>>tmpPair = new Pair(m,q);
		//ultraDataBase.get(m.getClass()).add(tmpPair);

	}

	@Override
	public void unregister(MicroService m)
	{
		Class<? extends Event> e=mapEvent.get(m).peek();
		ultraDataBase2.get(e.getClasses()).clear();

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		for (Pair<MicroService,LinkedBlockingQueue<Message>> pair : ultraDataBase2.get(mapEvent.get(m.getClass()).peek()))
		{
			if (pair.getKey() == m)
			{
				return pair.getValue().take();
			}
		}
		//for (Pair<MicroService, LinkedBlockingQueue<Message>> pair :ultraDataBase.get(m.getClass()))
		//	{
		// if(pair.getKey() == m)
		//  {
		//  	return pair.getValue().take();
		//  }

		//}
		return null;
	}
}
