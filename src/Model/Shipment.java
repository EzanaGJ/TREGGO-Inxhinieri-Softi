package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Shipment {
    public int shipmentId;
    public int orderId;
    public String trackingNumber;
    public String courier;
    public Date shippedAt;
    public String status;

    public static Map<Integer, Shipment> shipmentDB = new HashMap<>();

    public void createShipment() {
        this.shippedAt = new Date();
        this.status = "Created";
        if (this.trackingNumber == null || this.trackingNumber.isEmpty()) {
            generateTrackingNumber();
        }
        shipmentDB.put(this.shipmentId, this);
    }

    public void updateDetails(String courier) {
        this.courier = courier;
        updateStatus("Updated");
    }

    public void deleteShipment() {
        shipmentDB.remove(this.shipmentId);
    }

    public void generateTrackingNumber() {
        this.trackingNumber = "TRK" + (1000 + this.shipmentId);
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void getTrackingInfo() {
        System.out.println("Tracking Number: " + this.trackingNumber);
        System.out.println("Status: " + this.status);
    }
}
