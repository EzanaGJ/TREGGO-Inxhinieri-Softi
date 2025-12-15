package DAO;

import Model.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private static List<Item> itemDatabase = new ArrayList<>();
    private static int idCounter = 1;

    public static void clearDatabase() {
    }

    public Item save(Item item) {
        itemDatabase.add(item);
        return item;
    }

    public Item create(String title, String description, double price,
                       Model.Enum.Category category, Model.Enum.Size size, String brand) {
        Item item = new Item(idCounter++, title, description, price, category, size, brand);
        itemDatabase.add(item);
        return item;
    }

    public Item findById(int id) {
        return itemDatabase.stream()
                .filter(item -> item.getItemId() == id)
                .findFirst()
                .orElse(null);
    }

    public void delete(int id) {
        itemDatabase.removeIf(item -> item.getItemId() == id);
    }

    public List<Item> findAll() {
        return itemDatabase;
    }
}
