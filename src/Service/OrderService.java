package Service;

import DAO.OrderDAO;
import Model.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDao;
    private final List<String> actions = new ArrayList<>();

    public OrderService(OrderDAO orderDao) {
        this.orderDao = orderDao;
    }

    public Order createOrder(int userId, int addressId) throws SQLException {
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if (addressId <= 0) throw new IllegalArgumentException("Address ID must be positive");

        Order order = new Order(userId, addressId, "pending");
        orderDao.create(order);

        actions.add("CREATE_ORDER:" + order.getOrderId() + ":USER:" + userId);
        return order;
    }

    public double calculateTotal(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");

        double total = Math.random() * 100; // placeholder calculation
        actions.add("CALCULATE_TOTAL:" + order.getOrderId() + ":" + total);
        return total;
    }

    public String getAddress(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");

        String address = "AddressID:" + order.getAddressId(); // placeholder
        actions.add("GET_ADDRESS:" + order.getOrderId() + ":" + address);
        return address;
    }

    public String getTrackingNo(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");

        String tracking = "TRACK" + order.getOrderId();
        actions.add("GET_TRACKING_NO:" + order.getOrderId() + ":" + tracking);
        return tracking;
    }

    public void updateStatus(Order order, String status) throws SQLException {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        if (status == null || status.isBlank()) throw new IllegalArgumentException("Status cannot be empty");

        orderDao.updateStatus(order.getOrderId(), status);
        actions.add("UPDATE_STATUS:" + order.getOrderId() + ":" + status);
    }

    public void assignBuyer(Order order, int buyerId) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        if (buyerId <= 0) throw new IllegalArgumentException("Buyer ID must be positive");

        order.setUserId(buyerId);
        actions.add("ASSIGN_BUYER:" + order.getOrderId() + ":" + buyerId);
    }

    public void assignSeller(Order order, int sellerId) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        if (sellerId <= 0) throw new IllegalArgumentException("Seller ID must be positive");

        // placeholder: just log action
        actions.add("ASSIGN_SELLER:" + order.getOrderId() + ":" + sellerId);
    }

    public Order getOrderById(int orderId) throws SQLException {
        return orderDao.getOrderById(orderId);
    }

    public List<String> getActions() {
        return List.copyOf(actions);
    }
}
