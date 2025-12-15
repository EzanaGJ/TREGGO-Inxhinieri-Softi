package Test;

import DAO.ItemDAO;
import Model.Enum.Category;
import Model.Enum.Size;
import Model.Item;
import Service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        ItemDAO.clearDatabase();
        itemService = new ItemService();
    }

    @Test
    void testDeleteListing() {
        Item item = itemService.createListing(
                "Hat",
                "Nice hat",
                15,
                Category.OTHER,
                Size.S,
                "H&M"
        );

        itemService.deleteListing(item.getItemId());

        assertEquals(0, itemService.getAllItems().size());
    }
}
