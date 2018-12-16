package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Message.GetVehicle;
import bgu.spl.mics.application.Message.ReturnVehicle;
import bgu.spl.mics.application.Message.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

import java.util.concurrent.TimeUnit;

/**
 * ResourceService is in charge of the store resources - the delivery vehicles.
 * Holds a reference to the {@link ResourceHolder} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ResourceService extends MicroService{
	private  int tick = 1;
	private int endTick;
	private  int speed;
	TimeUnit t=TimeUnit.MILLISECONDS;
  private 	ResourcesHolder resourcesHolder;

	public ResourceService() {
		super("Change_This_Name");
		resourcesHolder = ResourcesHolder.getInstance();
	}

	@Override
	protected void initialize() {
    subscribeEvent(GetVehicle.class, ev ->{
    	complete(ev, resourcesHolder.acquireVehicle().get());

	});
    subscribeEvent(ReturnVehicle.class, ev->{
    	resourcesHolder.releaseVehicle(ev.getMazda());
	});
		subscribeBroadcast(TickBroadcast.class, ev->{
			tick = ev.getTick();
			speed = ev.getSpeed();
			endTick = ev.getEndTick();
			if(tick==endTick)
				terminate();
		});
	}

}
