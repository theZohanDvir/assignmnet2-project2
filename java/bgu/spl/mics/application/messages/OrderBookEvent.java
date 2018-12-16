package bgu.spl.mics.application.Message;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class OrderBookEvent implements Event<OrderReceipt> {
    private String bookName;
    private Customer yosi;
    int tick;
   // private  int tick;
    public OrderBookEvent(String bookName,int tick, Customer yosi){
        this.bookName = bookName;
        this.tick = tick;
        this.yosi = yosi;
    }
    public String getBookName(){
        return bookName;
    }

    public Customer getYosi() {
        return yosi;
    }

    public int getTick() {
        return tick;
    }
}
