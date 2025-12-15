package Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    @Test
    void testConstructor() {
        Brand brand = new Brand(1, "Nike");

        assertEquals(1, brand.brandId);
        assertEquals("Nike", brand.getName());
    }

    @Test
    void testSetBrandId() {
        Brand brand = new Brand(1, "Adidas");

        brand.setBrandId(2);

        assertEquals(2, brand.brandId);
    }

    @Test
    void testSetName() {
        Brand brand = new Brand(1, "Puma");

        brand.setName("Reebok");

        assertEquals("Reebok", brand.getName());
    }

    @Test
    void testGetName() {
        Brand brand = new Brand(1, "Under Armour");

        String name = brand.getName();

        assertEquals("Under Armour", name);
    }

    @Test
    void testToString() {
        Brand brand = new Brand(1, "Fila");

        assertEquals("Fila", brand.toString());
    }
}
