package main.java.com.ramsmart;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import main.java.com.ramsmart.collections.*;
import main.java.com.ramsmart.entities.*;
import main.java.com.ramsmart.utils.Validator;

public class RamsMart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Orders orders = new Orders();
    private MemberList members = new MemberList();
    private Products products = new Products();
    private FeePaid feePaid = new FeePaid();
    private static Validator validator = Validator.instance();
    private static RamsMart ramsMart;

    private RamsMart() {

    }

    public static RamsMart instance() {
        return ramsMart == null ? (ramsMart = new RamsMart()) : ramsMart;
    }

    public int enrollMember(String name, String address, String phoneNumber) {
        Member member = new Member(name, address, phoneNumber, feePaid.getFeePaid());

        if (members.addMember(member)) {
            return member.getMemberId();
        }

        return -1;
    }

    public boolean removeMember(int memberId) {
        return members.deleteMember(memberId);
    }

    public boolean addProduct(String productId, String productName, double productPrice, int reorderLevel) {
        if (validator.validateNewProduct(productId, productName, products)) {
            if (products.addProduct(new Product(productId, productName, productPrice, reorderLevel))) {
                Order order = new Order(productId, productName, reorderLevel*2);

                return orders.addOrder(order);
            }
        }

        return false;
    }

    public Transaction initiateCheckout(int memberId) {
        Member member = members.search(memberId);

        if (member != null) {
            Transaction transaction = new Transaction();
            if (member.addTransaction(transaction)) {
                return transaction;
            }
        }

        return null;
    }

    public boolean addItemToTransaction(Transaction transaction, String productId, int quantity) {
        Product product = products.search(productId);

        if (product != null) {
            return transaction.addItem(product, quantity);
        }

        return false;
    }

    public void reduceStockLevels(Transaction transaction) {
        if (transaction != null) {
            for (Item item : transaction.getItems()) {
                Product product = products.search(item.getProductId());

                if (product != null) {
                    if (product.reduceOnHands(item.getQuantity()) <= product.getReorderLevel()) {
                        Order order = new Order(product.getProductId(), product.getProductName(), 2*product.getReorderLevel());
                        orders.addOrder(order);

                        System.out.println(order);
                    }
                }
            }
        }
    }

    public ArrayList<Product> retrieveProductInfo(String name) {
        return products.searchProductsByName(name);
    }

    public ArrayList<Member> retrieveMemberInfo(String name) {
        return members.searchMembersByName(name);
    }

    // todo: update this to actually process the order
    public boolean processOrderItem(String productId, String productName, int quantity, int orderId) {
        Order order = orders.search(orderId);
        OrderItem orderItem = order.searchProduct(productId);
        if (orderItem == null) {
            return false;
        }

        return orderItem.checkIn();
    }

    public ArrayList<Order> getOutstandingOrders() {
        return orders.getUnprocessedOrders();
    }

    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("RamsMart");
            ObjectOutputStream output = new ObjectOutputStream(file);

            output.writeObject(ramsMart);
            Member.save(output);
            Order.save(output);
            Transaction.save(output);
            file.close();

            return true;
        } catch (IOException ex) {
            System.out.println("Error saving RAMS data. Exception: " + ex.getMessage());
            return false;
        }
    }
    
    public String validateTransactionRequest(int memberId, String startDateString, String endDateString) {
    	try {
    		Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDateString);
    		Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDateString);
    		if(!validator.validateMember(memberId, members)) {
        		return "Invalid member Id";
        	}else if(!validator.validateTwoDates(startDate, endDate)) {
        		return "The provided start date does not come before the end date";
        	}else {
        		return null;
        	}
    	} catch (ParseException pe) {
            return "Invalid dates";
        }
    	
    }

    public MemberList getMembers() {
        return members;
    }

    public Products getProducts(){
        return products;
    }
    
    public ArrayList<Transaction> getTransactionsByMemberAndDates(int memberId, String startDateString, String endDateString) {
    	try {
    		Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDateString);
    		Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDateString);
    		Member member = members.search(memberId);
    		return member.getTransactionsByDates(startDate, endDate);
    	}catch (ParseException pe) {
    		return null;
        }
    }

    public static RamsMart loadSavedData() {
        try {
            FileInputStream file = new FileInputStream("RamsMart");
            ObjectInputStream input = new ObjectInputStream(file);

            ramsMart = (RamsMart) input.readObject();
            Member.retrieve(input);
            Order.retrieve(input);
            Transaction.retrieve(input);

            return ramsMart;
        } catch (IOException ex) {
            System.out.println("Some data not found.");

            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("No data found, loading new application!");

            return null;
        }
    }
}
