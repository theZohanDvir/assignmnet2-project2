package bgu.spl.mics.application.passiveObjects;


import org.omg.PortableInterceptor.SUCCESSFUL;

import static bgu.spl.mics.application.passiveObjects.OrderResult.NOT_IN_STOCK;
import static bgu.spl.mics.application.passiveObjects.OrderResult.SUCCESSFULLY_TAKEN;

import static org.junit.Assert.*;

public class InventoryTest
{
    private BookInventoryInfo[] biiArr = null;
    private static Inventory inventory = new Inventory();
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
        assertEquals( SUCCESSFULLY_TAKEN, inventory.take( "AAA" ) );
        assertEquals( NOT_IN_STOCK, inventory.take( "CCC" ) );
    }

    @org.junit.Test
    public void checkAvailabiltyAndGetPrice ()
    {

    }

    @org.junit.Test
    public void printInventoryToFile ()
    {
    }
}