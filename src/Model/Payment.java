package Model;

import Model.Enum.PaymentStatus;

import java.util.*;

public class Payment {
    public String method; // e.g., "Credit Card", "PayPal"
    public double amount;
    public Date timestamp;
    PaymentStatus paymentStatus;

    public static List<Payment> paymentDatabase = new ArrayList<>();

    public Payment(String method, double amount) {
        this.method = method;
        this.amount = amount;
        this.timestamp = new Date();
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public void processPayment() {
        // Simulate processing
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.timestamp = new Date();
        paymentDatabase.add(this);
        System.out.println("Payment of $" + amount + " via " + method + " processed successfully.");
    }

    void verifyPayment() {
        if (paymentStatus == PaymentStatus.COMPLETED) {
            System.out.println("Payment verified: COMPLETED.");
        } else {
            System.out.println("Payment verification failed: " + paymentStatus);
        }
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus status) {
        this.paymentStatus = status;
        System.out.println("Payment status updated to " + status);
    }

    public void refund() {
        if (paymentStatus == PaymentStatus.COMPLETED) {
            paymentStatus = PaymentStatus.REFUNDED;
            System.out.println("Payment of $" + amount + " refunded successfully.");
        } else {
            System.out.println("Cannot refund payment in status: " + paymentStatus);
        }
    }

    static void listAllPayments() {
        System.out.println("All Payments:");
        for (Payment p : paymentDatabase) {
            System.out.println(" - $" + p.amount + " via " + p.method + " (" + p.paymentStatus + ")");
        }
    }
}