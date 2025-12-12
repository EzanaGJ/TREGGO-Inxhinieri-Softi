package Model;

import java.util.ArrayList;
import java.util.List;

class Item {
    int itemId;
    String title;
    String description;
    double price;
    Category category;
    Size size;
    String brand;
    boolean sold;

    static List<Item> itemDatabase = new ArrayList<>();
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

    void createListing() {
        itemDatabase.add(this);
        System.out.println("Item " + itemId + " (" + title + ") listed for $" + price);
    }

    static Item getItem(int id) {
        return itemDatabase.stream()
                .filter(item -> item.itemId == id)
                .findFirst()
                .orElse(null);
    }

    void modifyDetails(String newTitle, String newDescription, double newPrice, Category newCategory, Size newSize, String newBrand) {
        this.title = newTitle;
        this.description = newDescription;
        this.price = newPrice;
        this.category = newCategory;
        this.size = newSize;
        this.brand = newBrand;
        System.out.println("Item " + itemId + " details updated.");
    }

    void deleteListing() {
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

    void markAsSold() {
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