package Model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ShipmentTest {

    @Test
    void testConstructorWithId() {
        Shipment shipment = new Shipment(
                1,
                100,
                200,
                "TRK123456",
                "DHL",
                "SHIPPED"
        );

        assertEquals(1, shipment.getShipmentId());
        assertEquals(100, shipment.getOrderId());
        assertEquals(200, shipment.getAddressId());
        assertEquals("TRK123456", shipment.getTrackingNumber());
        assertEquals("DHL", shipment.getDeliveryService());
        assertEquals("SHIPPED", shipment.getStatus());
    }

    @Test
    void testConstructorWithoutId() {
        Shipment shipment = new Shipment(
                100,
                200,
                "TRK654321",
                "FedEx",
                "CREATED"
        );

        assertEquals(0, shipment.getShipmentId()); // default value
        assertEquals(100, shipment.getOrderId());
        assertEquals(200, shipment.getAddressId());
        assertEquals("TRK654321", shipment.getTrackingNumber());
        assertEquals("FedEx", shipment.getDeliveryService());
        assertEquals("CREATED", shipment.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        Shipment shipment = new Shipment(
                100,
                200,
                "TRK000000",
                "UPS",
                "CREATED"
        );

        shipment.setShipmentId(5);
        shipment.setOrderId(150);
        shipment.setAddressId(250);
        shipment.setTrackingNumber("TRK999999");
        shipment.setDeliveryService("Posta");
        shipment.setStatus("DELIVERED");

        assertEquals(5, shipment.getShipmentId());
        assertEquals(150, shipment.getOrderId());
        assertEquals(250, shipment.getAddressId());
        assertEquals("TRK999999", shipment.getTrackingNumber());
        assertEquals("Posta", shipment.getDeliveryService());
        assertEquals("DELIVERED", shipment.getStatus());
    }
}

