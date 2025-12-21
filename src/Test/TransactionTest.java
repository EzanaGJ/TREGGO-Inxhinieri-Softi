package Test;

import Model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @BeforeEach
    void setUp() {
        Transaction.transactionDB.clear();
    }

    @Test
    void testCreateTransaction() {
        Transaction t = new Transaction();
        t.transactionId = 1;
        t.amount = 100.0;
        t.paymentType = "Credit Card";

        t.createTransaction();

        assertEquals(1, Transaction.transactionDB.size());
        assertTrue(Transaction.transactionDB.containsKey(1));
        assertNotNull(t.createdAt);
    }

    @Test
    void testUpdateTransaction() {
        Transaction t = new Transaction();
        t.transactionId = 2;
        t.amount = 50.0;
        t.paymentType = "PayPal";
        t.createTransaction();

        // ndryshojmë të dhënat
        t.amount = 75.0;
        t.paymentType = "Debit Card";
        t.updateTransaction();

        Transaction updated = Transaction.transactionDB.get(2);
        assertEquals(75.0, updated.amount);
        assertEquals("Debit Card", updated.paymentType);
    }

    @Test
    void testDeleteTransaction() {
        Transaction t = new Transaction();
        t.transactionId = 3;
        t.amount = 30.0;
        t.paymentType = "Cash";
        t.createTransaction();

        t.deleteTransaction();

        assertFalse(Transaction.transactionDB.containsKey(3));
    }

    @Test
    void testLinkToOrder() {
        Transaction t = new Transaction();
        t.linkToOrder(101);

        assertEquals(101, t.orderId);
    }

    @Test
    void testLinkToPayment() {
        Transaction t = new Transaction();
        t.linkToPayment(202);

        assertEquals(202, t.paymentId);
    }

    @Test
    void testValidateTransactionValid() {
        Transaction t = new Transaction();
        t.amount = 120.0;
        t.paymentType = "Credit Card";

        assertDoesNotThrow(t::validateTransaction);
    }

    @Test
    void testValidateTransactionInvalidAmount() {
        Transaction t = new Transaction();
        t.amount = -10.0;
        t.paymentType = "PayPal";

        assertDoesNotThrow(t::validateTransaction);
    }

    @Test
    void testValidateTransactionMissingPaymentType() {
        Transaction t = new Transaction();
        t.amount = 50.0;
        t.paymentType = "";

        assertDoesNotThrow(t::validateTransaction);
    }
}