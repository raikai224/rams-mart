package main.java.com.ramsmart.entities;

import java.io.Serializable;

/**
 * Represents a single Transaction (issue, renew, etc.)
 * 
 * @author Syntish Masson
 *
 */
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	private String productId;
	private String productName;
	private int quantity;
	private double price;
	private double totalItemCost;

	/**
	 * Constructor
	 * 
	 * @param product - The product associated with the line item
	 * @param quantity - The quantity of product
	 * @param totalItemCost - The cost of the line item (product price * quantity)
	 */
	public Item(Product product, int quantity, double totalItemCost) {
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.quantity = quantity;
		this.price = product.getCurrentPrice();
		this.totalItemCost = totalItemCost;
	}

	public String getProductId() {
		return this.productId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public double getTotalItemCost() {
		return this.totalItemCost;
	}

	@Override
	public String toString() {
		return this.productName + " " + this.quantity + " $" + this.price + " $" + this.totalItemCost;
	}
}
