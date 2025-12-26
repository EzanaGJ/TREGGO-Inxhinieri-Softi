package Service;

import DAO.TransactionDAO;
import Model.Transaction;

import java.util.Date;

public class TransactionService {
    private final TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public void createTransaction(Transaction transaction) {
        transaction.setCreatedAt(new Date());
        transactionDAO.createTransaction(transaction);
        System.out.println("Transaction created: ID = " + transaction.getTransactionId() + ", Amount = " + transaction.getAmount());
    }

    public void updateTransaction(Transaction transaction) {
        transactionDAO.updateTransaction(transaction);
        System.out.println("Transaction updated: ID = " + transaction.getTransactionId());
    }

    public void deleteTransaction(int transactionId) {
        transactionDAO.deleteTransaction(transactionId);
        System.out.println("Transaction deleted: ID = " + transactionId);
    }

    public void linkToOrder(Transaction transaction, int orderId) {
        transaction.setOrderId(orderId);
        System.out.println("Transaction " + transaction.getTransactionId() + " linked to order " + orderId);
    }

    public void linkToPayment(Transaction transaction, int paymentId) {
        transaction.setPaymentId(paymentId);
        System.out.println("Transaction " + transaction.getTransactionId() + " linked to payment " + paymentId);
    }

    public void validateTransaction(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            System.out.println("Invalid transaction: amount must be positive!");
        } else if (transaction.getPaymentType() == null || transaction.getPaymentType().isEmpty()) {
            System.out.println("Invalid transaction: payment type required!");
        } else {
            System.out.println("Transaction is valid.");
        }
    }
}
