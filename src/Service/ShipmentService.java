package Service;

import Model.Shipment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShipmentService {

    private List<Shipment> shipments;
    private int nextId;

    public ShipmentService() {
        this.shipments = new ArrayList<>();
        this.nextId = 1; // ID e parë
    }

    // Krijo një shipment të ri
    public Shipment createShipment(int orderId, int addressId, String trackingNumber,
                                   String deliveryService, String status) {
        Shipment shipment = new Shipment(nextId, orderId, addressId, trackingNumber, deliveryService, status);
        shipments.add(shipment);
        nextId++; // rrit ID për shipment-in tjetër
        return shipment;
    }

    // Merr të gjithë shipment-et
    public List<Shipment> getAllShipments() {
        return new ArrayList<>(shipments); // kthe një kopje për siguri
    }

    // Merr shipment sipas ID-së
    public Shipment getShipmentById(int shipmentId) {
        Optional<Shipment> shipment = shipments.stream()
                .filter(s -> s.getShipmentId() == shipmentId)
                .findFirst();
        return shipment.orElse(null);
    }

    // Përditëso statusin e një shipment-i
    public boolean updateShipmentStatus(int shipmentId, String newStatus) {
        Shipment shipment = getShipmentById(shipmentId);
        if (shipment != null) {
            shipment.setStatus(newStatus);
            return true;
        }
        return false;
    }

    // Fshi një shipment
    public boolean deleteShipment(int shipmentId) {
        return shipments.removeIf(s -> s.getShipmentId() == shipmentId);
    }
}
