package Model;

public class Address {
    private int addressId;
    private int userId;
    private String country;
    public String city;
    private String postalCode;
    private String street;

    // Constructor used by DAO
    public Address(int addressId, int userId, String country, String city, String postalCode, String street) {
        this.addressId = addressId;
        this.userId = userId;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }

    // Getters
    public int getAddressId() { return addressId; }
    public int getUserId() { return userId; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getStreet() { return street; }

    // Setters
    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setStreet(String street) { this.street = street; }
}
