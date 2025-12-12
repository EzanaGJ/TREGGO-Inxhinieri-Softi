package Model;

class Offer {
    final int offerId;
    final int itemId;
    final int buyerId;
    double offeredPrice;
    String status; // pending, accepted, declined, cancelled
    Date created = new Date();

    Offer(int id, int itemId, int buyerId, double price) {
        this.offerId = id;
        this.itemId = itemId;
        this.buyerId = buyerId;
        this.offeredPrice = price;
        this.status = "pending"; }
    @Override public String toString(){ return String.format("Offer{id=%d,item=%d,buyer=%d,price=%.2f,status=%s}",offerId,itemId,buyerId,offeredPrice,status); }
}
