package DAO;

import Model.Address;
import java.sql.SQLException;
import java.util.List;

public interface AddressDAO {
    Address create(Address address) throws SQLException;
    Address getAddressById(int id) throws SQLException;
    List<Address> getAddressesByUser(int userId) throws SQLException;
    Address update(Address address) throws SQLException;
    void delete(int id) throws SQLException;
}
