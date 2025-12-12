package Model;
import java.util.ArrayList;
import java.util.List;

class Address {
    int addressId;
    int userId;
    String country;
    String city;
    String postalCode;
    String street;

    static List<Address> addressDatabase = new ArrayList<>();
    static int idCounter = 1;

    public Address(int userId, String country, String city, String postalCode, String street) {
        this.addressId = idCounter++;
        this.userId = userId;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }

    void addAddress() {
        addressDatabase.add(this);
        System.out.println("Address " + addressId + " added for user " + userId);
    }

    static List<Address> getAddress(int userId) {
        List<Address> result = new ArrayList<>();
        for (Address addr : addressDatabase) {
            if (addr.userId == userId) {
                result.add(addr);
            }
        }
        return result;
    }


    void deleteAddress() {
        addressDatabase.removeIf(addr -> addr.addressId == this.addressId);
        System.out.println("Address " + addressId + " deleted for user " + userId);
    }
}