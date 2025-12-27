package DAO;

import Model.Transaction;
import java.sql.SQLException;

public interface TransactionDAO {

    Transaction create(Transaction transaction) throws SQLException;

    Transaction getTransactionById(int id) throws SQLException;

    Transaction update(Transaction transaction) throws SQLException;

    void delete(int id) throws SQLException;
}
