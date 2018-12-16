package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Message.TickBroadcast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
int speed;
int duration;
	public TimeService(int speed,int duration) {
		super("Change_This_Name");
		this.duration = duration;
		this.speed = speed;
	// TODO Implement this
	}

	@Override
	protected void initialize() {
		Timer T = new Timer();
		for (int i = 0; i <duration ; i++) {
			int finalI = i;
			T.schedule(new TimerTask() {
				@Override
				public void run() {
					sendBroadcast(new TickBroadcast(finalI,duration,speed));
				}
			},speed,speed);
		}

		
	}

}
