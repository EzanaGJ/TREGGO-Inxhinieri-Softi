package Test;

import DAO.TransactionDAO;
import Model.Transaction;
import Service.TransactionService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private TransactionDAO transactionDAO;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionDAO = new TransactionDAO();
        transactionDAO.clearDatabase();
        transactionService = new TransactionService(transactionDAO);
    }

    @Test
    void testCreateTransaction() {
        Transaction transaction = new Transaction(1, 100.0, "Credit Card");
        transactionService.createTransaction(transaction);

        Transaction fromDB = transactionDAO.getTransaction(1);
        assertNotNull(fromDB);
        assertEquals(100.0, fromDB.getAmount());
        assertEquals("Credit Card", fromDB.getPaymentType());
    }

    @Test
    void testUpdateTransaction() {
        Transaction transaction = new Transaction(2, 50.0, "PayPal");
        transactionService.createTransaction(transaction);

        transaction.setAmount(75.0);
        transactionService.updateTransaction(transaction);

        Transaction fromDB = transactionDAO.getTransaction(2);
        assertEquals(75.0, fromDB.getAmount());
    }

    @Test
    void testDeleteTransaction() {
        Transaction transaction = new Transaction(3, 20.0, "Cash");
        transactionService.createTransaction(transaction);

        transactionService.deleteTransaction(3);
        assertNull(transactionDAO.getTransaction(3));
    }

    @Test
    void testLinkOrderAndPayment() {
        Transaction transaction = new Transaction(4, 150.0, "Card");
        transactionService.createTransaction(transaction);

        transactionService.linkToOrder(transaction, 101);
        transactionService.linkToPayment(transaction, 201);

        Transaction fromDB = transactionDAO.getTransaction(4);
        assertEquals(101, fromDB.getOrderId());
        assertEquals(201, fromDB.getPaymentId());
    }
}
