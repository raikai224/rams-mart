package main.java.com.ramsmart.entities;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productId;
    private String productName;
    private double currentPrice;
    private int stockOnHand;
    private int reorderLevel;

    public Product(String productId, String productName, double currentPrice, int reorderLevel) {
        this.productId = productId;
        this.productName = productName;
        this.currentPrice = currentPrice;
        this.reorderLevel = reorderLevel;
        this.stockOnHand = 0;
    }

    public int getStockOnHand() {
        return stockOnHand;
    }

    public void setStockOnHand(int stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public int reduceOnHands(int quantity) {
        return this.stockOnHand -= quantity;
    }

    @Override
    public String toString() {
        return "Product: productId=" + productId + ", productName=" + productName + ", stockOnHand=" + stockOnHand
                + ", currentPrice=" + currentPrice + ", reorderLevel=" + reorderLevel + ";";
    }
}
