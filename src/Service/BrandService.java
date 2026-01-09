package Service;

import DAO.BrandDAO;
import Model.Brand;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandService {

    private final BrandDAO brandDAO;
    private final List<String> actions = new ArrayList<>();

    public BrandService(BrandDAO brandDAO) {
        this.brandDAO = brandDAO;
    }

    public Brand createBrand(String name) throws SQLException {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Brand name cannot be empty");

        Brand brand = new Brand(name);
        brandDAO.create(brand);

        actions.add("CREATE_BRAND:" + brand.getBrandId());
        return brand;
    }

    public Brand getBrandById(int id) throws SQLException {
        actions.add("GET_BRAND:" + id);
        return brandDAO.getById(id);
    }

    public List<Brand> getAllBrands() throws SQLException {
        actions.add("GET_ALL_BRANDS");
        return brandDAO.findAll();
    }

    public Brand updateBrand(Brand brand) throws SQLException {
        if (brand == null)
            throw new IllegalArgumentException("Brand cannot be null");

        brandDAO.update(brand);
        actions.add("UPDATE_BRAND:" + brand.getBrandId());
        return brand;
    }

    public void deleteBrand(int id) throws SQLException {
        brandDAO.delete(id);
        actions.add("DELETE_BRAND:" + id);
    }

    public List<String> getActions() {
        return List.copyOf(actions);
    }
}
