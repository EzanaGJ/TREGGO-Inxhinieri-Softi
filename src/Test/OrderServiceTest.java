package Test;

import DAO.JdbcOrderDAO;
import DAO.OrderDAO;
import Model.Order;
import Service.OrderService;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;
    private int testUserId;
    private int testAddressId;

    @BeforeEach
    void setUp() throws SQLException {
        OrderDAO orderDAO = new JdbcOrderDAO();
        orderService = new OrderService(orderDAO);

        try (Connection conn = DatabaseManager.getInstance().getConnection()) {

            // create unique user per test (prevents duplicate email error)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user (name, password, role_type, email) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, "Order Test User");
                ps.setString(2, "password123");
                ps.setString(3, "USER");
                ps.setString(4, "order_" + System.nanoTime() + "@test.com");
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) testUserId = rs.getInt(1);
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO addresses (user_id, city, street) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, testUserId);
                ps.setString(2, "Test City");
                ps.setString(3, "Test Street");
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) testAddressId = rs.getInt(1);
            }
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM `order` WHERE user_id = ?")) {
                ps.setInt(1, testUserId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM addresses WHERE address_id = ?")) {
                ps.setInt(1, testAddressId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM user WHERE user_id = ?")) {
                ps.setInt(1, testUserId);
                ps.executeUpdate();
            }
        }
    }

    private Order createTestOrder() throws SQLException {
        return orderService.createOrder(testUserId, testAddressId);
    }

    @Test
    void testCreateOrder() throws SQLException {
        Order order = createTestOrder();
        assertTrue(order.getOrderId() > 0);
    }

    @Test
    void testCalculateTotal() throws SQLException {
        Order order = createTestOrder();
        double total = orderService.calculateTotal(order);

        assertTrue(total >= 0);

        boolean found = false;
        for (String a : orderService.getActions()) {
            if (a.startsWith("CALCULATE_TOTAL:" + order.getOrderId())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testGetAddress() throws SQLException {
        Order order = createTestOrder();
        String address = orderService.getAddress(order);

        assertNotNull(address);

        boolean found = false;
        for (String a : orderService.getActions()) {
            if (a.startsWith("GET_ADDRESS:" + order.getOrderId())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testGetTrackingNo() throws SQLException {
        Order order = createTestOrder();
        String tracking = orderService.getTrackingNo(order);

        assertNotNull(tracking);
        assertTrue(tracking.contains(String.valueOf(order.getOrderId())));

        boolean found = false;
        for (String a : orderService.getActions()) {
            if (a.startsWith("GET_TRACKING_NO:" + order.getOrderId())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testUpdateStatus() throws SQLException {
        Order order = createTestOrder();
        orderService.updateStatus(order, "shipped");

        boolean found = false;
        for (String a : orderService.getActions()) {
            if (a.equals("UPDATE_STATUS:" + order.getOrderId() + ":shipped")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testAssignBuyer() throws SQLException {
        Order order = createTestOrder();
        orderService.assignBuyer(order, 999);

        assertEquals(999, order.getUserId());

        boolean found = false;
        for (String a : orderService.getActions()) {
            if (a.equals("ASSIGN_BUYER:" + order.getOrderId() + ":999")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testAssignSeller() throws SQLException {
        Order order = createTestOrder();
        orderService.assignSeller(order, 888);

        boolean found = false;
        for (String a : orderService.getActions()) {
            if (a.equals("ASSIGN_SELLER:" + order.getOrderId() + ":888")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testGetOrderById() throws SQLException {
        Order order = createTestOrder();
        Order fetched = orderService.getOrderById(order.getOrderId());

        assertNotNull(fetched);
        assertEquals(order.getOrderId(), fetched.getOrderId());
    }
}
