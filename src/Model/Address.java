package Model;
class Address {
    final int addressId;
    final int userId;
    String country, city, postalCode, street;
    Address(int id, int userId, String country, String city, String postalCode, String street){
        this.addressId = id;
        this.userId = userId;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }
    @Override public String toString(){ return street + ", " + city + " (" + postalCode + "), " + country; }
}