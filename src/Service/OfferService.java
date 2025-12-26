package Service;

import DAO.OfferDAO;
import Model.Offer;
import java.util.List;

public class OfferService {

    private final OfferDAO offerDAO = new OfferDAO();

    public Offer makeOffer(int itemId, double price) {
        return offerDAO.save(itemId, price);
    }

    public void updateOfferPrice(int offerId, double newPrice) {
        Offer offer = offerDAO.findById(offerId);
        if (offer != null && "pending".equals(offer.getStatus())) {
            offer.setOfferedPrice(newPrice);
        }
    }

    public void acceptOffer(int offerId) {
        Offer offer = offerDAO.findById(offerId);
        if (offer != null && "pending".equals(offer.getStatus())) {
            offer.setStatus("accepted");
        }
    }

    public void declineOffer(int offerId) {
        Offer offer = offerDAO.findById(offerId);
        if (offer != null && "pending".equals(offer.getStatus())) {
            offer.setStatus("declined");
        }
    }

    public void cancelOffer(int offerId) {
        Offer offer = offerDAO.findById(offerId);
        if (offer != null && "pending".equals(offer.getStatus())) {
            offer.setStatus("canceled");
        }
    }

    public List<Offer> getOffersForItem(int itemId) {
        return offerDAO.findByItem(itemId);
    }

    public void clearAll() {
        offerDAO.clear();
    }
}
