package DAO;

import Model.Brand;
import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBrandDAO implements BrandDAO {

    @Override
    public Brand create(Brand brand) throws SQLException {
        String sql = "INSERT INTO brand (name) VALUES (?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, brand.getName());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    brand.setBrandId(rs.getInt(1));
                }
            }
        }

        return brand;
    }

    @Override
    public Brand getBrandById(int id) throws SQLException {
        String sql = "SELECT brand_id, name FROM brand WHERE brand_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Brand(
                            rs.getInt("brand_id"),
                            rs.getString("name")
                    );
                }
            }
        }

        return null;
    }

    @Override
    public List<Brand> getAllBrands() throws SQLException {
        String sql = "SELECT brand_id, name FROM brand";
        List<Brand> brands = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                brands.add(new Brand(
                        rs.getInt("brand_id"),
                        rs.getString("name")
                ));
            }
        }

        return brands;
    }

    @Override
    public Brand update(Brand brand) throws SQLException {
        String sql = "UPDATE brand SET name = ? WHERE brand_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, brand.getName());
            ps.setInt(2, brand.getBrandId());
            ps.executeUpdate();
        }

        return brand;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM brand WHERE brand_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}





