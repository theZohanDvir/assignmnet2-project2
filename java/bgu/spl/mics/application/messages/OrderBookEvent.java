package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class OrderBookEvent implements Event<OrderReceipt>
{
    private String bookName;
    private int orderTick;
    private Customer customer;

    public OrderBookEvent ( String bookName, int orderTick, Customer customer )
    {
        this.bookName=bookName;
        this.orderTick=orderTick;
        this.customer=customer;
    }

    public String getBookName ()
    {
        return bookName;
    }

    public int getOrderTick ()
    {
        return orderTick;
    }

    public Customer getCustomer ()
    {
        return customer;
    }
}
