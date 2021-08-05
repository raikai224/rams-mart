package main.java.com.ramsmart.entities;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter;
    private int orderId = 1;
    private Date orderDate;
    private Date shipmentDateReceived;
    private final ArrayList<OrderItem> orderItems = new ArrayList<>();

    public Order() {
        this.orderId = ++idCounter;
        this.orderDate = new Date();
    }

    public Order(String productId, String productName, int orderQuantity) {
        this.orderId = ++idCounter;
        this.orderDate = new Date();
        this.orderItems.add(new OrderItem(productId, productName, orderQuantity));
    }

    public boolean addProduct(String productId, String productName, int orderQuantity) {
        return this.orderItems.add(new OrderItem(productId, productName, orderQuantity));
    }

    public boolean addProduct(OrderItem orderItem) {
        return this.orderItems.add(orderItem);
    }

    public OrderItem searchProduct(String productId) {
        for (OrderItem orderItem : this.orderItems) {
            if (orderItem.getProductId().equals(productId)) {
                return orderItem;
            }
        }

        return null;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public Date getShipmentDateReceived() {
        return this.shipmentDateReceived;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return this.orderItems;
    }


    public static void save(ObjectOutputStream output) throws IOException {
        output.writeObject(idCounter);
    }

    public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
        idCounter = (int) input.readObject();
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder("--- Order #" + this.orderId + " ---\n");
        for (OrderItem orderItem : orderItems) {
            display.append(orderItem).append("\n");
        }

        return display.toString();
    }
}
