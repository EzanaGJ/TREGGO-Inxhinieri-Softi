package Model;

import java.util.Date;

public class Transaction {

    private int transactionId;
    private int paymentId;
    private double amount;
    private String paymentType;
    private Date createdAt;

    // Konstruktor
    public Transaction(int transactionId, double amount, String paymentType) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.createdAt = new Date();
    }

    // GETTERS
    public int getTransactionId() { return transactionId; }
    public int getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getPaymentType() { return paymentType; } // ✅ kjo duhet
    public Date getCreatedAt() { return createdAt; }

    // SETTERS
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; } // ✅ kjo duhet
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
