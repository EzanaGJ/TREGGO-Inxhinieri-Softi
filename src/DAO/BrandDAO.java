package DAO;

import Model.Brand;
import java.sql.SQLException;
import java.util.List;

public interface BrandDAO {

    Brand create(Brand brand) throws SQLException;

    Brand getBrandById(int id) throws SQLException;

    List<Brand> getAllBrands() throws SQLException;

    Brand update(Brand brand) throws SQLException;

    void delete(int id) throws SQLException;
}
