package main.java.com.ramsmart.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a single Transaction
 *
 * @author Syntish Masson
 */
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;
    private int transactionId;
    private Date date;
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Creates the transaction with a given type and product name. The date is the
     * current date. Generates and assigns a unique transaction id.
     */
    public Transaction() {
        this.transactionId = ++idCounter;
        this.date = new Date();
    }

    /**
     * Getter for transactionId
     *
     * @return id associated with transaction
     */
    public int getTransactionId() {
        return this.transactionId;
    }

    /**
     * Getter for items
     *
     * @return items list of the transaction
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * Getter for date
     *
     * @return date of the transaction
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Add new Item to Items List. Compute the total cost by multiplying product
     * cost with quantity
     *
     * @param product
     * @param quantity
     */
    public boolean addItem(Product product, int quantity) {
        double productPrice = product.getCurrentPrice();
        double totalItemCost = productPrice * quantity;
        Item item = new Item(product, quantity, totalItemCost);

        return this.items.add(item);
    }

//    /**
//     * Checks if transaction is on given date
//     *
//     * @param date The date for which transactions is being sought
//     * @return true if the dates match
//     */
//    public boolean onDate(Calendar date) {
//        return ((date.get(Calendar.YEAR) == this.date.get(Calendar.YEAR))
//                && (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH))
//                && (date.get(Calendar.DATE) == this.date.get(Calendar.DATE)));
//    }
//
//    /**
//     * Checks if transaction is between two specified dates
//     *
//     * @param startDate start date
//     * @param endDate   end date
//     * @return true if the transaction was between the two dates
//     */
    public boolean betweenDates(Date startDate, Date endDate) {
        return ((this.date.after(startDate) && this.date.before(endDate)) || this.date.equals(startDate)
                || this.date.equals(endDate));
    }
//
//    /**
//     * Returns the date as a String
//     *
//     * @return date with month, date, and year
//     */
//    public String getFormattedDate() {
//        return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
//    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Item item : items) {
            totalPrice += item.getTotalItemCost();
        }

        return totalPrice;
    }

    /**
     * Transaction toString method
     *
     * @return string representation of the Transaction object
     */
    @Override
    public String toString() {
        StringBuilder display = new StringBuilder("------ Transaction #" + this.transactionId + "------\n");
        for (Item item : items) {
            display.append(item).append("\n");
        }
        display.append("\nTotal Price: ").append(calculateTotalPrice());

        return display.toString();
    }

    /**
     * Method for deserializing idCounter data file. Assigns the deserialized
     * variable to idCounter.
     *
     * @param input input stream used to read the data file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
        Transaction.idCounter = (int) input.readObject();
    }

    /**
     * Serializes idCounter
     *
     * @param output output stream used to serialize the idCounter
     * @throws IOException
     */
    public static void save(ObjectOutputStream output) throws IOException {
        output.writeObject(idCounter);
    }
}
