package Test;

import DAO.JdbcAddressDAO;
import Service.AddressService;
import Model.Address;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceTest {

    private AddressService addressService;

    @BeforeEach
    void setUp() throws SQLException {
        addressService = new AddressService(new JdbcAddressDAO());

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM addresses");
            stmt.executeUpdate("DELETE FROM user WHERE email LIKE 'test_user@example.com'");

            stmt.executeUpdate("INSERT INTO user (user_id, name, password, role_type, email) " +
                    "VALUES (1, 'TestUser', 'pass', 'USER', 'test_user@example.com')");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM addresses");
            stmt.executeUpdate("DELETE FROM user WHERE email LIKE 'test_user@example.com'");
        }
    }

    @Test
    void testAddAndGetAddress() throws SQLException {
        Address a = addressService.addAddress(1, "Berlin", "10115", "Germany", "Test Street 1");
        assertNotNull(a);

        Address found = addressService.getAddressById(a.getAddressId());
        assertNotNull(found);
        assertEquals(1, found.getUserId());
        assertEquals("Berlin", found.getCity());
    }

    @Test
    void testGetAddressesByUser() throws SQLException {
        addressService.addAddress(1, "Berlin", "10115", "Germany", "Street 1");
        addressService.addAddress(1, "Munich", "80331", "Germany", "Street 2");

        List<Address> addresses = addressService.getAddressesByUser(1);
        assertEquals(2, addresses.size());
    }

    @Test
    void testUpdateAddress() throws SQLException {
        Address a = addressService.addAddress(1, "Berlin", "10115", "Germany", "Street 1");
        addressService.updateAddress(a.getAddressId(), "Hamburg", "20095", "Germany", "New Street");

        Address updated = addressService.getAddressById(a.getAddressId());
        assertEquals("Hamburg", updated.getCity());
        assertEquals("20095", updated.getPostalCode());
        assertEquals("New Street", updated.getStreet());
    }

    @Test
    void testDeleteAddress() throws SQLException {
        Address a = addressService.addAddress(1, "Berlin", "10115", "Germany", "Street 1");
        addressService.deleteAddress(a.getAddressId());

        Address found = addressService.getAddressById(a.getAddressId());
        assertNull(found);
    }
}
