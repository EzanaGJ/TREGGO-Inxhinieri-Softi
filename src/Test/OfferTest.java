package Test;

import Model.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferTest {

    @BeforeEach
    void setUp() {
        Offer.offerDatabase.clear();
        Offer.idCounter = 1;
    }

    @Test
    void testMakeOffer() {
        Offer offer = new Offer(1, 100.0);
        offer.makeOffer();

        assertEquals(1, Offer.offerDatabase.size());
        assertEquals(100.0, Offer.offerDatabase.get(0).offeredPrice);
        assertEquals("pending", Offer.offerDatabase.get(0).status);
    }

    @Test
    void testModifyOfferWhenPending() {
        Offer offer = new Offer(2, 80.0);
        offer.makeOffer();

        offer.modifyOffer(90.0);

        assertEquals(90.0, offer.offeredPrice);
        assertEquals("pending", offer.status);
    }

    @Test
    void testAcceptOffer() {
        Offer offer = new Offer(3, 150.0);
        offer.makeOffer();

        offer.acceptOffer();

        assertEquals("accepted", offer.getStatus());
    }

    @Test
    void testDeclineOffer() {
        Offer offer = new Offer(4, 60.0);
        offer.makeOffer();

        offer.declineOffer();

        assertEquals("declined", offer.getStatus());
    }

    @Test
    void testCancelOffer() {
        Offer offer = new Offer(5, 40.0);
        offer.makeOffer();

        offer.cancelOffer();

        assertEquals("canceled", offer.getStatus());
    }

    @Test
    void testCannotModifyAfterAccepted() {
        Offer offer = new Offer(6, 200.0);
        offer.makeOffer();
        offer.acceptOffer();

        offer.modifyOffer(220.0);

        assertEquals(200.0, offer.offeredPrice);
        assertEquals("accepted", offer.status);
    }

    @Test
    void testUpdatePriceUsesModifyOffer() {
        Offer offer = new Offer(7, 70.0);
        offer.makeOffer();

        offer.updatePrice(75.0);

        assertEquals(75.0, offer.offeredPrice);
    }
}