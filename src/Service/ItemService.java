package Service;

import DAO.ItemDAO;
import Model.Item;
import Model.Enum.Category;
import Model.Enum.Size;

import java.util.List;

public class ItemService {

    private ItemDAO itemDAO = new ItemDAO();

    public Item createListing(String title, String description, double price,
                              Category category, Size size, String brand) {

        if (price <= 0)
            throw new IllegalArgumentException("Price must be positive");

        Item item = itemDAO.create(title, description, price, category, size, brand);
        System.out.println("Item created: " + item.getTitle());
        return item;
    }

    public void updateItem(int id, String title, String description,
                           double price, Category category, Size size, String brand) {

        Item item = itemDAO.findById(id);
        if (item == null)
            throw new IllegalArgumentException("Item not found");

        item.setTitle(title);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setSize(size);
        item.setBrand(brand);

        System.out.println("Item " + id + " updated");
    }

    public void markAsSold(int id) {
        Item item = itemDAO.findById(id);
        if (item != null) {
            item.setSold(true);
            System.out.println("Item " + id + " marked as sold");
        }
    }

    public void deleteListing(int id) {
        itemDAO.delete(id);
        System.out.println("Item " + id + " deleted");
    }

    public List<Item> getAllItems() {
        return itemDAO.findAll();
    }
}
