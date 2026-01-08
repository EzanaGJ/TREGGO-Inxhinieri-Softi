package DAO;

import Model.User;
import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUserDAO implements UserDAO {

    @Override
    public User create(User user) throws SQLException {
        String sql = "INSERT INTO user (name, password, role_type, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRoleType());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
        }

        return user;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT user_id, name, password, role_type, email FROM user WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("role_type"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }

    @Override
    public User update(User user) throws SQLException {
        String sql = "UPDATE user SET name = ?, password = ?, role_type = ?, email = ? WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRoleType());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getUserId());
            ps.executeUpdate();
        }

        return user;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    @Override
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT user_id, name, password, role_type, email FROM user WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("role_type"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }

}
