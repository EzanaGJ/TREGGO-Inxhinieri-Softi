package Test;

import Model.Shipment;
import Service.ShipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentServiceTest {

    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        shipmentService = new ShipmentService();
    }

    @Test
    void testCreateShipment() {
        Shipment shipment = shipmentService.createShipment(101, 201, "TRACK123", "DHL", "Pending");
        assertNotNull(shipment);
        assertEquals(101, shipment.getOrderId());
        assertEquals("TRACK123", shipment.getTrackingNumber());
        assertEquals("Pending", shipment.getStatus());
    }

    @Test
    void testGetAllShipments() {
        shipmentService.createShipment(101, 201, "TRACK123", "DHL", "Pending");
        shipmentService.createShipment(102, 202, "TRACK456", "FedEx", "Shipped");

        List<Shipment> shipments = shipmentService.getAllShipments();
        assertEquals(2, shipments.size());
    }

    @Test
    void testGetShipmentById() {
        Shipment shipment1 = shipmentService.createShipment(101, 201, "TRACK123", "DHL", "Pending");
        Shipment shipment2 = shipmentService.createShipment(102, 202, "TRACK456", "FedEx", "Shipped");

        Shipment found = shipmentService.getShipmentById(shipment1.getShipmentId());
        assertNotNull(found);
        assertEquals("TRACK123", found.getTrackingNumber());

        Shipment notFound = shipmentService.getShipmentById(999);
        assertNull(notFound);
    }

    @Test
    void testUpdateShipmentStatus() {
        Shipment shipment = shipmentService.createShipment(101, 201, "TRACK123", "DHL", "Pending");

        boolean updated = shipmentService.updateShipmentStatus(shipment.getShipmentId(), "Delivered");
        assertTrue(updated);
        assertEquals("Delivered", shipmentService.getShipmentById(shipment.getShipmentId()).getStatus());

        // Test për shipment që nuk ekziston
        boolean updateFail = shipmentService.updateShipmentStatus(999, "Lost");
        assertFalse(updateFail);
    }

    @Test
    void testDeleteShipment() {
        Shipment shipment = shipmentService.createShipment(101, 201, "TRACK123", "DHL", "Pending");

        boolean deleted = shipmentService.deleteShipment(shipment.getShipmentId());
        assertTrue(deleted);
        assertNull(shipmentService.getShipmentById(shipment.getShipmentId()));

        // Test për shipment që nuk ekziston
        boolean deleteFail = shipmentService.deleteShipment(999);
        assertFalse(deleteFail);
    }
}
