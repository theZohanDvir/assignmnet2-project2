package bgu.spl.mics.application.passiveObjects;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class InventoryTest
{
    @Deployment
    public static JavaArchive createDeployment ()
    {
        return ShrinkWrap.create( JavaArchive.class ).addClass( Inventory.class ).addAsManifestResource( EmptyAsset.INSTANCE, "beans.xml" );
    }

    @org.junit.Test
    public void getInstance ()
    {
        Assert
    }

    @org.junit.Test
    public void load ()
    {
    }

    @org.junit.Test
    public void take ()
    {
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
