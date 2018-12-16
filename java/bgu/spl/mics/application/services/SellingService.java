package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Message.CheckAvailability;
import bgu.spl.mics.application.Message.OrderBookEvent;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.Message.TickBroadcast;

import java.util.concurrent.TimeUnit;

/**
 * Selling service in charge of taking orders from customers.
 * Holds a reference to the {@link MoneyRegister} singleton of the store.
 * Handles {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class SellingService extends MicroService{
	private  int tick = 1;
	private int speed;
	TimeUnit t=TimeUnit.MILLISECONDS;
    MoneyRegister moneyRegister;
    int endTick=1000;
	public SellingService() {
		super("Change_This_Name");
		moneyRegister = MoneyRegister.getInstance();
	}

	@Override
	protected void initialize() {
     subscribeEvent(OrderBookEvent.class, ev ->{
     	int prossTick = tick;
     	Future f = sendEvent(new CheckAvailability(ev.getBookName(),ev.getYosi().getAvailableCreditAmount()));
     	int result = (int) f.get((endTick-tick)*speed,t);

     	//create constractor Orderricipt
		 if(ev.getYosi().getAvailableCreditAmount()>= result)
		 {
			 moneyRegister.chargeCreditCard(ev.getYosi(),result);
			 OrderReceipt r = new OrderReceipt(this.getName(), result, ev.getBookName(),tick,ev.getTick(),prossTick);
			 moneyRegister.file(r);
			 this.complete(ev,r);
		 }

	 });
		subscribeBroadcast(TickBroadcast.class, ev->{
			tick = ev.getTick();
			endTick = ev.getEndTick();
			speed = ev.getSpeed();
		});
	}

}
