package Service;

import DAO.TransactionDAO;
import Model.Transaction;

import java.sql.SQLException;

public class TransactionService {

    private final TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    // ---------------- CREATE ----------------
    public Transaction createTransaction(Transaction transaction) throws Exception {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return transactionDAO.create(transaction);
    }

    // ---------------- GET ----------------
    public Transaction getTransactionById(int id) {
        if (id <= 0) return null;
        try {
            return transactionDAO.getTransactionById(id);
        } catch (SQLException e) {
            return null; // id non-existent
        }
    }

    // ---------------- UPDATE ----------------
    public Transaction updateTransaction(Transaction transaction) throws Exception {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return transactionDAO.update(transaction);
    }

    // ---------------- DELETE ----------------
    public void deleteTransaction(int id) {
        if (id <= 0) return;
        try {
            transactionDAO.delete(id);
        } catch (SQLException e) {
            // mos hedh exception për id jo-ekzistuese
        }
    }
}
