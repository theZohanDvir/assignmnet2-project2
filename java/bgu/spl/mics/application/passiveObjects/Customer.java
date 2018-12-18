package bgu.spl.mics.application.passiveObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a customer of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Customer {
	private int id;//: int – The id number of the customer.
	private String name;//: String – The name of the customer.
	private String address;//: String – The address of the customer.
	private int distance;//: int – the distance of the customer’s address from the store.
	private List <OrderReceipt> customerReceiptList= new ArrayList<>(  );//: List – all the receipts issued to the customer.
	private int creditCard;//: int – The number of the credit card of the customer.
	private int availableAmountInCreditCard;//: The remaining available amount of money in the creditcard of the customer.

	public Customer()
	{
	}
	public Customer(int id, String name, String address, int distance, int creditCard, int availableAmountInCreditCard)
	{
		this.id=id;
		this.name=name;
		this.address=address;
		this.distance=distance;
		this.creditCard=creditCard;
		this.availableAmountInCreditCard=availableAmountInCreditCard;
	}

	/**
     * Retrieves the name of the customer.
     */
	public String getName() { return name; }

	/**
     * Retrieves the ID of the customer  . 
     */
	public int getId() { return id; }
	
	/**
     * Retrieves the address of the customer.  
     */
	public String getAddress() { return address; }
	
	/**
     * Retrieves the distance of the customer from the store.  
     */
	public int getDistance() { return distance; }

	
	/**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     * @return A list of receipts.
     */
	public List<OrderReceipt> getCustomerReceiptList() { return customerReceiptList; }
	
	/**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     * @return Amount of money left.   
     */
	public int getAvailableCreditAmount() { return availableAmountInCreditCard; }
	
	/**
     * Retrieves this customers credit card serial number.    
     */
	public synchronized int getCreditNumber() { return creditCard; }

	public void adReceipt(OrderReceipt R){
		customerReceiptList.add(R);
	}

	public synchronized void amountToCharge(int amount){
		availableAmountInCreditCard=availableAmountInCreditCard-amount;
	}
	
}
