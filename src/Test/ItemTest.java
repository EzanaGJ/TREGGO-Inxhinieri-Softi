package Test;
import Model.Item;

import Model.Enum.Category;
import Model.Enum.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @BeforeEach
    void setUp() {
        Item.itemDatabase.clear();
    }

    @Test
    void testCreateListing() {
        Item item = new Item(
                "Jacket",
                "Winter jacket",
                50.0,
                Category.MEN,
                Size.M,
                "Nike"
        );

        item.createListing();

        assertEquals(1, Item.itemDatabase.size());
        assertEquals("Jacket", Item.itemDatabase.get(0).title);
    }

    @Test
    void testGetItemById() {
        Item item = new Item(
                "Shoes",
                "Running shoes",
                80.0,
                Category.MEN,
                Size.L,
                "Adidas"
        );
        item.createListing();

        Item found = Item.getItem(item.itemId);

        assertNotNull(found);
        assertEquals("Shoes", found.title);
    }

    @Test
    void testModifyDetails() {
        Item item = new Item(
                "T-Shirt",
                "Cotton",
                20.0,
                Category.WOMEN,
                Size.S,
                "Zara"
        );
        item.createListing();

        item.modifyDetails(
                "Hoodie",
                "Warm hoodie",
                35.0,
                Category.WOMEN,
                Size.M,
                "H&M"
        );

        assertEquals("Hoodie", item.title);
        assertEquals(35.0, item.price);
        assertEquals(Size.M, item.size);
    }

    @Test
    void testDeleteListing() {
        Item item = new Item(
                "Hat",
                "Summer hat",
                15.0,
                Category.KIDS,
                Size.S,
                "Puma"
        );
        item.createListing();
        item.deleteListing();

        assertTrue(Item.itemDatabase.isEmpty());
    }

    @Test
    void testMarkAsSold() {
        Item item = new Item(
                "Bag",
                "Leather bag",
                120.0,
                Category.WOMEN,
                Size.M,
                "Gucci"
        );
        item.markAsSold();

        assertTrue(item.sold);
    }
}
