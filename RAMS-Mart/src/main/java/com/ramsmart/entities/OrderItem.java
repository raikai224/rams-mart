package main.java.com.ramsmart.entities;

import java.io.Serializable;

public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productId;
    private String productName;
    private int orderQuantity;
    private boolean checkedIn = false;

    public OrderItem(String productId, String productName, int orderQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.orderQuantity = orderQuantity;
    }

    public boolean checkIn() {
        return checkedIn = true;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    @Override
    public String toString() {
        return "Product Id: " + this.productId + ", Product Name: " + this.productName + ", Order Quantity: " + this.orderQuantity;
    }
}
