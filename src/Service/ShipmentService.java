package Service;

import DAO.ShipmentDAO;
import Model.Shipment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShipmentService {

    private final ShipmentDAO shipmentDAO;
    private final List<String> actions = new ArrayList<>();

    public ShipmentService(ShipmentDAO shipmentDAO) {
        this.shipmentDAO = shipmentDAO;
    }

    public Shipment createShipment(int orderId, int addressId,
                                   String tracking, String service) throws SQLException {

        if (orderId <= 0 || addressId <= 0)
            throw new IllegalArgumentException("IDs must be positive");

        Shipment shipment = new Shipment(
                orderId,
                addressId,
                tracking,
                service,
                "created"
        );

        shipmentDAO.create(shipment);
        actions.add("CREATE_SHIPMENT:" + shipment.getShipmentId());
        return shipment;
    }

    public Shipment getShipmentById(int id) throws SQLException {
        actions.add("GET_SHIPMENT:" + id);
        return shipmentDAO.getById(id);
    }

    public void updateStatus(Shipment shipment, String status) throws SQLException {
        if (shipment == null)
            throw new IllegalArgumentException("Shipment cannot be null");

        shipmentDAO.updateStatus(shipment.getShipmentId(), status);
        actions.add("UPDATE_STATUS:" + shipment.getShipmentId() + ":" + status);
    }

    public void assignDeliveryService(Shipment shipment, String service) {
        if (shipment == null)
            throw new IllegalArgumentException("Shipment cannot be null");

        shipment.setDeliveryService(service);
        actions.add("ASSIGN_SERVICE:" + shipment.getShipmentId() + ":" + service);
    }

    public List<String> getActions() {
        return List.copyOf(actions);
    }
}
