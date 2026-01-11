package Test;

import DAO.InMemoryPaymentDAO;
import Model.Payment;
import Service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        InMemoryPaymentDAO dao = new InMemoryPaymentDAO();
        paymentService = new PaymentService(dao);
    }

    @Test
    void testCreatePayment() throws Exception {
        Payment payment = paymentService.createPayment(1, "Credit Card", 100.0);
        assertNotNull(payment);
        assertEquals(1, payment.getOrderId());
        assertEquals("Credit Card", payment.getMethod());
        assertEquals(100.0, payment.getAmount());
    }

    @Test
    void testCreatePayment_InvalidOrderId() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                paymentService.createPayment(0, "Credit Card", 100));
        assertEquals("Invalid order ID", ex.getMessage());
    }

    @Test
    void testCreatePayment_EmptyMethod() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                paymentService.createPayment(1, "", 100));
        assertEquals("Payment method required", ex.getMessage());
    }

    @Test
    void testCreatePayment_NegativeAmount() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                paymentService.createPayment(1, "Cash", -10));
        assertEquals("Amount must be positive", ex.getMessage());
    }

    @Test
    void testGetPaymentById() throws Exception {
        Payment payment = paymentService.createPayment(1, "Cash", 50);
        Payment found = paymentService.getPaymentById(payment.getPaymentId());
        assertNotNull(found);
        assertEquals(50, found.getAmount());

        // invalid id
        assertNull(paymentService.getPaymentById(0));
        assertNull(paymentService.getPaymentById(-1));
    }

    @Test
    void testUpdatePayment() throws Exception {
        Payment payment = paymentService.createPayment(1, "Cash", 50);
        payment.setAmount(75);
        Payment updated = paymentService.updatePayment(payment);
        assertEquals(75, updated.getAmount());
    }

    @Test
    void testUpdatePayment_NullPayment() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                paymentService.updatePayment(null));
        assertEquals("Payment cannot be null", ex.getMessage());
    }

    @Test
    void testDeletePayment() throws Exception {
        Payment payment = paymentService.createPayment(1, "Cash", 50);
        paymentService.deletePayment(payment.getPaymentId());
        assertNull(paymentService.getPaymentById(payment.getPaymentId()));

        // test invalid ids
        paymentService.deletePayment(0);
        paymentService.deletePayment(-5);
    }
}
