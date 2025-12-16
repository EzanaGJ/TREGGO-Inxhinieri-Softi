package Model;
import java.util.ArrayList;
import java.util.List;

public class Address {
    public int addressId;
    int userId;
    String country;
    public String city;
    String postalCode;
    String street;

    public static List<Address> addressDatabase = new ArrayList<>();
    static int idCounter = 1;

    public Address(int userId, String country, String city, String postalCode, String street) {
        this.addressId = idCounter++;
        this.userId = userId;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }

    public void addAddress() {
        addressDatabase.add(this);
        System.out.println("Address " + addressId + " added for user " + userId);
    }

    public static List<Address> getAddress(int userId) {
        List<Address> result = new ArrayList<>();
        for (Address addr : addressDatabase) {
            if (addr.userId == userId) {
                result.add(addr);
            }
        }
        return result;
    }


    public void deleteAddress() {
        addressDatabase.removeIf(addr -> addr.addressId == this.addressId);
        System.out.println("Address " + addressId + " deleted for user " + userId);
    }
}