package DAO;

import Model.FavoriteItem;
import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcFavoriteItemDAO implements FavoriteItemDAO {

    @Override
    public FavoriteItem create(FavoriteItem favorite) throws SQLException {
        String sql = "INSERT INTO favorites (user_id, item_id) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, favorite.getUserId());
            ps.setInt(2, favorite.getItemId());
            ps.executeUpdate();
        }
        return favorite;
    }

    @Override
    public FavoriteItem getFavorite(int userId, int itemId) throws SQLException {
        String sql = "SELECT user_id, item_id, created_at FROM favorites WHERE user_id = ? AND item_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FavoriteItem(
                            rs.getInt("user_id"),
                            rs.getInt("item_id"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<FavoriteItem> getFavoritesByUser(int userId) throws SQLException {
        String sql = "SELECT user_id, item_id, created_at FROM favorites WHERE user_id = ?";
        List<FavoriteItem> favorites = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    favorites.add(new FavoriteItem(
                            rs.getInt("user_id"),
                            rs.getInt("item_id"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        }
        return favorites;
    }

    @Override
    public void delete(int userId, int itemId) throws SQLException {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND item_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ps.executeUpdate();
        }
    }
}
