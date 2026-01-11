package DAO;

import Model.Shipment;
import java.sql.SQLException;

public interface ShipmentDAO {
    Shipment create(Shipment shipment) throws SQLException;
    Shipment getShipmentById(int id) throws SQLException;
    Shipment update(Shipment shipment) throws SQLException;
    void delete(int id) throws SQLException;
}
