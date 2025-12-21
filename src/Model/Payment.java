package Model;

import Model.Enum.PaymentStatus;
import java.util.Date;

public class Payment {
    private String method;
    private double amount;
    private Date timestamp;
    private PaymentStatus paymentStatus;

    public Payment(String method, double amount) {
        this.method = method;
        this.amount = amount;
        this.timestamp = new Date();
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Getters & Setters
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
}
