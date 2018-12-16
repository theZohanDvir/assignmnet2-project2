package bgu.spl.mics.application.passiveObjects;

import java.util.List;

/**
 * Passive data-object representing a customer of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Customer
{
    private int id; // int – The id number of the customer.
    private String name; // String – The name of the customer.
    private String address; // String – The address of the customer.
    private int distance; // int – the distance of the customer’s address from the store.
    private List<OrderReceipt> Receipts; // List – all the receipts issued to the customer.
    private int creditCard; // int – The number of the credit card of the customer.
    private int availableAmountInCreditCard; // The remaining available amount of money in the creditcardof the custome

    public Customer(int id, String name, String address,int distance, int creditCard, int availableAmountInCreditCard)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.distance=distance;
        this.creditCard=creditCard;
        this.availableAmountInCreditCard=availableAmountInCreditCard;
    }

    public void changeAmountOfCredit( int amount )
    {
        //change the current amount of money to the given one.
        availableAmountInCreditCard = amount;
    }

    /**
     * Retrieves the name of the customer.
     */
    public String getName ()
    {
        // TODO Implement this
        return name;
    }

    /**
     * Retrieves the ID of the customer  .
     */
    public int getId ()
    {
        // TODO Implement this
        return id;
    }

    /**
     * Retrieves the address of the customer.
     */
    public String getAddress ()
    {
        // TODO Implement this
        return address;
    }

    /**
     * Retrieves the distance of the customer from the store.
     */
    public int getDistance ()
    {
        // TODO Implement this
        return distance;
    }


    /**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     *
     * @return A list of receipts.
     */
    public List<OrderReceipt> getCustomerReceiptList ()
    {
        // TODO Implement this
        return Receipts;
    }

    /**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     *
     * @return Amount of money left.
     */
    public int getAvailableCreditAmount ()
    {
        // TODO Implement this
        return availableAmountInCreditCard;
    }

    /**
     * Retrieves this customers credit card serial number.
     */
    public int getCreditNumber ()
    {
        // TODO Implement this
        return creditCard;
    }
}
