package Service;

import DAO.PaymentDAO;
import Model.Enum.PaymentStatus;
import Model.Payment;

import java.util.Date;

public class PaymentService {
    private final PaymentDAO paymentDAO;

    public PaymentService(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public void processPayment(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setTimestamp(new Date());
        paymentDAO.addPayment(payment);
        System.out.println("Payment of $" + payment.getAmount() + " via " + payment.getMethod() + " processed successfully.");
    }

    public void verifyPayment(Payment payment) {
        if (payment.getPaymentStatus() == PaymentStatus.COMPLETED) {
            System.out.println("Payment verified: COMPLETED.");
        } else {
            System.out.println("Payment verification failed: " + payment.getPaymentStatus());
        }
    }

    public void refund(Payment payment) {
        if (payment.getPaymentStatus() == PaymentStatus.COMPLETED) {
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
            System.out.println("Payment of $" + payment.getAmount() + " refunded successfully.");
        } else {
            System.out.println("Cannot refund payment in status: " + payment.getPaymentStatus());
        }
    }

    public void listAllPayments() {
        System.out.println("All Payments:");
        for (Payment p : paymentDAO.getAllPayments()) {
            System.out.println(" - $" + p.getAmount() + " via " + p.getMethod() + " (" + p.getPaymentStatus() + ")");
        }
    }
}
