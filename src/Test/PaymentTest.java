package Test;

import DAO.PaymentDAO;
import Model.Enum.PaymentStatus;
import Model.Payment;
import Service.PaymentService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    private PaymentDAO paymentDAO;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentDAO = new PaymentDAO();
        paymentDAO.clearDatabase();
        paymentService = new PaymentService(paymentDAO);
    }

    @Test
    void testProcessPayment() {
        Payment payment = new Payment("Credit Card", 100.0);
        paymentService.processPayment(payment);

        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus());
        assertFalse(paymentDAO.getAllPayments().isEmpty());
        assertEquals(payment, paymentDAO.getAllPayments().get(0));
    }

    @Test
    void testRefundPayment() {
        Payment payment = new Payment("PayPal", 50.0);
        paymentService.processPayment(payment);
        paymentService.refund(payment);

        assertEquals(PaymentStatus.REFUNDED, payment.getPaymentStatus());
    }

    @Test
    void testVerifyPayment() {
        Payment payment = new Payment("Credit Card", 75.0);
        paymentService.processPayment(payment);
        paymentService.verifyPayment(payment);

        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus());
    }
}
