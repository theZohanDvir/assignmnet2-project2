package bgu.spl.mics.application.passiveObjects;


import org.omg.PortableInterceptor.SUCCESSFUL;

import static bgu.spl.mics.application.passiveObjects.OrderResult.NOT_IN_STOCK;
import static bgu.spl.mics.application.passiveObjects.OrderResult.SUCCESSFULLY_TAKEN;

import static org.junit.Assert.*;

public class InventoryTest
{
    //hadar the great
    private BookInventoryInfo[] biiArr = null;
    private static Inventory inventory = Inventory.getInstance();
    BookInventoryInfo bii1 = new BookInventoryInfo( "AAA", 2, 25 );
    BookInventoryInfo bii2 = new BookInventoryInfo( "BBB", 3, 35 );

    @org.junit.Test
    public void getInstance ()
    {
        assertEquals( inventory, Inventory.getInstance() );
    }

    @org.junit.Test
    public void load ()
    {
        biiArr = new BookInventoryInfo[2];
        biiArr[0]=bii1;
        biiArr[0]=bii2;
        inventory.load( biiArr );
    }

    @org.junit.Test
    public void take ()
    {
        biiArr = new BookInventoryInfo[1];
        biiArr[0]=bii1;
        inventory.load( biiArr );
        assertEquals( SUCCESSFULLY_TAKEN, inventory.take( "AAA" ) );
        assertEquals( NOT_IN_STOCK, inventory.take( "CCC" ) );
    }

    @org.junit.Test
    public void checkAvailabiltyAndGetPrice ()
    {
        BookInventoryInfo[] temp={bii1,bii2};
        inventory.load(temp);
        assertEquals(25,inventory.checkAvailabiltyAndGetPrice("AAA"));
        assertEquals(35,inventory.checkAvailabiltyAndGetPrice("BBB"));
    }
}