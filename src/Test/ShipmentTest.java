package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    @BeforeEach
    void setUp() {
        Shipment.shipmentDB.clear();
    }

    @Test
    void testCreateShipmentSetsDateAndStatus() {
        Shipment shipment = new Shipment();

        shipment.createShipment();

        assertNotNull(shipment.shippedAt);
        assertEquals("Created", shipment.status);
    }

    @Test
    void testShipmentDatabaseInitiallyEmpty() {
        assertTrue(Shipment.shipmentDB.isEmpty());
    }

    @Test
    void testAddShipmentToDatabase() {
        Shipment shipment = new Shipment();
        shipment.shipmentId = 1;
        shipment.orderId = 101;

        shipment.createShipment();
        Shipment.shipmentDB.put(shipment.shipmentId, shipment);

        assertEquals(1, Shipment.shipmentDB.size());
        assertTrue(Shipment.shipmentDB.containsKey(1));
    }

    @Test
    void testShipmentFieldsAssignment() {
        Shipment shipment = new Shipment();

        shipment.shipmentId = 5;
        shipment.orderId = 200;
        shipment.trackingNumber = "TRK123456";
        shipment.courier = "DHL";

        assertEquals(5, shipment.shipmentId);
        assertEquals(200, shipment.orderId);
        assertEquals("TRK123456", shipment.trackingNumber);
        assertEquals("DHL", shipment.courier);
    }

    @Test
    void testCreateShipmentStatusOnlyChangesRelevantFields() {
        Shipment shipment = new Shipment();
        shipment.trackingNumber = "ABC999";

        shipment.createShipment();

        assertEquals("ABC999", shipment.trackingNumber);
        assertEquals("Created", shipment.status);
        assertNotNull(shipment.shippedAt);
    }
}
