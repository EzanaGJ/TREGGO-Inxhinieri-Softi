package Test;

import DAO.JdbcOrderDAO;
import DAO.OrderDAO;
import Model.Order;
import Service.OrderService;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {

    private static Connection connection;
    private static OrderService orderService;

    private static int testUserId;
    private static int testAddressId;

    @BeforeAll
    static void setup() throws Exception {
        connection = DatabaseManager.getConnection();
        OrderDAO orderDAO = new JdbcOrderDAO(connection) {
            @Override
            public Optional<Order> findById(int orderId) {
                return Optional.empty();
            }

            @Override
            public List<Order> findAll() {
                return List.of();
            }

            @Override
            public List<Order> findByUserId(int userId) {
                return List.of();
            }

            @Override
            public boolean updateStatus(int orderId, String status) {
                return false;
            }
        };
        orderService = new OrderService(orderDAO);

        // Insert test user
        String insertUser = "INSERT INTO user (name, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertUser, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Order Test User");
            ps.setString(2, "password123");
            ps.setString(3, "order@test.com");
            ps.executeUpdate();
            var rs = ps.getGeneratedKeys();
            if (rs.next()) testUserId = rs.getInt(1);
        }

        // Insert test address
        String insertAddress = "INSERT INTO address (user_id, city, street) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertAddress, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, testUserId);
            ps.setString(2, "Test City");
            ps.setString(3, "Test Street");
            ps.executeUpdate();
            var rs = ps.getGeneratedKeys();
            if (rs.next()) testAddressId = rs.getInt(1);
        }
    }

    @AfterAll
    static void teardown() throws Exception {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM address WHERE address_id=?")) {
            ps.setInt(1, testAddressId);
            ps.executeUpdate();
        }

        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE user_id=?")) {
            ps.setInt(1, testUserId);
            ps.executeUpdate();
        }

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private Order createTestOrder() {
        return new Order(testUserId, testAddressId, "pending");
    }

    @Test
    void testCreateOrder() {
        Order order = createTestOrder();
        orderService.createOrder(order);

        assertTrue(order.getOrderId() > 0, "Order ID should be generated");
    }

    @Test
    void testGetOrderById() {
        Order order = createTestOrder();
        orderService.createOrder(order);

        Optional<Order> retrieved = orderService.getOrderById(order.getOrderId());
        assertTrue(retrieved.isPresent());
        assertEquals("pending", retrieved.get().getStatus());
    }

    @Test
    void testGetAllOrders() {
        Order order1 = createTestOrder();
        Order order2 = createTestOrder();

        orderService.createOrder(order1);
        orderService.createOrder(order2);

        List<Order> orders = orderService.getAllOrders();
        assertTrue(orders.size() >= 2);
    }

    @Test
    void testGetOrdersByUser() {
        Order order = createTestOrder();
        orderService.createOrder(order);

        List<Order> orders = orderService.getOrdersByUser(testUserId);
        assertTrue(orders.stream().anyMatch(o -> o.getOrderId() == order.getOrderId()));
    }

    @Test
    void testUpdateOrderStatus() {
        Order order = createTestOrder();
        orderService.createOrder(order);

        boolean updated = orderService.updateOrderStatus(order.getOrderId(), "completed");
        assertTrue(updated);

        Optional<Order> retrieved = orderService.getOrderById(order.getOrderId());
        assertTrue(retrieved.isPresent());
        assertEquals("completed", retrieved.get().getStatus());
    }

    @Test
    void testDeleteOrder() {
        Order order = createTestOrder();
        orderService.createOrder(order);

        boolean deleted = orderService.deleteOrder(order.getOrderId());
        assertTrue(deleted);

        Optional<Order> retrieved = orderService.getOrderById(order.getOrderId());
        assertFalse(retrieved.isPresent());
    }
}
