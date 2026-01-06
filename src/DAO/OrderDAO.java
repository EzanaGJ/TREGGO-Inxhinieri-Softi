package DAO;

import Model.Order;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order create(Order order) throws SQLException;

    Order getOrderById(int id) throws SQLException;

    Order update(Order order) throws SQLException;

    boolean delete(int id) throws SQLException;

    List<Order> findAll() throws SQLException;

    List<Order> findByUserId(int userId) throws SQLException;

    boolean updateStatus(int orderId, String status) throws SQLException;
}



