package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

public class ReturnVehicle implements Event {
    private DeliveryVehicle mazda;
    public ReturnVehicle(DeliveryVehicle mazda)
    {
        this.mazda = mazda;
    }
    public DeliveryVehicle getMazda()
    {
        return mazda;
    }
}
