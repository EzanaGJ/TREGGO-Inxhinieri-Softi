package DAO;

import Model.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    private final List<Item> items = new ArrayList<>();

    @Override
    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public Optional<Item> getItemById(int itemId) {
        return items.stream().filter(i -> i.getItemId() == itemId).findFirst();
    }

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    @Override
    public boolean updateItem(Item item) {
        Optional<Item> existing = getItemById(item.getItemId());
        if (existing.isPresent()) {
            items.remove(existing.get());
            items.add(item);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteItem(int itemId) {
        Optional<Item> existing = getItemById(itemId);
        if (existing.isPresent()) {
            items.remove(existing.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean markAsSold(int itemId) {
        Optional<Item> existing = getItemById(itemId);
        if (existing.isPresent()) {
            existing.get().setStatus("SOLD");
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getItemsByUserId(int userId) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getUserId() == userId) result.add(item);
        }
        return result;
    }

    @Override
    public List<Item> getItemsByStatus(String status) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (status.equals(item.getStatus())) result.add(item);
        }
        return result;
    }
}
