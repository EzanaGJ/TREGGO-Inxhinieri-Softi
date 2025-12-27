package DAO;

import Model.Address;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    private static final List<Address> addressDatabase = new ArrayList<>();
    private static int idCounter = 1;

    public Address save(int userId, String country, String city, String postalCode, String street) {
        Address address = new Address(idCounter++, userId, country, city, postalCode, street);
        addressDatabase.add(address);
        return address;
    }

    public List<Address> findByUser(int userId) {
        List<Address> result = new ArrayList<>();
        for (Address addr : addressDatabase) {
            if (addr.getUserId() == userId) result.add(addr);
        }
        return result;
    }

    public void delete(int addressId) {
        addressDatabase.removeIf(addr -> addr.getAddressId() == addressId);
    }

    // Important for tests: resets the static list and counter
    public static void clearDatabase() {
        addressDatabase.clear();
        idCounter = 1;
    }
}