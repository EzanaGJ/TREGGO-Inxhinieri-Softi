package Test;

import DAO.JdbcPaymentDAO;
import Model.Payment;
import Model.Enum.PaymentStatus;
import Service.PaymentService;
import db.DatabaseManager;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    private PaymentService paymentService;
    private int orderId;

    @BeforeEach
    void setUp() throws SQLException {
        paymentService = new PaymentService(new JdbcPaymentDAO());

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            // CREATE USER
            stmt.executeUpdate(
                    "INSERT INTO user(name, password, email) " +
                            "VALUES ('PayUser','123','pay_user@example.com')",
                    Statement.RETURN_GENERATED_KEYS
            );

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);

            // CREATE ADDRESS
            stmt.executeUpdate(
                    "INSERT INTO addresses(user_id, city) " +
                            "VALUES (" + userId + ", 'Tirana')",
                    Statement.RETURN_GENERATED_KEYS
            );

            rs = stmt.getGeneratedKeys();
            rs.next();
            int addressId = rs.getInt(1);

            // CREATE ORDER  (FK i nevojshëm për payment)
            stmt.executeUpdate(
                    "INSERT INTO `order`(user_id, address_id) " +
                            "VALUES (" + userId + ", " + addressId + ")",
                    Statement.RETURN_GENERATED_KEYS
            );

            rs = stmt.getGeneratedKeys();
            rs.next();
            orderId = rs.getInt(1);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM payment");
            stmt.executeUpdate("DELETE FROM `order`");
            stmt.executeUpdate("DELETE FROM addresses");
            stmt.executeUpdate("DELETE FROM user WHERE email='pay_user@example.com'");
        }
    }

    @Test
    void testCreatePaymentSuccess() throws SQLException {
        Payment p = new Payment(orderId, "CARD", 49.99);

        Payment created = paymentService.createPayment(p);

        assertNotNull(created);
        assertNotEquals(0, created.getPaymentId());
        assertEquals(PaymentStatus.PENDING, created.getPaymentStatus());
    }

    @Test
    void testCreatePaymentNull() {
        try {
            paymentService.createPayment(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }

    @Test
    void testCreatePaymentInvalidAmount() {
        try {
            paymentService.createPayment(
                    new Payment(orderId, "CARD", -5));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }

    @Test
    void testCreatePaymentNoMethod() {
        try {
            paymentService.createPayment(
                    new Payment(orderId, "", 10));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }

    @Test
    void testGetPaymentById() throws SQLException {
        Payment p = paymentService.createPayment(
                new Payment(orderId, "PAYPAL", 25));

        Payment found = paymentService.getPaymentById(p.getPaymentId());

        assertNotNull(found);
        assertEquals(p.getPaymentId(), found.getPaymentId());
    }

    @Test
    void testGetPaymentNotFound() throws SQLException {
        assertNull(paymentService.getPaymentById(999999));
    }

    @Test
    void testUpdatePaymentStatus() throws SQLException {
        Payment p = paymentService.createPayment(
                new Payment(orderId, "CARD", 100));

        paymentService.updatePaymentStatus(
                p.getPaymentId(), PaymentStatus.SUCCESS);

        Payment updated = paymentService.getPaymentById(p.getPaymentId());
        assertEquals(PaymentStatus.SUCCESS, updated.getPaymentStatus());
    }

    @Test
    void testUpdatePaymentNotFound() throws SQLException {
        paymentService.updatePaymentStatus(
                999999, PaymentStatus.FAILED);
    }

    @Test
    void testDeletePayment() throws SQLException {
        Payment p = paymentService.createPayment(
                new Payment(orderId, "CARD", 30));

        paymentService.deletePayment(p.getPaymentId());

        assertNull(paymentService.getPaymentById(p.getPaymentId()));
    }

    @Test
    void testDeleteInvalidId() throws SQLException {
        paymentService.deletePayment(-1);
    }
}
