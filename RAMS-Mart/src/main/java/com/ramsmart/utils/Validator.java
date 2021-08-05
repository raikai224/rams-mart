package main.java.com.ramsmart.utils;

import java.util.Date;

import main.java.com.ramsmart.collections.MemberList;
import main.java.com.ramsmart.collections.Products;

public class Validator {

    private static Validator validator;

    private Validator() { }

    public static Validator instance() {
        return validator == null ? (validator = new Validator()) : validator;
    }

    public boolean validateNewProduct(String productId, String productName, Products products) {
        return (products.search(productId) == null && products.searchProductsByName(productName).isEmpty());
    }
    
    public boolean validateTwoDates(Date startDate, Date endDate) {
    	return (startDate.before(endDate) || startDate.equals(endDate));
    }
    
    public boolean validateMember(int memberId, MemberList memberList) {
    	return memberList.search(memberId) != null ? true : false;
    }
}
