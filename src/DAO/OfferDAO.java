package DAO;

import Model.Offer;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {

    private static final List<Offer> offerDB = new ArrayList<>();
    private static int idCounter = 1;

    public Offer save(int itemId, double offeredPrice) {
        Offer offer = new Offer(
                idCounter++,
                itemId,
                offeredPrice,
                "pending"
        );
        offerDB.add(offer);
        return offer;
    }

    public List<Offer> findByItem(int itemId) {
        List<Offer> result = new ArrayList<>();
        for (Offer o : offerDB) {
            if (o.getItemId() == itemId) {
                result.add(o);
            }
        }
        return result;
    }

    public Offer findById(int offerId) {
        for (Offer o : offerDB) {
            if (o.getOfferId() == offerId) {
                return o;
            }
        }
        return null;
    }

    public void delete(int offerId) {
        offerDB.removeIf(o -> o.getOfferId() == offerId);
    }

    // vetëm për unit test
    public void clear() {
        offerDB.clear();
        idCounter = 1;
    }
}
