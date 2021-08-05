package main.java.com.ramsmart.collections;

import main.java.com.ramsmart.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;

public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<Order> orders = new ArrayList<>();

    public Order search(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public boolean addOrder(Order order) {
        return orders.add(order);
    }

    public ArrayList<Order> getUnprocessedOrders() {
        ArrayList<Order> outstandingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getShipmentDateReceived() == null) {
                outstandingOrders.add(order);
            }
        }
        return outstandingOrders;
    }
}
