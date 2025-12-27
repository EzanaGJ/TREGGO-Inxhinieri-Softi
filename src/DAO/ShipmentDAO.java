package DAO;

import Model.Shipment;
import java.sql.SQLException;
import java.util.List;

public interface ShipmentDAO {

    Shipment create(Shipment shipment) throws SQLException;

    Shipment getShipmentById(int id) throws SQLException;

    List<Shipment> getShipmentsByOrderId(int orderId) throws SQLException;

    Shipment update(Shipment shipment) throws SQLException;

    void delete(int id) throws SQLException;
}
