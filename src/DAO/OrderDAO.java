package DAO;

import Model.Order;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order create(Order order) throws SQLException;

    Order getOrderById(int id) throws SQLException;

    List<Order> getOrdersByUserId(int userId) throws SQLException;

    Order update(Order order) throws SQLException;

    boolean delete(int id) throws SQLException;

    Optional<Order> findById(int orderId);

    List<Order> findAll();

    List<Order> findByUserId(int userId);

    boolean updateStatus(int orderId, String status);
}



