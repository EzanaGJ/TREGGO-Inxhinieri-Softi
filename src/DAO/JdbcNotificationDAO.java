package DAO;

import Model.Notification;
import db.DatabaseManager;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

public class JdbcNotificationDAO implements NotificationDAO {

    @Override
    public Notification create(Notification notification) throws SQLException {
        String sql = "INSERT INTO notifications (user_id, type, message) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getType());
            ps.setString(3, notification.getMessageText());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Notification(
                            rs.getInt(1),
                            notification.getUserId(),
                            notification.getType(),
                            notification.getMessageText(),
                            new Date(),
                            false
                    );
                }
            }
        }
        return null;
    }

    @Override
    public Notification getById(int id) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE notification_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Notification(
                            rs.getInt("notification_id"),
                            rs.getInt("user_id"),
                            rs.getString("type"),
                            rs.getString("message"),
                            rs.getTimestamp("created_at"),
                            rs.getBoolean("read"));
                }
            }
        }
        return null;

    }
    @Override
    public List<Notification> getNotificationforUser(int userId) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
        List<Notification> list = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Notification(
                            rs.getInt("notification_id"),
                            rs.getInt("user_id"),
                            rs.getString("type"),
                            rs.getString("message"),
                            rs.getTimestamp("created_at"),
                            rs.getBoolean("read")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public boolean markAsRead(int id) throws SQLException {
        String sql = "UPDATE notifications SET `read` = TRUE WHERE notification_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

}
