package main.java.com.ramsmart.collections;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

import main.java.com.ramsmart.entities.Transaction;

public class TransactionList implements Serializable {

	private static final long serialVersionUID = 1L;

	// ArrayList adaptee
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

	/**
	 * Insert each transaction into a list of transactions.
	 * 
	 * @param transaction a transaction to add into the list.
	 * @return true if adding a transaction was successful
	 */
	public boolean addTransaction(Transaction transaction) {
		return this.transactions.add(transaction);
	}

	/**
	 * get a transaction by its id
	 * 
	 * @param id id of wanted transaction
	 * @return transaction with the parsed id if it exists.
	 */
	public ArrayList<Transaction> getTransactionById(int id) {
		ArrayList<Transaction> matchedTransactions = new ArrayList<>();

		for (Transaction transaction : transactions) {
			if (transaction.getTransactionId() == id) {
				matchedTransactions.add(transaction);
			}
		}
		return matchedTransactions;
	}
	
	public ArrayList<Transaction> getTransactionsByDates(Date startDate, Date endDate) {
		ArrayList<Transaction> transactionsByDates = new ArrayList<>();

		for (Transaction transaction : transactions) {
			if (transaction.betweenDates(startDate, endDate)) {
				transactionsByDates.add(transaction);
			}
		}
		return transactionsByDates;
	}
	

	/**
	 * This method overrides the toString method that displays the list of all the
	 * transactions.
	 */
	@Override
	public String toString() {
		String display = "All Transactions: \n";
		for (Transaction transaction : transactions) {
			display += transaction.toString() + "\n";
		}
		return display;
	}
}
