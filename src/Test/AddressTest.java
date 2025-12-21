package Test;

import DAO.AddressDAO;
import Model.Address;
import Service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceTest {

    private AddressService addressService;

    @BeforeEach
    void setUp() {
        AddressDAO.clearDatabase();
        addressService = new AddressService();
    }

    @Test
    void testAddAddress() {
        Address address = addressService.addAddress(1, "Albania", "Tirane", "1001", "Rruga Kavajes");
        List<Address> addresses = addressService.getUserAddresses(1);

        assertEquals(1, addresses.size());
    }

    @Test
    void testGetUserAddresses() {
        addressService.addAddress(1, "Albania", "Tirane", "1001", "Rruga Kavajes");
        addressService.addAddress(1, "Albania", "Durres", "1002", "Rruga kryesore");

        List<Address> addresses = addressService.getUserAddresses(1);
        assertEquals(2, addresses.size());
    }

    @Test
    void testDeleteAddress() {
        Address address = addressService.addAddress(1, "Albania", "Tirane", "1001", "Rruga Kavajes");
        addressService.deleteAddress(address.getAddressId());

        List<Address> addresses = addressService.getUserAddresses(1);
        assertEquals(0, addresses.size());
    }
}
