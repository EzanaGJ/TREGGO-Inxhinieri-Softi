package Model;

import java.sql.Timestamp;

public class FavoriteItem {
    private int userId;
    private int itemId;
    private Timestamp createdAt;

    public FavoriteItem(int userId, int itemId, Timestamp createdAt) {
        this.userId = userId;
        this.itemId = itemId;
        this.createdAt = createdAt;
    }

    public FavoriteItem(int userId, int itemId) {
        this(userId, itemId, null);
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
