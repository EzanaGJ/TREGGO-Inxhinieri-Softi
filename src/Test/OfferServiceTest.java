package Test;

import DAO.JdbcOfferDAO;
import Model.Offer;
import Service.OfferService;
import db.DatabaseManager;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class OfferServiceTest {

    private OfferService service;
    private int userId;
    private int itemId;

    @BeforeEach
    void setup() throws SQLException {
        service = new OfferService(new JdbcOfferDAO());

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            // Create user
            stmt.executeUpdate(
                    "INSERT INTO user(name,password,email) VALUES('OfferUser','123','offer@example.com')",
                    Statement.RETURN_GENERATED_KEYS
            );
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            userId = rs.getInt(1);

            // Create item (FK requirement)
            stmt.executeUpdate(
                    "INSERT INTO item(user_id, title, price) VALUES(" + userId + ", 'Item1', 50)",
                    Statement.RETURN_GENERATED_KEYS
            );
            rs = stmt.getGeneratedKeys();
            rs.next();
            itemId = rs.getInt(1);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM offer");
            stmt.executeUpdate("DELETE FROM item");
            stmt.executeUpdate("DELETE FROM user WHERE email='offer@example.com'");
        }
    }

    @Test
    void testCreateOfferSuccess() throws SQLException {
        Offer o = new Offer(0, userId, itemId, 99.99, "pending");
        Offer created = service.createOffer(o);

        assertNotNull(created);
        assertNotEquals(0, created.getOfferId());
    }

    @Test
    void testCreateOfferNull() {
        assertThrows(IllegalArgumentException.class, () -> service.createOffer(null));
    }

    @Test
    void testCreateOfferInvalidPrice() {
        Offer o = new Offer(0, userId, itemId, -10, "pending");
        assertThrows(IllegalArgumentException.class, () -> service.createOffer(o));
    }

    @Test
    void testGetOfferById() throws SQLException {
        Offer o = service.createOffer(new Offer(0, userId, itemId, 20, "pending"));
        Offer found = service.getOfferById(o.getOfferId());

        assertNotNull(found);
        assertEquals(o.getOfferId(), found.getOfferId());
    }

    @Test
    void testGetOfferNotFound() throws SQLException {
        assertNull(service.getOfferById(999999));
    }

    @Test
    void testUpdateOffer() throws SQLException {
        Offer o = service.createOffer(new Offer(0, userId, itemId, 10, "pending"));
        o.setOfferedPrice(50);
        o.setStatus("accepted");

        Offer updated = service.updateOffer(o);

        assertEquals(50, updated.getOfferedPrice());
        assertEquals("accepted", updated.getStatus());
    }

    @Test
    void testDeleteOffer() throws SQLException {
        Offer o = service.createOffer(new Offer(0, userId, itemId, 15, "pending"));
        service.deleteOffer(o.getOfferId());

        assertNull(service.getOfferById(o.getOfferId()));
    }
}
