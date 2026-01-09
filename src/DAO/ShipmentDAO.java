package DAO;

import Model.Shipment;

import java.sql.SQLException;
import java.util.List;

public interface ShipmentDAO {

    Shipment create(Shipment shipment) throws SQLException;

    Shipment getById(int id) throws SQLException;

    List<Shipment> findAll() throws SQLException;

    Shipment update(Shipment shipment) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean updateStatus(int shipmentId, String status) throws SQLException;
}
