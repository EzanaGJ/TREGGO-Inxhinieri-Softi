package DAO;

import Model.Address;
import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAddressDAO implements AddressDAO {

    @Override
    public Address create(Address address) throws SQLException {
        String sql = "INSERT INTO addresses (user_id, city, postal_code, country, street) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getPostalCode());
            ps.setString(4, address.getCountry());
            ps.setString(5, address.getStreet());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    address.setAddressId(rs.getInt(1));
                }
            }
        }

        return address;
    }

    @Override
    public Address getAddressById(int id) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE address_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Address(
                            rs.getInt("address_id"),
                            rs.getInt("user_id"),
                            rs.getString("city"),
                            rs.getString("postal_code"),
                            rs.getString("country"),
                            rs.getString("street")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Address> getAddressesByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE user_id = ?";
        List<Address> list = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Address(
                            rs.getInt("address_id"),
                            rs.getInt("user_id"),
                            rs.getString("city"),
                            rs.getString("postal_code"),
                            rs.getString("country"),
                            rs.getString("street")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public Address update(Address address) throws SQLException {
        String sql = "UPDATE addresses SET city = ?, postal_code = ?, country = ?, street = ? WHERE address_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, address.getCity());
            ps.setString(2, address.getPostalCode());
            ps.setString(3, address.getCountry());
            ps.setString(4, address.getStreet());
            ps.setInt(5, address.getAddressId());
            ps.executeUpdate();
        }

        return address;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM addresses WHERE address_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
