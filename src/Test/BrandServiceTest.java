package Test;

import DAO.JdbcBrandDAO;
import Model.Brand;
import Service.BrandService;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BrandServiceTest {

    private BrandService brandService;

    @BeforeEach
    void setUp() throws SQLException {
        brandService = new BrandService(new JdbcBrandDAO());
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM brand WHERE name LIKE 'Test_%'");
        }
    }

    @Test
    void testCreateAndFindBrand() throws SQLException {
        Brand b = brandService.createBrand("Test_Brand1");

        assertNotEquals(0, b.getBrandId());

        Brand found = brandService.getBrandById(b.getBrandId());
        assertNotNull(found);
        assertEquals("Test_Brand1", found.getName());
    }

    @Test
    void testCreateBrandEmptyName() {
        try {
            brandService.createBrand("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException | SQLException e) {
        }
    }

    @Test
    void testUpdateBrand() throws SQLException {
        Brand b = brandService.createBrand("Test_Brand2");

        brandService.updateBrand(b.getBrandId(), "Test_Brand2_Updated");

        Brand updated = brandService.getBrandById(b.getBrandId());
        assertEquals("Test_Brand2_Updated", updated.getName());
    }

    @Test
    void testDeleteBrand() throws SQLException {
        Brand b = brandService.createBrand("Test_Brand3");

        brandService.deleteBrand(b.getBrandId());

        Brand found = brandService.getBrandById(b.getBrandId());
        assertNull(found);
    }

    @Test
    void testGetAllBrands() throws SQLException {
        brandService.createBrand("Test_Brand4");
        brandService.createBrand("Test_Brand5");

        List<Brand> brands = brandService.getAllBrands();

        assertTrue(brands.stream().anyMatch(b -> b.getName().equals("Test_Brand4")));
        assertTrue(brands.stream().anyMatch(b -> b.getName().equals("Test_Brand5")));
    }
}