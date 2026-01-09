package DAO;

import Model.Brand;

import java.sql.SQLException;
import java.util.List;

public interface BrandDAO {

    Brand create(Brand brand) throws SQLException;

    Brand getById(int id) throws SQLException;

    List<Brand> findAll() throws SQLException;

    Brand update(Brand brand) throws SQLException;

    boolean delete(int id) throws SQLException;
}
