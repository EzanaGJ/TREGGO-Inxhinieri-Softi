package Test;

import DAO.TransactionDAO;
import DAO.TransactionDAOImpl; // DAO konkrete që ruan në List
import Model.Transaction;
import Service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        TransactionDAO dao = new TransactionDAOImpl(); // DAO konkrete
        transactionService = new TransactionService(dao);
    }

    @Test
    void testCreateTransaction() throws Exception {
        Transaction t = new Transaction(1, 100.0, "Credit Card");
        Transaction created = transactionService.createTransaction(t);
        assertNotNull(created);
        assertEquals(100.0, created.getAmount());
        assertEquals("Credit Card", created.getPaymentType());
    }

    @Test
    void testCreateTransaction_NullTransaction() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                transactionService.createTransaction(null));
        assertEquals("Transaction cannot be null", ex.getMessage());
    }

    @Test
    void testCreateTransaction_NegativeAmount() {
        Transaction t = new Transaction(1, -50.0, "Cash");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                transactionService.createTransaction(t));
        assertEquals("Amount must be positive", ex.getMessage());
    }

    @Test
    void testGetTransactionById() throws Exception {
        Transaction t = new Transaction(1, 50.0, "Cash");
        Transaction created = transactionService.createTransaction(t);

        Transaction found = transactionService.getTransactionById(created.getTransactionId());
        assertNotNull(found);
        assertEquals(50.0, found.getAmount());

        // test invalid id
        assertNull(transactionService.getTransactionById(0));
        assertNull(transactionService.getTransactionById(-1));
        assertNull(transactionService.getTransactionById(999)); // non-existent
    }

    @Test
    void testUpdateTransaction() throws Exception {
        Transaction t = new Transaction(1, 50.0, "Cash");
        Transaction created = transactionService.createTransaction(t);

        created.setAmount(75.0);
        Transaction updated = transactionService.updateTransaction(created);
        assertEquals(75.0, updated.getAmount());
    }

    @Test
    void testUpdateTransaction_NullTransaction() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                transactionService.updateTransaction(null));
        assertEquals("Transaction cannot be null", ex.getMessage());
    }

    @Test
    void testUpdateTransaction_NegativeAmount() throws Exception {
        Transaction t = new Transaction(1, 50.0, "Cash");
        Transaction created = transactionService.createTransaction(t);
        created.setAmount(-10.0);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                transactionService.updateTransaction(created));
        assertEquals("Amount must be positive", ex.getMessage());
    }

    @Test
    void testDeleteTransaction() throws Exception {
        Transaction t = new Transaction(1, 50.0, "Cash");
        Transaction created = transactionService.createTransaction(t);

        transactionService.deleteTransaction(created.getTransactionId());
        assertNull(transactionService.getTransactionById(created.getTransactionId()));

        // test invalid ids
        transactionService.deleteTransaction(0);
        transactionService.deleteTransaction(-5);
        transactionService.deleteTransaction(999); // non-existent
    }
}
