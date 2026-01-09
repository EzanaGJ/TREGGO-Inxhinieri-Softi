package Test;

import DAO.BrandDAO;
import DAO.JdbcBrandDAO;
import Model.Brand;
import Service.BrandService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BrandServiceTest {

    private BrandService brandService;

    @BeforeEach
    void setUp() {
        BrandDAO brandDAO = new JdbcBrandDAO();
        brandService = new BrandService(brandDAO);
    }

    @Test
    void testCreateBrand() throws SQLException {
        Brand brand = brandService.createBrand("Nike");
        assertTrue(brand.getBrandId() > 0);
    }

    @Test
    void testGetBrandById() throws SQLException {
        Brand brand = brandService.createBrand("Adidas");
        Brand fetched = brandService.getBrandById(brand.getBrandId());

        assertNotNull(fetched);
        assertEquals("Adidas", fetched.getName());
    }

    @Test
    void testGetAllBrands() throws SQLException {
        brandService.createBrand("Puma");
        List<Brand> brands = brandService.getAllBrands();

        assertFalse(brands.isEmpty());
    }

    @Test
    void testUpdateBrand() throws SQLException {
        Brand brand = brandService.createBrand("OldName");
        brand.setName("NewName");

        brandService.updateBrand(brand);
        Brand updated = brandService.getBrandById(brand.getBrandId());

        assertEquals("NewName", updated.getName());
    }

    @Test
    void testDeleteBrand() throws SQLException {
        Brand brand = brandService.createBrand("TempBrand");
        brandService.deleteBrand(brand.getBrandId());

        Brand deleted = brandService.getBrandById(brand.getBrandId());
        assertNull(deleted);
    }
}
