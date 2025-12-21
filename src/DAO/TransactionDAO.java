package DAO;

import Model.Transaction;

import java.util.HashMap;
import java.util.Map;

public class TransactionDAO {
    private static final Map<Integer, Transaction> transactionDB = new HashMap<>();

    public void createTransaction(Transaction transaction) {
        transactionDB.put(transaction.getTransactionId(), transaction);
    }

    public Transaction getTransaction(int transactionId) {
        return transactionDB.get(transactionId);
    }

    public void updateTransaction(Transaction transaction) {
        transactionDB.put(transaction.getTransactionId(), transaction);
    }

    public void deleteTransaction(int transactionId) {
        transactionDB.remove(transactionId);
    }

    public void clearDatabase() {
        transactionDB.clear();
    }
}
