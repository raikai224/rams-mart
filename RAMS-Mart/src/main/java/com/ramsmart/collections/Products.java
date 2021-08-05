package main.java.com.ramsmart.collections;

import java.io.Serializable;
import java.util.ArrayList;

import main.java.com.ramsmart.entities.Product;

public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<Product> products = new ArrayList<>();

    public Product search(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }

        return null;
    }

    public boolean addProduct(Product product) {
        return this.products.add(product);
    }

    /**
     * removes the passed product from the list of products
     *
     * @param product product to remove
     * @return boolean true if the member has been removed
     */
    public boolean removeProduct(Product product) {
        return this.products.remove(product);
    }

    /**
     * Adds to the products list a products that is already there. "Need to be
     * implemented if you guys agree"
     *
     * @param productId of the product to be added
     */
    public boolean addExistingProduct(String productId) {
        return false;
    }

    public ArrayList<Product> searchProductsByName(String name) {
        ArrayList<Product> matchedProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getProductName().toLowerCase().startsWith(name.toLowerCase())) {
                matchedProducts.add(product);
            }
        }
        return matchedProducts;
    }

    /**
     * finds a product through Id and changes the product's price to the price
     * passed as an argument
     *
     * @param productId - id of the product
     * @param newPrice  - the price to set the product to
     * @return boolean - true if the price was changed
     */
    public boolean changePrice(String productId, double newPrice) {
        Product product = search(productId);
        if (product != null) {
            product.setCurrentPrice(newPrice);
            return true;
        }

        return false;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * This method overrides the toString method that displays the list of all the
     * products.
     */
    @Override
    public String toString() {
        StringBuilder display = new StringBuilder("------ Products ------\n");
        for (Product product : products) {
            display.append(product.toString()).append("\n");
        }

        return display.toString();
    }
}
