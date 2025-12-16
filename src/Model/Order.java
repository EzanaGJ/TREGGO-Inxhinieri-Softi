package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {

    public int orderId;
    int itemId;
    double price;
    public Address shippingAddr;
    public Date createdAt;
    public String status;
    String trackingNo;
    public int buyerId;
    public int sellerId;

    public static List<Order> orderDatabase = new ArrayList<>();
    static int idCounter = 1;

    // Konstruktor
    public Order(int itemId, double price) {
        this.orderId = idCounter++;
        this.itemId = itemId;
        this.price = price;
    }

    // Krijimi i porosisë
    public void createOrder() {
        this.createdAt = new Date();
        this.status = "CREATED";
        this.trackingNo = UUID.randomUUID().toString();
        orderDatabase.add(this);
        System.out.println("Order " + orderId + " created");
    }

    // Llogaritja e totalit
    public double calculateTotal() {
        return price;
    }

    // Shtimi i adresës së dërgesës
    public void addAddress(Address a) {
        this.shippingAddr = a;
    }

    // Marrja e tracking number
    public String getTrackingNo() {
        return trackingNo;
    }

    // Përditësimi i statusit
    public void updateStatus(String status) {
        this.status = status;
    }

    public void assignBuyer(int buyerId) {
        this.buyerId = buyerId;
    }

    public void assignSeller(int sellerId) {
        this.sellerId = sellerId;
    }
}
