package Test;

import DAO.JdbcShipmentDAO;
import DAO.ShipmentDAO;
import Model.Shipment;
import Service.ShipmentService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ShipmentServiceTest {

    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        ShipmentDAO shipmentDAO = new JdbcShipmentDAO();
        shipmentService = new ShipmentService(shipmentDAO);
    }

    @Test
    void testCreateShipment() throws SQLException {
        Shipment shipment = shipmentService.createShipment(
                1, 1, "TRACK123", "DHL");

        assertTrue(shipment.getShipmentId() > 0);
    }

    @Test
    void testGetShipmentById() throws SQLException {
        Shipment shipment = shipmentService.createShipment(
                1, 1, "TRACK456", "UPS");

        Shipment fetched = shipmentService.getShipmentById(shipment.getShipmentId());

        assertNotNull(fetched);
        assertEquals("TRACK456", fetched.getTrackingNumber());
    }

    @Test
    void testUpdateStatus() throws SQLException {
        Shipment shipment = shipmentService.createShipment(
                1, 1, "TRACK789", "FedEx");

        shipmentService.updateStatus(shipment, "shipped");

        boolean found = shipmentService.getActions()
                .contains("UPDATE_STATUS:" + shipment.getShipmentId() + ":shipped");

        assertTrue(found);
    }

    @Test
    void testAssignDeliveryService() throws SQLException {
        Shipment shipment = shipmentService.createShipment(
                1, 1, "TRACK999", "Post");

        shipmentService.assignDeliveryService(shipment, "DHL");

        assertEquals("DHL", shipment.getDeliveryService());
    }
}
