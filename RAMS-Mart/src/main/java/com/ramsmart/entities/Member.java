package main.java.com.ramsmart.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import main.java.com.ramsmart.collections.TransactionList;

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter;

    private int memberId;
    private String memberName;
    private String memberPhoneNumber;
    private String memberAddress;
    private Date dateJoined;
    private double feePaid;
    private TransactionList transactions = new TransactionList();

    public Member(String name, String address, String phoneNumber, double feePaid) {
        this.memberId = ++idCounter;
        this.memberName = name;
        this.memberPhoneNumber = phoneNumber;
        this.memberAddress = address;
        this.dateJoined = new Date();
        this.feePaid = feePaid;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String name) {
        memberName = name;
    }

    public String getPhoneNumber() {
        return memberPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        memberPhoneNumber = phoneNumber;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String address) {
        memberAddress = address;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public double getFeePaid() {
        return feePaid;
    }

    public boolean addTransaction(Transaction transaction) {
        return transactions.addTransaction(transaction);
    }
    public ArrayList<Transaction> getTransactionsByDates(Date startDate, Date endDate){
    	return transactions.getTransactionsByDates(startDate, endDate);
    	
    }

    @Override
    public String toString() {
        return "Member [memberId=" + memberId + ", memberName=" + memberName + ", memberPhoneNumber="
                + memberPhoneNumber + ", memberAddress=" + memberAddress + ", dateJoined=" + dateJoined + "]";
    }

    public static void save(ObjectOutputStream output) throws IOException {
        output.writeObject(idCounter);
    }

    public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
        idCounter = (int) input.readObject();
    }
}
