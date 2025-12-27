package DAO;

import Model.Transaction;
import db.DatabaseManager;

import java.sql.*;

public class JdbcTransactionDAO implements TransactionDAO {

    @Override
    public Transaction create(Transaction t) throws SQLException {
        String sql = """
            INSERT INTO transaction (payment_id, amount, type)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, t.getPaymentId());
            ps.setDouble(2, t.getAmount());
            ps.setString(3, t.getPaymentType());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setTransactionId(rs.getInt(1));
            }
            return t;
        }
    }

    @Override
    public Transaction getTransactionById(int id) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE transaction_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Transaction not found");
            }

            Transaction t = new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getDouble("amount"),
                    rs.getString("type")
            );
            t.setPaymentId(rs.getInt("payment_id"));
            return t;
        }
    }

    @Override
    public Transaction update(Transaction t) throws SQLException {
        String sql = """
            UPDATE transaction
            SET amount = ?, type = ?
            WHERE transaction_id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, t.getAmount());
            ps.setString(2, t.getPaymentType());
            ps.setInt(3, t.getTransactionId());

            ps.executeUpdate();
            return t;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM transaction WHERE transaction_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
