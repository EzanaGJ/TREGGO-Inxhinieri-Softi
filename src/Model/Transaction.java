package Model;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private int orderId;
    private int paymentId;
    private double amount;
    private String paymentType;
    private Date createdAt;

    public Transaction(int transactionId, double amount, String paymentType) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.createdAt = new Date();
    }

    // Getters & Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
