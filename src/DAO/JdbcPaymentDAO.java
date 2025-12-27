package DAO;

import Model.Payment;
import Model.Enum.PaymentStatus;
import db.DatabaseManager;

import java.sql.*;

public class JdbcPaymentDAO implements PaymentDAO {

    @Override
    public Payment create(Payment p) throws SQLException {
        String sql = """
            INSERT INTO payment (order_id, method, amount, status)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getOrderId());
            ps.setString(2, p.getMethod());
            ps.setDouble(3, p.getAmount());
            ps.setString(4, p.getPaymentStatus().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setPaymentId(rs.getInt(1));
            }

            return p;
        }
    }

    @Override
    public Payment getPaymentById(int id) throws SQLException {
        String sql = "SELECT * FROM payment WHERE payment_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Payment p = new Payment(
                    rs.getInt("order_id"),
                    rs.getString("method"),
                    rs.getDouble("amount")
            );
            p.setPaymentId(rs.getInt("payment_id"));
            p.setPaymentStatus(PaymentStatus.valueOf(rs.getString("status")));

            return p;
        }
    }

    @Override
    public Payment update(Payment p) throws SQLException {
        String sql = """
            UPDATE payment
            SET method = ?, amount = ?, status = ?
            WHERE payment_id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getMethod());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getPaymentStatus().name());
            ps.setInt(4, p.getPaymentId());

            ps.executeUpdate();
            return p;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
