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
    }
}