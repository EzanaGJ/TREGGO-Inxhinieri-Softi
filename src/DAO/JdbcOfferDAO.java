package DAO;

import Model.Offer;
import db.DatabaseManager;

import java.sql.*;

public class JdbcOfferDAO implements OfferDAO {

    @Override
    public Offer create(Offer offer) throws SQLException {
        if (offer == null) throw new IllegalArgumentException("Offer cannot be null");

        String sql = "INSERT INTO offer (user_id, item_id, proposed_price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, offer.getUserId());
            ps.setInt(2, offer.getItemId());
            ps.setDouble(3, offer.getOfferedPrice());
            ps.setString(4, offer.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                offer.setOfferId(rs.getInt(1));
            }
            return offer;
        }
    }

    @Override
    public Offer getOfferById(int id) throws SQLException {
        String sql = "SELECT * FROM offer WHERE offer_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return new Offer(
                    rs.getInt("offer_id"),
                    rs.getInt("user_id"),
                    rs.getInt("item_id"),
                    rs.getDouble("proposed_price"),
                    rs.getString("status")
            );
        }
    }

    @Override
    public Offer update(Offer offer) throws SQLException {
        String sql = "UPDATE offer SET proposed_price=?, status=? WHERE offer_id=?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, offer.getOfferedPrice());
            ps.setString(2, offer.getStatus());
            ps.setInt(3, offer.getOfferId());

            ps.executeUpdate();
            return offer;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM offer WHERE offer_id=?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
