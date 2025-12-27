package Service;

import DAO.OfferDAO;
import Model.Offer;

import java.sql.SQLException;

public class OfferService {

    private final OfferDAO offerDAO;

    public OfferService(OfferDAO offerDAO) {
        this.offerDAO = offerDAO;
    }

    public Offer createOffer(Offer offer) throws SQLException {
        if (offer == null) throw new IllegalArgumentException("Offer cannot be null");
        if (offer.getOfferedPrice() <= 0) throw new IllegalArgumentException("Price must be positive");
        return offerDAO.create(offer);
    }

    public Offer getOfferById(int id) throws SQLException {
        return offerDAO.getOfferById(id);
    }

    public Offer updateOffer(Offer offer) throws SQLException {
        return offerDAO.update(offer);
    }

    public void deleteOffer(int id) throws SQLException {
        offerDAO.delete(id);
    }
}
