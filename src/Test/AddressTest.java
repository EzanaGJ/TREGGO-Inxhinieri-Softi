package Test;
import Model.Address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @BeforeEach
    void setUp() {
        Address.addressDatabase.clear();
    }

    @Test
    void testAddAddress() {
        Address address = new Address(
                1,
                "Albania",
                "Tirana",
                "1001",
                "Rruga e Kavajes"
        );

        address.addAddress();

        assertEquals(1, Address.addressDatabase.size());
        assertEquals("Tirana", Address.addressDatabase.get(0).city);
    }

    @Test
    void testGetAddressByUserId() {
        Address a1 = new Address(1, "Albania", "Tirana", "1001", "Street 1");
        Address a2 = new Address(1, "Albania", "Durres", "2001", "Street 2");
        Address a3 = new Address(2, "Albania", "Vlore", "3001", "Street 3");

        a1.addAddress();
        a2.addAddress();
        a3.addAddress();

        List<Address> user1Addresses = Address.getAddress(1);

        assertEquals(2, user1Addresses.size());
    }

    @Test
    void testDeleteAddress() {
        Address address = new Address(
                1,
                "Albania",
                "Shkoder",
                "4001",
                "Pedonalja"
        );

        address.addAddress();
        address.deleteAddress();

        assertTrue(Address.addressDatabase.isEmpty());
    }
}
