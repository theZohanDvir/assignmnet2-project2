package bgu.spl.mics.application;

import bgu.spl.mics.application.messages.OrderBookEvent;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner
{

    private static final String filePath = "input.json";

    public static void main ( String[] args )
    {
        Gson gson = new Gson();
        BookInventoryInfo[] bookInventoryInfos;
        DeliveryVehicle[] deliveryVehicles;
        Customer[] customers = new Customer[0];
        List<OrderBookEvent> boeList = new ArrayList<OrderBookEvent>();
        int timeSpeed = 0, duration = 0, selling = 0, inventoryService = 0, logistics = 0, resourcesService = 0;
        File jsonFile = Paths.get( "C:\\input.json" ).toFile();
        try
        {
            JsonObject jsonObject = gson.fromJson( new FileReader( jsonFile ), JsonObject.class );
            int size = jsonObject.getAsJsonArray( "initialInventory" ).size();
            bookInventoryInfos = new BookInventoryInfo[size];
            for ( int i = 0 ; i < size ; i++ )
            {
                bookInventoryInfos[i] = new BookInventoryInfo( jsonObject.getAsJsonArray( "initialInventory" ).get( i ).getAsJsonObject().get( "bookTitle" ).getAsString(), jsonObject.getAsJsonArray( "initialInventory" ).get( i ).getAsJsonObject().get( "amount" ).getAsInt(), jsonObject.getAsJsonArray( "initialInventory" ).get( i ).getAsJsonObject().get( "price" ).getAsInt() );
            }
            size = jsonObject.getAsJsonArray( "initialResources" ).get( 0 ).getAsJsonObject().get( "vehicles" ).getAsJsonArray().size();
            deliveryVehicles = new DeliveryVehicle[size];
            for ( int i = 0 ; i < size ; i++ )
            {
                deliveryVehicles[i] = new DeliveryVehicle( jsonObject.getAsJsonArray( "initialResources" ).get( 0 ).getAsJsonObject().get( "vehicles" ).getAsJsonArray().get( i ).getAsJsonObject().get( "license" ).getAsInt(), jsonObject.getAsJsonArray( "initialResources" ).get( 0 ).getAsJsonObject().get( "vehicles" ).getAsJsonArray().get( 0 ).getAsJsonObject().get( "speed" ).getAsInt() );
            }
            timeSpeed = jsonObject.getAsJsonObject( "services" ).get( "time" ).getAsJsonObject().get( "speed" ).getAsInt();
            duration = jsonObject.getAsJsonObject( "services" ).get( "time" ).getAsJsonObject().get( "duration" ).getAsInt();
            selling = jsonObject.getAsJsonObject( "services" ).get( "selling" ).getAsInt();
            inventoryService = jsonObject.getAsJsonObject( "services" ).get( "inventoryService" ).getAsInt();
            logistics = jsonObject.getAsJsonObject( "services" ).get( "logistics" ).getAsInt();
            resourcesService = jsonObject.getAsJsonObject( "services" ).get( "resourcesService" ).getAsInt();
            size = jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().size();
            customers = new Customer[size];
            for ( int i = 0 ; i < size ; i++ )
            {
                customers[i] = new Customer( jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "id" ).getAsInt(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "name" ).getAsString(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "address" ).getAsString(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "distance" ).getAsInt(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "creditCard" ).getAsJsonObject().get( "number" ).getAsInt(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "creditCard" ).getAsJsonObject().get( "amount" ).getAsInt() );
                int sizeOfOrders = jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "orderSchedule" ).getAsJsonArray().size();
                for ( int j = 0 ; j < sizeOfOrders ; j++ )
                {
                    boeList.add( new OrderBookEvent( jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( 0 ).getAsJsonObject().get( "orderSchedule" ).getAsJsonArray().get( j ).getAsJsonObject().get( "bookTitle" ).getAsString(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( 0 ).getAsJsonObject().get( "orderSchedule" ).getAsJsonArray().get( j ).getAsJsonObject().get( "tick" ).getAsInt(), customers[i] ) );
                }
            }

        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        List<Thread> list = new ArrayList<Thread>();
        CountDownLatch c = new CountDownLatch( 17 );
        for ( int i = 0 ; i < selling ; i++ )
        {// SellingService Thread Add
            list.add( new Thread( new SellingService( i + 1 ,c) ) );
        }
        for ( int i = 0 ; i < inventoryService ; i++ )
        {// InventoryService Thread Add
            list.add( new Thread( new InventoryService( i + 1 ,c) ) );
        }
        for ( int i = 0 ; i < logistics ; i++ )
        {// LogisticsService Thread Add
            list.add( new Thread( new LogisticsService( i + 1 ,c) ) );
        }
        for ( int i = 0 ; i < resourcesService ; i++ )
        {// ResourceService Thread Add
            list.add( new Thread( new ResourceService( i + 1 ,c) ) );
        }
        for ( int i = 0 ; i < customers.length ; i++ )
        {// APIService Thread Add
            list.add( new Thread( new APIService( i + 1, boeList ,c  ) ) );
        }
        for ( int i = 0 ; i < list.size() ; i++ )
        {// Run all Threads
            list.get( i ).start();
        }
        try
        {
            c.await();
        } catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        Thread time = new Thread( new TimeService( timeSpeed, duration ,c) );
        time.start();
        try
        {
            time.join();
        } catch ( InterruptedException e )
        {
            e.printStackTrace();
        }

    }
}
