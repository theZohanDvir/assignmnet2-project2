package bgu.spl.mics.application.passiveObjects;


import java.util.ArrayList;
import java.util.List;

/**
 * Passive object representing the store finance management.
 * It should hold a list of receipts issued by the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class MoneyRegister
{
    private static List<OrderReceipt> orList;
    private static MoneyRegister instance = null;

    /**
     * Retrieves the single instance of this class.
     */
    public static MoneyRegister getInstance ()
    {
        //TODO: Implement this
        if ( instance == null )
            instance = new MoneyRegister();
        return instance;
    }

    /**
     * Saves an order receipt in the money register.
     * <p>
     *
     * @param r The receipt to save in the money register.
     */
    public void file ( OrderReceipt r )
    {
        //TODO: Implement this.
        if ( orList == null )
            orList = new ArrayList<OrderReceipt>();
        orList.add( r );
    }

    /**
     * Retrieves the current total earnings of the store.
     */
    public int getTotalEarnings ()
    {
        int sum = 0;
        //TODO: Implement this
        for ( int i = 0 ; i < orList.size() ; i++ )
        {
            sum += orList.get( i ).getPrice();
        }
        return sum;
    }

    /**
     * Charges the credit card of the customer a certain amount of money.
     * <p>
     *
     * @param amount amount to charge
     */
    public void chargeCreditCard ( Customer c, int amount )
    {
        // TODO Implement this
        c.changeAmountOfCredit( amount );
    }

    /**
     * Prints to a file named @filename a serialized object List<OrderReceipt> which holds all the order receipts
     * currently in the MoneyRegister
     * This method is called by the main method in order to generate the output..
     */
    public void printOrderReceipts ( String filename )
    {
        //TODO: Implement this
    }
}
