package Test;

import DAO.JdbcTransactionDAO;
import Model.Transaction;
import Service.TransactionService;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    private TransactionService service;
    private int transactionId;
    private static int paymentId;

    @BeforeEach
    void setUp() {
        service = new TransactionService(new JdbcTransactionDAO());
    }

    @BeforeAll
    static void setupAll() {
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {

            // Shto user dhe merr id
            int userId;
            try (Statement st = conn.createStatement()) {
                st.execute("INSERT INTO user (name, password) VALUES ('trxUser','123')");
                var rs = st.executeQuery("SELECT user_id FROM user WHERE name='trxUser' LIMIT 1");
                rs.next();
                userId = rs.getInt(1);

                // Shto address duke përdorur userId
                st.execute("INSERT INTO addresses (user_id, city) VALUES (" + userId + ",'Tirane')");
                rs = st.executeQuery("SELECT address_id FROM addresses WHERE user_id=" + userId + " LIMIT 1");
                rs.next();
                int addressId = rs.getInt(1);

                // Shto order
                st.execute("INSERT INTO `order` (user_id, address_id) VALUES (" + userId + "," + addressId + ")");
                rs = st.executeQuery("SELECT order_id FROM `order` WHERE user_id=" + userId + " LIMIT 1");
                rs.next();
                int orderId = rs.getInt(1);

                // Shto payment
                st.execute("INSERT INTO payment (order_id, amount, status) VALUES (" + orderId + ",100,'SUCCESS')");
                rs = st.executeQuery("SELECT payment_id FROM payment WHERE order_id=" + orderId + " LIMIT 1");
                rs.next();
                paymentId = rs.getInt(1);
            }

        } catch (Exception e) {
            fail("SetupAll failed: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        if (transactionId > 0) {
            try {
                service.deleteTransaction(transactionId);
            } catch (Exception e) {
                fail("Cleanup failed: " + e.getMessage());
            }
            transactionId = 0;
        }
    }

    @AfterAll
    static void cleanupAll() {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement st = conn.createStatement()) {

            st.execute("DELETE FROM transaction");
            st.execute("DELETE FROM payment");
            st.execute("DELETE FROM `order`");
            st.execute("DELETE FROM addresses");
            st.execute("DELETE FROM user");

        } catch (Exception e) {
            fail("CleanupAll failed: " + e.getMessage());
        }
    }

    @Test
    void testCreateAndGetTransaction() {
        try {
            Transaction t = new Transaction(0, 50, "payment");
            t.setPaymentId(paymentId);
            Transaction created = service.createTransaction(t);
            transactionId = created.getTransactionId();

            Transaction found = service.getTransactionById(transactionId);
            assertNotNull(found);
            assertEquals(50, found.getAmount());
            assertEquals(paymentId, found.getPaymentId());

        } catch (Exception e) {
            fail("testCreateAndGetTransaction failed: " + e.getMessage());
        }
    }

    @Test
    void testUpdateTransaction() {
        try {
            Transaction t = new Transaction(0, 30, "payment");
            t.setPaymentId(paymentId);
            Transaction created = service.createTransaction(t);
            transactionId = created.getTransactionId();

            created.setAmount(80);
            Transaction updated = service.updateTransaction(created);
            assertEquals(80, updated.getAmount());

        } catch (Exception e) {
            fail("testUpdateTransaction failed: " + e.getMessage());
        }
    }

    @Test
    void testInvalidAmountThrowsException() {
        try {
            Transaction t = new Transaction(0, -10, "payment");
            service.createTransaction(t);
            fail("Expected IllegalArgumentException for negative amount");
        } catch (IllegalArgumentException ignored) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        try {
            Transaction t = new Transaction(0, 0, "payment");
            service.createTransaction(t);
            fail("Expected IllegalArgumentException for zero amount");
        } catch (IllegalArgumentException ignored) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetTransactionByIdNotFound() {
        try {
            Transaction t = service.getTransactionById(999999);
            assertNull(t);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testDeleteTransactionNotFound() {
        try {
            service.deleteTransaction(999999);
        } catch (Exception e) {
            fail("Deleting non-existing transaction should not throw");
        }
    }

    @Test
    void testCreateTransactionNullThrowsException() {
        try {
            service.createTransaction(null);
            fail("Expected IllegalArgumentException for null transaction");
        } catch (IllegalArgumentException ignored) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testUpdateTransactionNullThrowsException() {
        try {
            service.updateTransaction(null);
            fail("Expected IllegalArgumentException for null transaction");
        } catch (IllegalArgumentException ignored) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
