package Service;

import DAO.AddressDAO;
import Model.Address;

import java.sql.SQLException;
import java.util.List;

public class AddressService {

    private final AddressDAO addressDAO;

    public AddressService(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public Address addAddress(int userId, String city, String postalCode, String country, String street) throws SQLException {
        if (userId <= 0 || city == null || city.isEmpty()) {
            throw new IllegalArgumentException("Invalid user ID or city");
        }
        Address address = new Address(0, userId, city, postalCode, country, street);
        return addressDAO.create(address);
    }

    public Address getAddressById(int id) throws SQLException {
        return addressDAO.getAddressById(id);
    }

    public List<Address> getAddressesByUser(int userId) throws SQLException {
        return addressDAO.getAddressesByUser(userId);
    }

    public Address updateAddress(int addressId, String city, String postalCode, String country, String street) throws SQLException {
        Address existing = addressDAO.getAddressById(addressId);
        if (existing == null) return null;

        if (city != null && !city.isEmpty()) existing.setCity(city);
        if (postalCode != null && !postalCode.isEmpty()) existing.setPostalCode(postalCode);
        if (country != null && !country.isEmpty()) existing.setCountry(country);
        if (street != null && !street.isEmpty()) existing.setStreet(street);

        return addressDAO.update(existing);
    }

    public void deleteAddress(int id) throws SQLException {
        addressDAO.delete(id);
    }
}
