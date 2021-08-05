package main.java.com.ramsmart;

import java.util.*;

import main.java.com.ramsmart.collections.MemberList;
import main.java.com.ramsmart.collections.Products;
import main.java.com.ramsmart.entities.*;
import main.java.com.ramsmart.utils.*;

public class UserInterface {

    private static UserInterface userInterface;
    private static RamsMart ramsMart;
    private final Scanner scanner = new Scanner(System.in);

    private UserInterface() {
        if (ramsMart == null) {
            ramsMart = RamsMart.loadSavedData();

            if (ramsMart == null) {
                ramsMart = RamsMart.instance();
            }
        }
    }

    public static UserInterface instance() {
        return userInterface == null ? (userInterface = new UserInterface()) : userInterface;
    }

    public void run() {
        int runCounter = 0;
        String askForInput = "---------- What would you like to do? ----------";
        System.out.println(askForInput);
        displayMenuOptions();
        while (runCounter != 1) {
            try {
                MenuOption option = MenuOption.getOption(scanner.nextInt());
                runCounter = selectOption(option);

                if (runCounter == 0) System.out.println(askForInput);
            } catch (InputMismatchException ex) {
                System.out.println("Invalid Input!");
                displayMenuOptions();

                scanner.next();
            }
        }
    }

    private void displayMenuOptions() {
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getOptionCode() + option.getOptionMessage());
        }
    }

    private int selectOption(MenuOption option) {
        if (option != null) {
            switch (option) {
                case EXIT:
                    exit();
                    return 1;
                case ENROLL_MEMBER:
                    enrollMember();
                    return 0;
                case REMOVE_MEMBER:
                    removeMember();
                    if (promptYesOrNo("Would you like to remove another member?")) {
                        selectOption(option);
                    }

                    return 0;
                case ADD_PRODUCT:
                    addProduct();
                    if (promptYesOrNo("Would you like to add another product?")) {
                        selectOption(option);
                    }

                    return 0;
                case CHECKOUT:
                    checkout();
                    if (promptYesOrNo("Would you like to checkout another member?")) {
                        selectOption(option);
                    }

                    return 0;
                case PROCESS_SHIPMENT:
                    System.out.println("Process Shipment");
                    return 0;
                case PRICE_CHANGE:
                    changePrice();
                    return 0;
                case RETRIEVE_PRODUCT_INFO:
                    retrieveProductInfo();
                    return 0;
                case RETRIEVE_MEMBER_INFO:
                    retrieveMemberInfo();
                    return 0;
                case PRINT_TRANSACTIONS:
                    printTransaction();
                    return 0;
                case LIST_OUTSTANDING_ORDERS:
                    listOutstandingOrders();
                    return 0;
                case LIST_ALL_MEMBERS:
                    listAllMembers();
                    return 0;
                case LIST_ALL_PRODUCTS:
                    listAllProducts();
                    return 0;
                case SAVE:
                    save();
                    return 0;
                case HELP:
                    displayMenuOptions();
                    return 0;
            }
        }
        throw new InputMismatchException();
    }

    private void exit() {
        System.out.println("Exiting System. Goodbye!");
        System.exit(1);
    }

    private void enrollMember() {
        String name = getUserInput("Enter in Member Name:");
        String address = getUserInput("Enter in Member Address:");
        String phoneNumber = getUserInput("Enter in Member Phone Number:");
        int memberId = ramsMart.enrollMember(name, address, phoneNumber);

        if (memberId != -1) {
            System.out.println("Successfully enrolled Member. MemberId is " + memberId);
        } else {
            System.out.println("Failed to enroll member!");
        }
    }

    private void removeMember() {
        try {
            int memberId = Integer.parseInt(getUserInput("Please enter in Member Id:"));

            if (ramsMart.removeMember(memberId)) {
                System.out.println("Member with ID of " + memberId + " removed successfully.");
            } else {
                System.out.println("Failed to remove Member with ID " + memberId + ". No Member found.");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid MemberId!");
        }
    }

    private void addProduct() {
        try {
            String productId = getUserInput("Please enter in the barcode/product Id:");
            String productName = getUserInput("Please enter in the product name:");
            double productPrice = Double.parseDouble(getUserInput("Please enter in the product price:"));
            int productReorderLevel = Integer.parseInt(getUserInput("Please enter in the product reorder level:"));

            if (ramsMart.addProduct(productId, productName, productPrice, productReorderLevel)) {
                System.out.println("Product created successfully!");
            } else {
                System.out.println("There was an error creating product! Product (id or name) already exists.");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid input, please ensure that the price and order level is a number!");
        }
    }

    private void checkout() {
        try {
            int memberId = Integer.parseInt(getUserInput("Please enter in Member Id:"));
            Transaction transaction = ramsMart.initiateCheckout(memberId);

            if (transaction != null) {
                addProductToTransaction(transaction);

                System.out.println(transaction + "\n");

                System.out.println("------ Orders ------");
                ramsMart.reduceStockLevels(transaction);
                System.out.println();
            } else {
                System.out.println("Member does not exist!");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid MemberId!");
        }
    }

    private void addProductToTransaction(Transaction transaction) {
        try {
            String productId = getUserInput("Please enter in the productId:");
            int quantity = Integer.parseInt(getUserInput("Please enter in the quantity:"));

            if (ramsMart.addItemToTransaction(transaction, productId, quantity)) {
                System.out.println("Product added successfully");
            } else {
                System.out.println("Failed to add product. Product may not exist.");
            }

            if (promptYesOrNo("Add another item?")) {
                addProductToTransaction(transaction);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid Quantity, please enter in a number.");
        }
    }

    private void changePrice() {

    }

    private void retrieveProductInfo() {
        String nameBeginsWith = getUserInput("Please enter in Product name to search");
        ArrayList<Product> products = ramsMart.retrieveProductInfo(nameBeginsWith);

        if (products.isEmpty()) {
            System.out.println("No products found by given name!");
        } else {
            for (Product product : products) {
                System.out.println("------------------------------");
                System.out.println(
                        "Product Id: " + product.getProductId() +
                                "\nName: " + product.getProductName() +
                                "\nPrice: " + product.getCurrentPrice() +
                                "\nStock On Hand: " + product.getStockOnHand() +
                                "\nReorder Level" + product.getReorderLevel()
                );
            }
            System.out.println();
        }
    }

    private void retrieveMemberInfo() {
        String nameBeginsWith = getUserInput("Please enter in Member name to search");
        ArrayList<Member> members = ramsMart.retrieveMemberInfo(nameBeginsWith);

        if (members == null) {
            System.out.println("No members found by given name!");
        } else {
            for (Member member : members) {
                System.out.println("------------------------------");
                System.out.println(
                        "MemberId: " + member.getMemberId() +
                                "\nAddress: " + member.getMemberAddress() +
                                "\nFee Paid: " + member.getFeePaid()
                );
            }
            System.out.println();
        }
    }

    private void printTransaction() {
    	int memberId = Integer.parseInt(getUserInput("Please enter in Member Id:"));
    	String startDate = getUserInput("Please enter in start date , MM/dd/yyyy");
    	String endDate = getUserInput("Please enter in end date, MM/dd/yyyy");
    	String errorMessage = ramsMart.validateTransactionRequest(memberId, startDate, endDate);
    	if(errorMessage != null) {
    		ArrayList<Transaction> transactions = ramsMart.getTransactionsByMemberAndDates(memberId, startDate, endDate);
    		if(transactions.isEmpty()) {
    			System.out.println("No transactions found for that member between those dates");
    		}else {
    			for (Transaction transaction : transactions) {
        			System.out.println("------------------------------");
        			System.out.println(transaction.toString());
        		}
    		}
    	}else {
    		System.out.println(errorMessage);
    	}
    }

    private void listOutstandingOrders() {
        ArrayList<Order> outstandingOrders = ramsMart.getOutstandingOrders();

        if (outstandingOrders.isEmpty()) {
            System.out.println("No Outstanding Orders found!");
        } else {
            for (Order order : outstandingOrders) {
                System.out.println("------------------------------");
                System.out.println("Order Id: " + order.getOrderId());
                System.out.println("Order Date: " + order.getOrderDate());
                for (OrderItem product : order.getOrderItems()) {
                    System.out.println("Product Name: " + product.getProductName());
                    System.out.println("Order Quantity: " + product.getOrderQuantity());
                }
            }
        }
    }

    private void listAllMembers() {
        MemberList members = ramsMart.getMembers();

        if (members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            System.out.println(members);
        }
    }

    private void listAllProducts() {
        Products products = ramsMart.getProducts();

        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println(products);
        }
    }

    private void save() {
        if (RamsMart.save()) {
            System.out.println("Data successfully saved!");
        }
    }

    private String getUserInput(String inputPrompt) {
    	//Should we use the scanner declared up top instead of making these new ones so we don't have resource leak?
        Scanner promptScanner = new Scanner(System.in);
        System.out.println(inputPrompt);

        return promptScanner.nextLine();
    }

    private boolean promptYesOrNo(String inputPrompt) {
        Scanner promptScanner = new Scanner(System.in);
        System.out.println(inputPrompt + " Y for YES, any other key for NO.");

        return promptScanner.nextLine().equalsIgnoreCase("y");
    }
}
