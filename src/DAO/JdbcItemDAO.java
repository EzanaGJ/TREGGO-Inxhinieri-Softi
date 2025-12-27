package DAO;

import Model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcItemDAO implements ItemDAO {

    private final Connection connection;

    public JdbcItemDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addItem(Item item) {
        String sql = "INSERT INTO item (user_id, category_id, brand_id, title, description, price, size, `condition`, image, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, item.getUserId());
            if (item.getCategoryId() != null) ps.setInt(2, item.getCategoryId()); else ps.setNull(2, Types.INTEGER);
            if (item.getBrandId() != null) ps.setInt(3, item.getBrandId()); else ps.setNull(3, Types.INTEGER);
            ps.setString(4, item.getTitle());
            ps.setString(5, item.getDescription());
            ps.setBigDecimal(6, item.getPrice());
            ps.setString(7, item.getSize());
            ps.setString(8, item.getCondition());
            ps.setString(9, item.getImage());
            ps.setString(10, item.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) item.setItemId(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Optional<Item> getItemById(int itemId) {
        String sql = "SELECT * FROM item WHERE item_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRowToItem(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) items.add(mapRowToItem(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    @Override
    public boolean updateItem(Item item) {
        String sql = "UPDATE item SET user_id=?, category_id=?, brand_id=?, title=?, description=?, price=?, size=?, `condition`=?, image=?, status=? WHERE item_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, item.getUserId());
            if (item.getCategoryId() != null) ps.setInt(2, item.getCategoryId()); else ps.setNull(2, Types.INTEGER);
            if (item.getBrandId() != null) ps.setInt(3, item.getBrandId()); else ps.setNull(3, Types.INTEGER);
            ps.setString(4, item.getTitle());
            ps.setString(5, item.getDescription());
            ps.setBigDecimal(6, item.getPrice());
            ps.setString(7, item.getSize());
            ps.setString(8, item.getCondition());
            ps.setString(9, item.getImage());
            ps.setString(10, item.getStatus());
            ps.setInt(11, item.getItemId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM item WHERE item_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean markAsSold(int itemId) {
        String sql = "UPDATE item SET status='sold' WHERE item_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public List<Item> getItemsByUserId(int userId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE user_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) items.add(mapRowToItem(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    @Override
    public List<Item> getItemsByStatus(String status) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE status=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) items.add(mapRowToItem(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    private Item mapRowToItem(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("item_id"),
                rs.getInt("user_id"),
                rs.getObject("category_id") != null ? rs.getInt("category_id") : null,
                rs.getObject("brand_id") != null ? rs.getInt("brand_id") : null,
                rs.getString("title"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getString("size"),
                rs.getString("condition"),
                rs.getString("image"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
        );
    }
}
