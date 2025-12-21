package Model;

public class FavoriteItem {

    private int favoriteId;
    private int userId;
    private int itemId;

    public FavoriteItem(int favoriteId, int userId, int itemId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.itemId = itemId;
    }

    public int getFavoriteId() { return favoriteId; }
    public int getUserId() { return userId; }
    public int getItemId() { return itemId; }
}
