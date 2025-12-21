package Test;

import Model.Brand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    @Test
    void testBrandCreation() {
        Brand brand = new Brand(1, "Nike");

        assertEquals(1, brand.brandId);
        assertEquals("Nike", brand.getName());
    }

    @Test
    void testSetBrandId() {
        Brand brand = new Brand(2, "Adidas");

        brand.setBrandId(5);

        assertEquals(5, brand.brandId);
    }

    @Test
    void testSetName() {
        Brand brand = new Brand(3, "Puma");

        brand.setName("Reebok");

        assertEquals("Reebok", brand.getName());
    }

    @Test
    void testToString() {
        Brand brand = new Brand(4, "Zara");

        assertEquals("Zara", brand.toString());
    }
}
