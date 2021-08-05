package main.java.com.ramsmart.utils;


public enum MenuOption {
    EXIT(0, ": Exit"),
    ENROLL_MEMBER(1, ": Enroll a Member"),
    REMOVE_MEMBER(2, ": Remove a Member"),
    ADD_PRODUCT(3, ": Add a Product"),
    CHECKOUT(4, ": Checkout a Member"),
    PROCESS_SHIPMENT(5, ": Process a Shipment"),
    PRICE_CHANGE(6, ": Change Product Price"),
    RETRIEVE_PRODUCT_INFO(7, ": Retrieve Product Info"),
    RETRIEVE_MEMBER_INFO(8, ": Retrieve Member Info"),
    PRINT_TRANSACTIONS(9, ": Print Transactions"),
    LIST_OUTSTANDING_ORDERS(10, ": List Outstanding Orders"),
    LIST_ALL_MEMBERS(11, ": List all Members"),
    LIST_ALL_PRODUCTS(12, ": List all Products"),
    SAVE(13, ": Save Data"),
    HELP(14, ": Help");

    private final int optionCode;
    private final String optionMessage;

    private MenuOption(int optionCode, String optionMessage) {
        this.optionCode = optionCode;
        this.optionMessage = optionMessage;
    }

    public static MenuOption getOption(int selectedOption) {
        for (MenuOption option : MenuOption.values()) {
            if (option.optionCode == selectedOption) {
                return option;
            }
        }
        return null;
    }

    public int getOptionCode() {
        return optionCode;
    }

    public String getOptionMessage() {
        return optionMessage;
    }
}
