package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a receipt that should
 * be sent to a customer after the completion of a BookOrderEvent.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class OrderReceipt
{
    private int orderId; // int – the id of the order.
    private String seller; // string - the name of the service which handled the order.
    private int customer; // id - the id of the customer the receipt is issued to.
    private String bookTitle; // string – title of the book bought.
    private int price; // int – the price the customer paid for the book.
    private int issuedTick; // int - tick in which this receipt was issued (upon completing the correspondingevent).
    private int orderTick; // int - tick in which the customer ordered the book.
    private int proccessTick; // int – tick in which the selling service started processing the order.

    public OrderReceipt(int customer, String bookTitle,int orderTick)
    {
        this.customer=customer;
        this.bookTitle=bookTitle;
        this.orderTick=orderTick;
    }

    /**
     * Retrieves the orderId of this receipt.
     */
    public int getOrderId ()
    {
        // TODO Implement this
        return orderId;
    }

    /**
     * Retrieves the name of the selling service which handled the order.
     */
    public String getSeller ()
    {
        // TODO Implement this
        return seller;
    }

    /**
     * Retrieves the ID of the customer to which this receipt is issued to.
     * <p>
     *
     * @return the ID of the customer
     */
    public int getCustomerId ()
    {
        // TODO Implement this
        return customer;
    }

    /**
     * Retrieves the name of the book which was bought.
     */
    public String getBookTitle ()
    {
        // TODO Implement this
        return bookTitle;
    }

    /**
     * Retrieves the price the customer paid for the book.
     */
    public int getPrice ()
    {
        // TODO Implement this
        return price;
    }

    /**
     * Retrieves the tick in which this receipt was issued.
     */
    public int getIssuedTick ()
    {
        // TODO Implement this
        return issuedTick;
    }

    /**
     * Retrieves the tick in which the customer sent the purchase request.
     */
    public int getOrderTick ()
    {
        // TODO Implement this
        return orderTick;
    }

    /**
     * Retrieves the tick in which the treating selling service started
     * processing the order.
     */
    public int getProcessTick ()
    {
        // TODO Implement this
        return proccessTick;
    }
}
