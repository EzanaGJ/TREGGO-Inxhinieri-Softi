package Test;

import Model.Offer;
import Service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OfferServiceTest {

    private OfferService offerService;

    @BeforeEach
    void setup() {
        offerService = new OfferService();
        offerService.clearAll();
    }

    @Test
    void testMakeOffer() {
        Offer offer = offerService.makeOffer(1, 100.0);

        assertNotNull(offer);
        assertEquals(1, offer.getItemId());
        assertEquals(100.0, offer.getOfferedPrice());
        assertEquals("pending", offer.getStatus());
    }

    @Test
    void testUpdateOfferPrice() {
        Offer offer = offerService.makeOffer(1, 100.0);
        offerService.updateOfferPrice(offer.getOfferId(), 120.0);

        assertEquals(120.0, offer.getOfferedPrice());
    }

    @Test
    void testAcceptOffer() {
        Offer offer = offerService.makeOffer(1, 90.0);
        offerService.acceptOffer(offer.getOfferId());

        assertEquals("accepted", offer.getStatus());
    }

    @Test
    void testGetOffersForItem() {
        offerService.makeOffer(1, 50.0);
        offerService.makeOffer(1, 60.0);
        offerService.makeOffer(2, 70.0);

        List<Offer> offers = offerService.getOffersForItem(1);
        assertEquals(2, offers.size());
    }

    @Test
    void testCancelOffer() {
        Offer offer = offerService.makeOffer(3, 200.0);
        offerService.cancelOffer(offer.getOfferId());

        assertEquals("canceled", offer.getStatus());
    }
}
