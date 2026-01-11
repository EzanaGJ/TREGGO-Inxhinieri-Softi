package DAO;

import Model.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private final List<Transaction> storage = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public Transaction create(Transaction transaction) throws SQLException {
        transaction.setTransactionId(idCounter++);
        storage.add(transaction);
        return transaction;
    }

    @Override
    public Transaction getTransactionById(int id) throws SQLException {
        return storage.stream()
                .filter(t -> t.getTransactionId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Transaction update(Transaction transaction) throws SQLException {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getTransactionId() == transaction.getTransactionId()) {
                storage.set(i, transaction);
                return transaction;
            }
        }
        throw new SQLException("Transaction not found");
    }

    @Override
    public void delete(int id) throws SQLException {
        storage.removeIf(t -> t.getTransactionId() == id);

    }
}
