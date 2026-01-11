package DAO;

import Model.Order;
import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderDAO implements OrderDAO {

    @Override
    public Order create(Order order) throws SQLException {
        String sql = "INSERT INTO `order` (user_id, address_id, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getAddressId());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    order.setOrderId(rs.getInt(1));
                }
            }
        }

        return order;
    }

    @Override
    public Order getOrderById(int id) throws SQLException {
        String sql = "SELECT order_id, user_id, address_id, status, created_at FROM `order` WHERE order_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("order_id"),
                            rs.getInt("user_id"),
                            rs.getInt("address_id"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        }

        return null;
    }

    @Override
    public List<Order> findByUserId(int userId) throws SQLException {
        String sql = "SELECT order_id, user_id, address_id, status, created_at FROM `order` WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(new Order(
                            rs.getInt("order_id"),
                            rs.getInt("user_id"),
                            rs.getInt("address_id"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        }

        return orders;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        String sql = "SELECT order_id, user_id, address_id, status, created_at FROM `order`";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getInt("address_id"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }
        }

        return orders;
    }

    @Override
    public Order update(Order order) throws SQLException {
        String sql = "UPDATE `order` SET user_id = ?, address_id = ?, status = ? WHERE order_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getAddressId());
            ps.setString(3, order.getStatus());
            ps.setInt(4, order.getOrderId());
            ps.executeUpdate();
        }

        return order;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `order` WHERE order_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    @Override
    public boolean updateStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE `order` SET status = ? WHERE order_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }
}
