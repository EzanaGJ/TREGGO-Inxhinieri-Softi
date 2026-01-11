package Service;

import DAO.PaymentDAO;
import Model.Payment;

public class PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentService(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public Payment createPayment(int orderId, String method, double amount) throws Exception {
        if (orderId <= 0) throw new IllegalArgumentException("Invalid order ID");
        if (method == null || method.isEmpty()) throw new IllegalArgumentException("Payment method required");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

        Payment payment = new Payment(orderId, method, amount);
        return paymentDAO.create(payment);
    }

    public Payment getPaymentById(int id) {
        if (id <= 0) return null;
        try {
            return paymentDAO.getPaymentById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public Payment updatePayment(Payment payment) throws Exception {
        if (payment == null) throw new IllegalArgumentException("Payment cannot be null");
        return paymentDAO.update(payment);
    }

    public void deletePayment(int id) {
        if (id <= 0) return;
        try {
            paymentDAO.delete(id);
        } catch (Exception e) {
            // Ignore exception
        }
    }
}
