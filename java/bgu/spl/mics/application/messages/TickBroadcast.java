package bgu.spl.mics.application.Message;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
    int tick;
    int endTick;
    int speed;
    public TickBroadcast(int tick,int endTick,int speed){
        this.tick = tick;
        this.endTick = endTick;
        this.speed = speed;
    }

    public int getTick() {
        return tick;
    }

    public int getEndTick() {
        return endTick;
    }

    public int getSpeed() {
        return speed;
    }
}
