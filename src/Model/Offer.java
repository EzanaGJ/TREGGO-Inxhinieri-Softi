package Model;

public class Offer {

    private int offerId;
    private int itemId;
    private double offeredPrice;
    private String status;

    // Constructor used by DAO
    public Offer(int offerId, int itemId, double offeredPrice, String status) {
        this.offerId = offerId;
        this.itemId = itemId;
        this.offeredPrice = offeredPrice;
        this.status = status;
    }

    // Getters
    public int getOfferId() { return offerId; }
    public int getItemId() { return itemId; }
    public double getOfferedPrice() { return offeredPrice; }
    public String getStatus() { return status; }

    // Setters
    public void setOfferedPrice(double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
