package DAO;

import Model.Shipment;
import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcShipmentDAO implements ShipmentDAO {

    @Override
    public Shipment create(Shipment shipment) throws SQLException {
        String sql = "INSERT INTO shipment (order_id, address_id, tracking_number, delivery_service, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, shipment.getOrderId());
            ps.setInt(2, shipment.getAddressId());
            ps.setString(3, shipment.getTrackingNumber());
            ps.setString(4, shipment.getDeliveryService());
            ps.setString(5, shipment.getStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    shipment.setShipmentId(rs.getInt(1));
                }
            }
        }

        return shipment;
    }

    @Override
    public Shipment getShipmentById(int id) throws SQLException {
        String sql = "SELECT * FROM shipment WHERE shipment_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Shipment(
                            rs.getInt("shipment_id"),
                            rs.getInt("order_id"),
                            rs.getInt("address_id"),
                            rs.getString("tracking_number"),
                            rs.getString("delivery_service"),
                            rs.getString("status")
                    );
                }
            }
        }

        return null;
    }

    @Override
    public List<Shipment> getShipmentsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM shipment WHERE order_id = ?";
        List<Shipment> shipments = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    shipments.add(new Shipment(
                            rs.getInt("shipment_id"),
                            rs.getInt("order_id"),
                            rs.getInt("address_id"),
                            rs.getString("tracking_number"),
                            rs.getString("delivery_service"),
                            rs.getString("status")
                    ));
                }
            }
        }

        return shipments;
    }

    @Override
    public Shipment update(Shipment shipment) throws SQLException {
        String sql = "UPDATE shipment SET order_id = ?, address_id = ?, tracking_number = ?, " +
                "delivery_service = ?, status = ? WHERE shipment_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shipment.getOrderId());
            ps.setInt(2, shipment.getAddressId());
            ps.setString(3, shipment.getTrackingNumber());
            ps.setString(4, shipment.getDeliveryService());
            ps.setString(5, shipment.getStatus());
            ps.setInt(6, shipment.getShipmentId());

            ps.executeUpdate();
        }

        return shipment;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM shipment WHERE shipment_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}




