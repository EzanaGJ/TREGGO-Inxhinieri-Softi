package Model;

import Model.Enum.OrderStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Order {
    int orderId;
    int itemId;
    double price;
    Address shippingAddr;
    Date createdAt;
    OrderStatus status;
    int buyerId;
    int sellerId;
    String trackingNo;

    static List<Order> orderDatabase = new ArrayList<>();
    static int idCounter = 1;

    public Order(int itemId, double price) {
        this.orderId = idCounter++;
        this.itemId = itemId;
        this.price = price;
        this.createdAt = new Date();
        this.status = OrderStatus.PENDING;
    }

    void createOrder() {
        orderDatabase.add(this);
        System.out.println("Order " + orderId + " created for item " + itemId + " at price $" + price);
    }

   double calculateTotal() {
        System.out.println("Total for order " + orderId + " is $" + price);
        return price;
    }

    void addAddress(Address a) {
        this.shippingAddr = a;
        System.out.println("Shipping address added for order " + orderId + ": " + a.street + ", " + a.city);
    }

    String getTrackingNo() {
        if (trackingNo == null) {
            trackingNo = "TRK" + orderId + new Date().getTime();
        }
        System.out.println("Tracking number for order " + orderId + ": " + trackingNo);
        return trackingNo;
    }

    void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        System.out.println("Order " + orderId + " status updated to " + newStatus);
    }

    void assignBuyer(int buyerId) {
        this.buyerId = buyerId;
        System.out.println("Buyer " + buyerId + " assigned to order " + orderId);
    }

    void assignSeller(int sellerId) {
        this.sellerId = sellerId;
        System.out.println("Seller " + sellerId + " assigned to order " + orderId);
    }

    static void listAllOrders() {
        System.out.println("All Orders:");
        for (Order o : orderDatabase) {
            System.out.println(" - Order " + o.orderId + ": item " + o.itemId + " $" + o.price + " status: " + o.status);
        }
    }
}