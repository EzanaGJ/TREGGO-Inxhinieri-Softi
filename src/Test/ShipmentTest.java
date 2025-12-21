package Test;

import Model.Shipment;
import org.junit.jupiter.api.Test;

public class ShipmentTest {

    @Test
    public void testShipmentWorkflow() {
        Shipment s1 = new Shipment();
        s1.shipmentId = 1;
        s1.orderId = 101;
        s1.courier = "DHL";
        s1.createShipment();

        Shipment s2 = new Shipment();
        s2.shipmentId = 2;
        s2.orderId = 102;
        s2.courier = "FedEx";
        s2.createShipment();

        s1.updateDetails("UPS");

        Shipment.shipmentDB.values().forEach(shipment -> {
            System.out.println("Shipment ID: " + shipment.shipmentId);
            System.out.println("Order ID: " + shipment.orderId);
            System.out.println("Courier: " + shipment.courier);
            System.out.println("Shipped At: " + shipment.shippedAt);
            System.out.println("Tracking Number: " + shipment.trackingNumber);
            System.out.println("Status: " + shipment.status);
            System.out.println("--------------------------");
        }
        );

        s2.getTrackingInfo();

        s1.deleteShipment();
        System.out.println("Shipment 1 deleted. Remaining shipments:");
        Shipment.shipmentDB.values().forEach(shipment ->
                System.out.println("Shipment ID: " + shipment.shipmentId)
        );
    }
}
