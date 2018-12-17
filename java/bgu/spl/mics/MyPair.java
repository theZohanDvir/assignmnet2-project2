package bgu.spl.mics;

public class MyPair<T extends Message> {
    public Class<T> msgClass;
    public Callback<T> callback;
    public MyPair(Class<T> msgClass,Callback<T> callback){
        this.msgClass=msgClass;
        this.callback=callback;
    }
    public <E extends Message> void call(E msg){
        if(msgClass.isInstance(msg))
            callback.call(msgClass.cast(msg));
    }
    public Class<T> getKey()
    {
        return msgClass;
    }
}