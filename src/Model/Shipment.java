package Model;

public class Shipment {

    private int shipmentId;
    private int orderId;
    private int addressId;
    private String trackingNumber;
    private String deliveryService;
    private String status;

    // Constructor me ID (kur merret nga DB)
    public Shipment(int shipmentId, int orderId, int addressId,
                    String trackingNumber, String deliveryService, String status) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.addressId = addressId;
        this.trackingNumber = trackingNumber;
        this.deliveryService = deliveryService;
        this.status = status;
    }

    // Constructor pa ID (kur krijohet e re)
    public Shipment(int orderId, int addressId,
                    String trackingNumber, String deliveryService, String status) {
        this.orderId = orderId;
        this.addressId = addressId;
        this.trackingNumber = trackingNumber;
        this.deliveryService = deliveryService;
        this.status = status;
    }

    // Getters
    public int getShipmentId() {
        return shipmentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getDeliveryService() {
        return deliveryService;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void setDeliveryService(String deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}