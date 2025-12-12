package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Shipment {
    int shipmentId;
    int orderId;
    String trackingNumber;
    String courier;
    Date shippedAt;
    String status;

    static Map<Integer, Shipment> shipmentDB = new HashMap<>();

    void createShipment() {
        this.shippedAt = new Date();
        this.status = "Created";
    }
}