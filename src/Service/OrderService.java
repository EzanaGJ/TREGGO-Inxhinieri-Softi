package Service;

import DAO.OrderDAO;
import Model.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    // Krijon porosi të re
    public void createOrder(Order order) throws SQLException {
        orderDAO.create(order);
    }

    // Merr porosi sipas ID
    public Optional<Order> getOrderById(int orderId) {
        return orderDAO.findById(orderId);
    }

    // Merr të gjitha porositë
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    // Merr porositë e një user-i
    public List<Order> getOrdersByUser(int userId) {
        return orderDAO.findByUserId(userId);
    }

    // Përditëson statusin e porosisë
    public boolean updateOrderStatus(int orderId, String status) {
        return orderDAO.updateStatus(orderId, status);
    }

    // Fshin porosi
    public boolean deleteOrder(int orderId) throws SQLException {
        return orderDAO.delete(orderId);
    }
}
