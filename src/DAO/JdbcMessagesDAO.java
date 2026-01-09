package DAO;

import Model.Messages;
import db.DatabaseManager;

import java.sql.*;

public class JdbcMessagesDAO implements MessagesDAO {

    @Override
    public Messages create(Messages m) throws SQLException {
        String sql = """
            INSERT INTO messages (sender_id, receiver_id, content, timestamp, `read`)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, m.getSenderId());
            ps.setInt(2, m.getReceiverId());
            ps.setString(3, m.getContent());
            ps.setTimestamp(4, new Timestamp(m.getTimestamp().getTime()));
            ps.setBoolean(5, m.isRead());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Messages(
                            rs.getInt(1),
                            m.getSenderId(),
                            m.getReceiverId(),
                            m.getContent(),
                            m.getTimestamp(),
                            m.isRead()
                    );
                }
            }

            throw new SQLException("Message not created");
        }
    }

    @Override
    public Messages getMessageById(int id) throws SQLException {
        String sql = "SELECT * FROM messages WHERE message_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Message not found");
                }

                return new Messages(
                        rs.getInt("message_id"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("content"),
                        rs.getTimestamp("timestamp"),
                        rs.getBoolean("read")
                );
            }
        }
    }

    @Override
    public Messages update(Messages m) throws SQLException {
        String sql = """
            UPDATE messages
            SET content = ?, `read` = ?
            WHERE message_id = ?
        """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getContent());
            ps.setBoolean(2, m.isRead());
            ps.setInt(3, m.getMessageId());

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new SQLException("Message not found for update");
            }

            return m;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM messages WHERE message_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
