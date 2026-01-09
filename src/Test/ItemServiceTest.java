package Test;

import DAO.ItemDAOImpl;
import Model.Item;
import Model.Enum.ItemStatus;
import Service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        itemService = new ItemService(itemDAO);
    }

    @Test
    void testCreateItem() {
        Item item = new Item();
        item.setItemId(1);
        item.setUserId(100);
        item.setTitle("Test Item");
        item.setPrice(new BigDecimal("50.00"));
        item.setStatus(ItemStatus.AVAILABLE.name());
        item.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        itemService.createItem(item);

        Optional<Item> result = itemService.getItemById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getItemId());
        assertEquals(ItemStatus.AVAILABLE.name(), result.get().getStatus());
    }

    @Test
    void testGetAllItems() {
        Item item1 = new Item(); item1.setItemId(1); item1.setStatus(ItemStatus.AVAILABLE.name());
        Item item2 = new Item(); item2.setItemId(2); item2.setStatus(ItemStatus.SOLD.name());
        itemService.createItem(item1);
        itemService.createItem(item2);

        List<Item> allItems = itemService.getAllItems();
        assertEquals(2, allItems.size());
    }

    @Test
    void testGetItemsByStatus() {
        Item item1 = new Item(); item1.setItemId(1); item1.setStatus(ItemStatus.AVAILABLE.name());
        Item item2 = new Item(); item2.setItemId(2); item2.setStatus(ItemStatus.SOLD.name());
        itemService.createItem(item1);
        itemService.createItem(item2);

        List<Item> availableItems = itemService.getItemsByStatus(ItemStatus.AVAILABLE);
        assertEquals(1, availableItems.size());
        assertEquals(ItemStatus.AVAILABLE.name(), availableItems.get(0).getStatus());
    }

    @Test
    void testUpdateItemThrowsExceptionForInvalidItem() {
        Item invalidItem = new Item(); // no ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> itemService.updateItem(invalidItem));
        assertEquals("Invalid item", exception.getMessage());
    }

    @Test
    void testDeleteItem() {
        Item item = new Item(); item.setItemId(1); item.setStatus(ItemStatus.AVAILABLE.name());
        itemService.createItem(item);

        boolean deleted = itemService.deleteItem(1);
        assertTrue(deleted);
        assertFalse(itemService.getItemById(1).isPresent());
    }

    @Test
    void testMarkItemAsSold() {
        Item item = new Item(); item.setItemId(1); item.setStatus(ItemStatus.AVAILABLE.name());
        itemService.createItem(item);

        boolean marked = itemService.markItemAsSold(1);
        assertTrue(marked);

        Optional<Item> updated = itemService.getItemById(1);
        assertTrue(updated.isPresent());
        assertEquals(ItemStatus.SOLD.name(), updated.get().getStatus());
    }

    @Test
    void testGetItemsByUser() {
        Item item1 = new Item(); item1.setItemId(1); item1.setUserId(100);
        Item item2 = new Item(); item2.setItemId(2); item2.setUserId(200);
        Item item3 = new Item(); item3.setItemId(3); item3.setUserId(100);

        itemService.createItem(item1);
        itemService.createItem(item2);
        itemService.createItem(item3);

        List<Item> user100Items = itemService.getItemsByUser(100);
        assertEquals(2, user100Items.size());
        for (Item item : user100Items) {
            assertEquals(100, item.getUserId());
        }
    }
}
