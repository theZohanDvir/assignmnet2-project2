package bgu.spl.mics.application.passiveObjects;


import static bgu.spl.mics.application.passiveObjects.OrderResult.NOT_IN_STOCK;
import static bgu.spl.mics.application.passiveObjects.OrderResult.SUCCESSFULLY_TAKEN;

/**
 * Passive data-object representing the store inventory.
 * It holds a collection of {@link BookInventoryInfo} for all the
 * books in the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */

public class Inventory
{
    private BookInventoryInfo[] bookInventoryInfo;
    private static Inventory inventory = null;

    /**
     * Retrieves the single instance of this class.
     */
    private static class SingletonHolder
    {
        private static Inventory instance = new Inventory();
    }

    public static Inventory getInstance ()
    {
        return SingletonHolder.instance;

    }

    /**
     * Initializes the store inventory. This method adds all the items given to the store
     * inventory.
     * <p>
     *
     * @param inventory Data structure containing all data necessary for initialization
     *                  of the inventory.
     */
    public void load ( BookInventoryInfo[] inventory )
    {
        BookInventoryInfo[] temp = new BookInventoryInfo[inventory.length + bookInventoryInfo.length];
        for ( int i = 0 ; i < bookInventoryInfo.length ; i++ )
        {
            temp[i] = bookInventoryInfo[i];
        }
        for ( int j = bookInventoryInfo.length ; j < temp.length ; j++ )
        {
            temp[j] = inventory[j - bookInventoryInfo.length];
        }
        bookInventoryInfo = temp;
    }

    /**
     * Attempts to take one book from the store.
     * <p>
     *
     * @param book Name of the book to take from the store
     * @return an {@link Enum} with options NOT_IN_STOCK and SUCCESSFULLY_TAKEN.
     * The first should not change the state of the inventory while the
     * second should reduce by one the number of books of the desired type.
     */
    public OrderResult take ( String book )
    {
        for ( BookInventoryInfo currbook : bookInventoryInfo )
        {
            if ( currbook.getBookTitle().equals( book ) )
                if ( currbook.getAmountInInventory() > 0 )
                {
                    currbook.reduceAmountInInventory();
                    return SUCCESSFULLY_TAKEN;
                }
        }
        return NOT_IN_STOCK;
    }

    /**
     * Checks if a certain book is available in the inventory.
     * <p>
     *
     * @param book Name of the book.
     * @return the price of the book if it is available, -1 otherwise.
     */
    public int checkAvailabiltyAndGetPrice ( String book )
    {

        for ( BookInventoryInfo currbook : bookInventoryInfo )
        {
            if ( currbook.getBookTitle().equals( book ) )
                return currbook.getPrice();
        }
        return -1;
    }

    /**
     * <p>
     * Prints to a file name @filename a serialized object HashMap<String,Integer> which is a Map of all the books in the inventory. The keys of the Map (type {@link String})
     * should be the titles of the books while the values (type {@link Integer}) should be
     * their respective available amount in the inventory.
     * This method is called by the main method in order to generate the output.
     */
    public void printInventoryToFile ( String filename )
    {
        //TODO: Implement this
    }
}
