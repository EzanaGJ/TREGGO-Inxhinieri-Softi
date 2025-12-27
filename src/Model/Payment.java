package Model;

import Model.Enum.PaymentStatus;
import java.util.Date;

public class Payment {

    private int paymentId;     // PK
    private int orderId;       // FK → order.order_id
    private String method;
    private double amount;
    private PaymentStatus paymentStatus;
    private Date timestamp;

    public Payment(int orderId, String method, double amount) {
        this.orderId = orderId;
        this.method = method;
        this.amount = amount;
        this.paymentStatus = PaymentStatus.PENDING;
        this.timestamp = new Date();
    }

    // GETTERS , SETTERS

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
