package DAO;

import Model.Item;
import java.util.List;
import java.util.Optional;

public interface ItemDAO {
    void addItem(Item item);
    Optional<Item> getItemById(int itemId);
    List<Item> getAllItems();
    boolean updateItem(Item item);
    boolean deleteItem(int itemId);
    boolean markAsSold(int itemId);
    List<Item> getItemsByUserId(int userId);
    List<Item> getItemsByStatus(String status);
}
