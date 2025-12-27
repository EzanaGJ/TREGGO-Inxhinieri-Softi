package Model;

public class Offer {

    private int offerId;       // auto-increment nga DB
    private int userId;        // foreign key
    private int itemId;        // foreign key
    private double offeredPrice;
    private String status;

    // Konstruktor për krijimin nga DAO (të gjithë fushat)
    public Offer(int offerId, int userId, int itemId, double offeredPrice, String status) {
        this.offerId = offerId;
        this.userId = userId;
        this.itemId = itemId;
        this.offeredPrice = offeredPrice;
        this.status = status;
    }

    // Konstruktor për krijim i ri (pa offerId)
    public Offer(int userId, int itemId, double offeredPrice, String status) {
        this(0, userId, itemId, offeredPrice, status);
    }

    // Getters
    public int getOfferId() { return offerId; }
    public int getUserId() { return userId; }
    public int getItemId() { return itemId; }
    public double getOfferedPrice() { return offeredPrice; }
    public String getStatus() { return status; }

    // Setters
    public void setOfferId(int offerId) { this.offerId = offerId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setOfferedPrice(double offeredPrice) { this.offeredPrice = offeredPrice; }
    public void setStatus(String status) { this.status = status; }
}
