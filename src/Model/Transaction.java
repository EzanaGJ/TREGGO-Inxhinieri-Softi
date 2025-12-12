package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Transaction {
    int transactionId;
    int orderId;
    int paymentId;
    double amount;
    String paymentType;
    Date createdAt;

    static Map<Integer, Transaction> transactionDB = new HashMap<>();

    void createTransaction() {
        this.createdAt = new Date();
        transactionDB.put(transactionId, this);
        System.out.println("Transaction created: ID = " + transactionId + ", Amount = " + amount);
    }

    void updateTransaction() {
        Transaction t = transactionDB.get(transactionId);
        if (t != null) {
            t.amount = this.amount;
            t.paymentType = this.paymentType;
            System.out.println("Transaction updated.");
        } else {
            System.out.println("Transaction not found!");
        }
    }

    void deleteTransaction() {
        if (transactionDB.remove(transactionId) != null) {
            System.out.println("Transaction deleted.");
        } else {
            System.out.println("Transaction not found!");
        }
    }

    void linkToOrder(int orderId) {
        this.orderId = orderId;
        System.out.println("Linked to order " + orderId);
    }

    void linkToPayment(int paymentId) {
        this.paymentId = paymentId;
        System.out.println("Linked to payment " + paymentId);
    }


    void assignUser() {
        System.out.println("User assigned to transaction " + transactionId);
    }

    void getAmount() {
        System.out.println("Transaction amount: " + amount);
    }
    void getTimestamp() {
        System.out.println("Created at: " + createdAt);
    }
    void validateTransaction() {
        if (amount <= 0) {
            System.out.println("Invalid transaction: amount must be positive!");
        } else if (paymentType == null || paymentType.isEmpty()) {
            System.out.println("Invalid transaction: payment type required!");
        } else {
            System.out.println("Transaction is valid.");
        }
    }
}