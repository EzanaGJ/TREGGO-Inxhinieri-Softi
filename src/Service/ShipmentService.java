package Service;

import DAO.ShipmentDAO;
import Model.Shipment;

import java.sql.SQLException;

public class ShipmentService {

    private final ShipmentDAO shipmentDao;

    public ShipmentService(ShipmentDAO shipmentDao) {
        this.shipmentDao = shipmentDao;
    }

    public Shipment createShipment(int orderId, int addressId, String trackingNumber, String deliveryService, String status) throws SQLException {
        if (orderId <= 0) throw new IllegalArgumentException("Order ID must be valid");
        if (addressId <= 0) throw new IllegalArgumentException("Address ID must be valid");
        if (status == null || status.isBlank()) throw new IllegalArgumentException("Status cannot be empty");

        Shipment shipment = new Shipment(orderId, addressId, trackingNumber, deliveryService, status);
        shipmentDao.create(shipment);
        return shipment;
    }

    public Shipment getShipmentById(int id) throws SQLException {
        return shipmentDao.getShipmentById(id);
    }

    public void updateShipment(int id, String trackingNumber, String deliveryService, String status) throws SQLException {
        Shipment shipment = shipmentDao.getShipmentById(id);
        if (shipment != null) {
            if (trackingNumber != null && !trackingNumber.isBlank()) shipment.setTrackingNumber(trackingNumber);
            if (deliveryService != null && !deliveryService.isBlank()) shipment.setDeliveryService(deliveryService);
            if (status != null && !status.isBlank()) shipment.setStatus(status);

            shipmentDao.update(shipment);
        }
    }

    public void deleteShipment(int id) throws SQLException {
        shipmentDao.delete(id);
    }
}
