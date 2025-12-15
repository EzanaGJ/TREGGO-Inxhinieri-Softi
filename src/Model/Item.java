package Model;

import Model.Enum.Category;
import Model.Enum.Size;

public class Item {
    private int itemId;
    private String title;
    private String description;
    private double price;
    private Category category;
    private Size size;
    private String brand;
    private boolean sold;

    public Item(int itemId, String title, String description,
                double price, Category category, Size size, String brand) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.size = size;
        this.brand = brand;
        this.sold = false;
    }

    // Getters & Setters
    public int getItemId() { return itemId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }
    public Size getSize() { return size; }
    public String getBrand() { return brand; }
    public boolean isSold() { return sold; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(Category category) { this.category = category; }
    public void setSize(Size size) { this.size = size; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setSold(boolean sold) { this.sold = sold; }
}
