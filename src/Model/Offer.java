package Model;

import java.util.ArrayList;
import java.util.List;

public class Offer {
    int offerId;
    int itemId;
    public double offeredPrice;
    public String status;

    public static List<Offer> offerDatabase = new ArrayList<>();
    public static int idCounter = 1;

    public Offer(int itemId, double offeredPrice) {
        this.offerId = idCounter++;
        this.itemId = itemId;
        this.offeredPrice = offeredPrice;
        this.status = "pending";
    }

    public void makeOffer() {
        offerDatabase.add(this);
        System.out.println("Offer " + offerId + " made for item " + itemId + " at price $" + offeredPrice);
    }

    public void modifyOffer(double newPrice) {
        if ("pending".equals(status)) {
            this.offeredPrice = newPrice;
            System.out.println("Offer " + offerId + " updated to $" + newPrice);
        } else {
            System.out.println("Cannot modify offer " + offerId + " because it is " + status);
        }
    }

    public void acceptOffer() {
        if ("pending".equals(status)) {
            status = "accepted";
            System.out.println("Offer " + offerId + " accepted.");
        } else {
            System.out.println("Cannot accept offer " + offerId + " because it is " + status);
        }
    }

    public void declineOffer() {
        if ("pending".equals(status)) {
            status = "declined";
            System.out.println("Offer " + offerId + " declined.");
        } else {
            System.out.println("Cannot decline offer " + offerId + " because it is " + status);
        }
    }

    public void cancelOffer() {
        if ("pending".equals(status)) {
            status = "canceled";
            System.out.println("Offer " + offerId + " canceled.");
        } else {
            System.out.println("Cannot cancel offer " + offerId + " because it is " + status);
        }
    }

    public void updatePrice(double newPrice) {
        modifyOffer(newPrice);
    }

    public String getStatus() {
        return status;
    }

    static void listOffersForItem(int itemId) {
        System.out.println("Offers for item " + itemId + ":");
        for (Offer o : offerDatabase) {
            if (o.itemId == itemId) {
                System.out.println(" - Offer " + o.offerId + ": $" + o.offeredPrice + " (" + o.status + ")");
            }
        }
    }
}