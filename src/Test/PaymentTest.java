package Test;

import Model.Payment;
import Model.Enum.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @BeforeEach
    void setUp() {
        Payment.paymentDatabase.clear();
    }

    @Test
    void testCreatePaymentInitialStatus() {
        Payment payment = new Payment("Credit Card", 100.0);

        assertEquals("Credit Card", payment.method);
        assertEquals(100.0, payment.amount);
        assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus());
        assertNotNull(payment.timestamp);
    }

    @Test
    void testProcessPayment() {
        Payment payment = new Payment("PayPal", 75.0);
        payment.processPayment();

        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus());
        assertEquals(1, Payment.paymentDatabase.size());
        assertEquals(payment, Payment.paymentDatabase.get(0));
    }

    @Test
    void testVerifyPaymentCompleted() {
        Payment payment = new Payment("Credit Card", 50.0);
        payment.processPayment();

        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus());
    }

    @Test
    void testSetPaymentStatus() {
        Payment payment = new Payment("Debit Card", 30.0);
        payment.setPaymentStatus(PaymentStatus.FAILED);

        assertEquals(PaymentStatus.FAILED, payment.getPaymentStatus());
    }

    @Test
    void testRefundWhenCompleted() {
        Payment payment = new Payment("PayPal", 120.0);
        payment.processPayment();

        payment.refund();

        assertEquals(PaymentStatus.REFUNDED, payment.getPaymentStatus());
    }

    @Test
    void testRefundWhenNotCompleted() {
        Payment payment = new Payment("Credit Card", 60.0);

        payment.refund();

        assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus());
    }

    @Test
    void testMultiplePaymentsAddedToDatabase() {
        Payment p1 = new Payment("Credit Card", 20.0);
        Payment p2 = new Payment("PayPal", 40.0);

        p1.processPayment();
        p2.processPayment();

        assertEquals(2, Payment.paymentDatabase.size());
    }
}
