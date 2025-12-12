package Model;

class Order {
    final int orderId;
    final int itemId;
    final int buyerId;
    final int sellerId;
    double price;
    String status; // created, paid, shipped, delivered, cancelled
    Address shippingAddress;
    Date created = new Date();

    Order(int id, int itemId, int buyerId, int sellerId, double price, Address addr){
        this.orderId = id;
        this.itemId = itemId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status = "created";
        this.shippingAddress = addr;
    }
    @Override public String toString(){ return String.format("Order{id=%d,item=%d,price=%.2f,status=%s}",orderId,itemId,price,status); }
}
