package bgu.spl.mics.application;


import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
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
        Customer[] customers;
        List<BookOrderEvent> boeList = new ArrayList<BookOrderEvent>();
        int timeSpeed, duration, selling, inventoryService, logistics, resourcesService;
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
            System.out.println( jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( 0 ).getAsJsonObject().get( "orderSchedule" ).getAsJsonArray().size() );
            for ( int i = 0 ; i < size ; i++ )
            {
                customers[i] = new Customer( jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "id" ).getAsInt(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "name" ).getAsString(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "address" ).getAsString(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "distance" ).getAsInt(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "creditCard" ).getAsJsonObject().get( "number" ).getAsInt(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "creditCard" ).getAsJsonObject().get( "amount" ).getAsInt() );
                int sizeOfOrders = jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( i ).getAsJsonObject().get( "orderSchedule" ).getAsJsonArray().size();
                for ( int j = 0 ; j < sizeOfOrders ; j++ )
                {
                    boeList.add( new BookOrderEvent( jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( j ).getAsJsonObject().get( "orderSchedule" ).getAsJsonArray().get( j ).getAsJsonObject().get( "bookTitle" ).getAsString(), jsonObject.getAsJsonObject( "services" ).get( "customers" ).getAsJsonArray().get( j ).getAsJsonObject().get( "orderSchedule" ).getAsJsonArray().get( j ).getAsJsonObject().get( "tick" ).getAsInt(), customers[i] ) );
                }
            }

        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }



    }
}
