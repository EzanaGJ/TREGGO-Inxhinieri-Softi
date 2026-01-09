package Service;

import DAO.ItemDAO;
import Model.Item;
import Model.Enum.ItemStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemService {

    private final ItemDAO itemDAO;

    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void createItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        itemDAO.addItem(item);
    }

    public Optional<Item> getItemById(int itemId) {
        return itemDAO.getItemById(itemId);
    }

    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public boolean updateItem(Item item) {
        if (item == null || item.getItemId() <= 0) throw new IllegalArgumentException("Invalid item");
        return itemDAO.updateItem(item);
    }

    public boolean deleteItem(int itemId) {
        return itemDAO.deleteItem(itemId);
    }

    public boolean markItemAsSold(int itemId) {
        return itemDAO.markAsSold(itemId);
    }

    public List<Item> getItemsByUser(int userId) {
        return itemDAO.getItemsByUserId(userId);
    }

    public List<Item> getItemsByStrategy(ItemFilterStrategy strategy) {
        return itemDAO.getAllItems().stream()
                .filter(strategy::filter)
                .collect(Collectors.toList());
    }

    public List<Item> getItemsByStatus(ItemStatus status) {
        return getItemsByStrategy(new StatusFilterStrategy(status));
    }
}
