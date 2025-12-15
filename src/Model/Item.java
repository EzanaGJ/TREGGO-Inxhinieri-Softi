package Model;

import Model.Enum.Category;
import Model.Enum.Size;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public int itemId;
    public String title;
    String description;
    public double price;
    Category category;
    public Size size;
    String brand;
    public boolean sold;

    public static List<Item> itemDatabase = new ArrayList<>();
    static int idCounter = 1;

    public Item(String title, String description, double price, Category category, Size size, String brand) {
        this.itemId = idCounter++;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.size = size;
        this.brand = brand;
        this.sold = false;
    }

    public void createListing() {
        itemDatabase.add(this);
        System.out.println("Item " + itemId + " (" + title + ") priced $" + price);
    }

    public static Item getItem(int id) {
        return itemDatabase.stream()
                .filter(item -> item.itemId == id)
                .findFirst()
                .orElse(null);
    }

    public void modifyDetails(String newTitle, String newDescription, double newPrice, Category newCategory, Size newSize, String newBrand) {
        this.title = newTitle;
        this.description = newDescription;
        this.price = newPrice;
        this.category = newCategory;
        this.size = newSize;
        this.brand = newBrand;
        System.out.println("Item " + itemId + " details updated.");
    }

    public void deleteListing() {
        itemDatabase.removeIf(item -> item.itemId == this.itemId);
        System.out.println("Item " + itemId + " deleted.");
    }

    Category getCategory() {
        return category;
    }

    Size getSize() {
        return size;
    }

    String getBrand() {
        return brand;
    }

    public void markAsSold() {
        this.sold = true;
        System.out.println("Item " + itemId + " marked as sold.");
    }
    static void listAllItems() {
        System.out.println("All Items:");
        for (Item item : itemDatabase) {
            System.out.println(" - " + item.itemId + ": " + item.title + " $" + item.price + (item.sold ? " (sold)" : ""));
        }
    }
}