package Service;

import DAO.AddressDAO;
import Model.Address;
import java.util.List;

public class AddressService {

    private final AddressDAO addressDAO = new AddressDAO();

    public Address addAddress(int userId, String country, String city, String postalCode, String street) {
        Address address = addressDAO.save(userId, country, city, postalCode, street);
        System.out.println("Address " + address.getAddressId() + " added for user " + userId);
        return address;
    }

    public List<Address> getUserAddresses(int userId) {
        return addressDAO.findByUser(userId);
    }

    public void deleteAddress(int addressId) {
        addressDAO.delete(addressId);
        System.out.println("Address " + addressId + " deleted");
    }
}
