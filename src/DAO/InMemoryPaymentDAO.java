package DAO;

import Model.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryPaymentDAO implements PaymentDAO {

    private final List<Payment> storage = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public Payment create(Payment payment) throws SQLException {
        payment.setPaymentId(idCounter++);
        storage.add(payment);
        return payment;
    }

    @Override
    public Payment getPaymentById(int id) throws SQLException {
        return storage.stream()
                .filter(p -> p.getPaymentId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public Payment update(Payment payment) throws SQLException {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getPaymentId() == payment.getPaymentId()) {
                storage.set(i, payment);
                return payment;
            }
        }
        throw new SQLException("Payment not found");
    }

    @Override
    public void delete(int id) throws SQLException {
        storage.removeIf(p -> p.getPaymentId() == id);
    }
}
