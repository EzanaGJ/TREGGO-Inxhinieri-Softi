package DAO;

import Model.Payment;
import java.sql.SQLException;

public interface PaymentDAO {

    Payment create(Payment payment) throws SQLException;

    Payment getPaymentById(int id) throws SQLException;

    Payment update(Payment payment) throws SQLException;

    void delete(int id) throws SQLException;
}

