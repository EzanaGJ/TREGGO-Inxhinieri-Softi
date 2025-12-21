package DAO;

import Model.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private static final List<Payment> paymentDatabase = new ArrayList<>();

    public void addPayment(Payment payment) {
        paymentDatabase.add(payment);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentDatabase); // return copy
    }

    public void clearDatabase() {
        paymentDatabase.clear();
    }
}
