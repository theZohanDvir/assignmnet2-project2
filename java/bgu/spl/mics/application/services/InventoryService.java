package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CheckAvailability;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

import java.util.concurrent.TimeUnit;

/**
 * InventoryService is in charge of the book inventory and stock.
 * Holds a reference to the {@link Inventory} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class InventoryService extends MicroService{
	private  int tick = 1;

	private  int endTick;
	//private  CheckAvailability book;
	Inventory inventory;


	public InventoryService(int serviceNum ) {
		super("inv"+serviceNum);
		inventory = Inventory.getInstance();
		System.out.println( this.getName()+" cosnturct" );
	}

	@Override
	protected void initialize() {
		System.out.println( this.getName()+" init" );
     subscribeEvent(CheckAvailability.class, ev ->{
     	int price=-1;
	synchronized (inventory) {
		price = inventory.checkAvailabiltyAndGetPrice(ev.getBookName());

		if (ev.getCredit() >= price) {
			inventory.take(ev.getBookName());

		}
	}
		 complete(ev, price);
	 });
		subscribeBroadcast(TickBroadcast.class, ev->{
			tick = ev.getTick();
			endTick = ev.getEndTick();

			if(tick==endTick){
				terminate();
			}
		});


	}

}
