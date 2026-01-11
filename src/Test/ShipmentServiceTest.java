package Test;

import DAO.JdbcShipmentDAO;
import Model.Shipment;
import Service.ShipmentService;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentServiceTest {

    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() throws SQLException {
        shipmentService = new ShipmentService(new JdbcShipmentDAO());

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            // Insert dummy user (needed for order and address FK)
            stmt.executeUpdate("INSERT IGNORE INTO user (user_id, name, password, email, role_type) VALUES (9999,'Test User','pass','test_user@example.com','USER')");

            // Insert dummy address
            stmt.executeUpdate("INSERT IGNORE INTO addresses (address_id, user_id, city, street, postal_code, country) VALUES (9999,9999,'TestCity','TestStreet','12345','TestCountry')");

            // Insert dummy order
            stmt.executeUpdate("INSERT IGNORE INTO `order` (order_id, user_id, address_id, status) VALUES (9999,9999,9999,'pending')");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            // Clean up shipments
            stmt.executeUpdate("DELETE FROM shipment WHERE tracking_number LIKE 'TEST_%'");

            // Clean up dummy order, address, and user
            stmt.executeUpdate("DELETE FROM `order` WHERE order_id=9999");
            stmt.executeUpdate("DELETE FROM addresses WHERE address_id=9999");
            stmt.executeUpdate("DELETE FROM user WHERE user_id=9999");
        }
    }

    @Test
    void testCreateAndFindShipment() throws SQLException {
        Shipment s = shipmentService.createShipment(
                9999, // order_id
                9999, // address_id
                "TEST_TRK123",
                "DHL",
                "pending"
        );

        assertNotEquals(0, s.getShipmentId());

        Shipment found = shipmentService.getShipmentById(s.getShipmentId());
        assertNotNull(found);
        assertEquals("TEST_TRK123", found.getTrackingNumber());
        assertEquals("pending", found.getStatus());
    }

    @Test
    void testUpdateShipment() throws SQLException {
        Shipment s = shipmentService.createShipment(9999, 9999, "TEST_TRK456", "UPS", "pending");

        shipmentService.updateShipment(s.getShipmentId(), "TEST_TRK789", "FedEx", "in_transit");

        Shipment updated = shipmentService.getShipmentById(s.getShipmentId());
        assertEquals("TEST_TRK789", updated.getTrackingNumber());
        assertEquals("FedEx", updated.getDeliveryService());
        assertEquals("in_transit", updated.getStatus());
    }

    @Test
    void testDeleteShipment() throws SQLException {
        Shipment s = shipmentService.createShipment(9999, 9999, "TEST_TRK999", "DHL", "pending");

        shipmentService.deleteShipment(s.getShipmentId());

        Shipment found = shipmentService.getShipmentById(s.getShipmentId());
        assertNull(found);
    }
}
