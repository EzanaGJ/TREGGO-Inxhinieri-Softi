package Service;

import DAO.PaymentDAO;
import Model.Payment;
import Model.Enum.PaymentStatus;

import java.sql.SQLException;

public class PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentService(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public Payment createPayment(Payment payment) throws SQLException {
        if (payment == null) {
            throw new IllegalArgumentException("Payment is null");
        }
        if (payment.getOrderId() <= 0) {
            throw new IllegalArgumentException("Invalid order id");
        }
        if (payment.getMethod() == null || payment.getMethod().isEmpty()) {
            throw new IllegalArgumentException("Payment method required");
        }
        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        payment.setPaymentStatus(PaymentStatus.PENDING);
        return paymentDAO.create(payment);
    }

    public Payment getPaymentById(int id) throws SQLException {
        if (id <= 0) return null;
        return paymentDAO.getPaymentById(id);
    }

    public Payment updatePaymentStatus(int id, PaymentStatus status) throws SQLException {
        Payment p = paymentDAO.getPaymentById(id);
        if (p == null) return null;

        p.setPaymentStatus(status);
        return paymentDAO.update(p);
    }

    public void deletePayment(int id) throws SQLException {
        if (id <= 0) return;
        paymentDAO.delete(id);
    }
}

