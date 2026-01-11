package Service;

import DAO.BrandDAO;
import Model.Brand;

import java.sql.SQLException;
import java.util.List;

public class BrandService {
    private final BrandDAO brandDAO;

    public BrandService(BrandDAO brandDAO) {
        this.brandDAO = brandDAO;
    }

    public Brand createBrand(String name) throws SQLException {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Brand name cannot be empty");

        Brand existing = brandDAO.getBrandByName(name);
        if (existing != null)
            throw new IllegalArgumentException("Brand already exists");

        return brandDAO.create(new Brand(name));
    }

    public Brand getBrandById(int id) throws SQLException {
        return brandDAO.getBrandById(id);
    }

    public Brand updateBrand(int id, String newName) throws SQLException {
        Brand brand = brandDAO.getBrandById(id);
        if (brand == null)
            throw new IllegalArgumentException("Brand not found");

        if (newName != null && !newName.isBlank())
            brand.setName(newName);

        return brandDAO.update(brand);
    }

    public void deleteBrand(int id) throws SQLException {
        brandDAO.delete(id);
    }

    public List<Brand> getAllBrands() throws SQLException {
        return brandDAO.getAllBrands();
    }
}