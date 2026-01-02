package DAO;

import Model.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

    Order create(Order order) throws SQLException;

    Order getOrderById(int id) throws SQLException;

    List<Order> getOrdersByUserId(int userId) throws SQLException;

    Order update(Order order) throws SQLException;

    void delete(int id) throws SQLException;
}



