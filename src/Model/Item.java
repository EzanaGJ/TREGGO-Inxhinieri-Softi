package Model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Item {
    private int itemId;
    private int userId;
    private Integer categoryId;
    private Integer brandId;
    private String title;
    private String description;
    private BigDecimal price;
    private String size;
    private String condition;
    private String image;
    private String status;
    private Timestamp createdAt;

    // No-arg constructor
    public Item() {}

    // Parameterized constructor
    public Item(Integer itemId, int userId, Integer categoryId, Integer brandId,
                String title, String description, BigDecimal price, String size,
                String condition, String image, String status, Timestamp createdAt) {
        this.itemId = itemId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.size = size;
        this.condition = condition;
        this.image = image;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters
    public int getItemId() { return itemId; }
    public int getUserId() { return userId; }
    public Integer getCategoryId() { return categoryId; }
    public Integer getBrandId() { return brandId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getSize() { return size; }
    public String getCondition() { return condition; }
    public String getImage() { return image; }
    public String getStatus() { return status; }
    public Timestamp getCreatedAt() { return createdAt; }

    // Setters
    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public void setBrandId(Integer brandId) { this.brandId = brandId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setSize(String size) { this.size = size; }
    public void setCondition(String condition) { this.condition = condition; }
    public void setImage(String image) { this.image = image; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
