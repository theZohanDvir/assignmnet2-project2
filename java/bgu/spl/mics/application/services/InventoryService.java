package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * InventoryService is in charge of the book inventory and stock.
 * Holds a reference to the {@link Inventory} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class InventoryService extends MicroService
{
    Inventory inventory = null;

    public InventoryService ()
    {
        super( "InventoryService" );
        // TODO Implement this
        inventory = Inventory.getInstance();
    }

    @Override
    protected void initialize ()
    {
        // TODO Implement this

    }

}
