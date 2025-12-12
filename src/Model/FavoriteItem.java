package Model;

import java.util.ArrayList;
import java.util.List;

class FavoriteItem {
    int favoriteId;
    int userId;
    int itemId;

    static List<FavoriteItem> favoriteDatabase = new ArrayList<>();
    static int idCounter = 1;

    public FavoriteItem(int userId, int itemId) {
        this.favoriteId = idCounter++;
        this.userId = userId;
        this.itemId = itemId;
    }

    void markAsFavorite() {
        favoriteDatabase.add(this);
        System.out.println("Item " + itemId + " marked as favorite by user " + userId);
    }

    void removeFavorite() {
        favoriteDatabase.removeIf(fav -> fav.userId == this.userId && fav.itemId == this.itemId);
        System.out.println("Item " + itemId + " removed from favorites for user " + userId);
    }

    boolean isFavorite() {
        return favoriteDatabase.stream()
                .anyMatch(fav -> fav.userId == this.userId && fav.itemId == this.itemId);
    }

    static void clearFavorites(int userId) {
        favoriteDatabase.removeIf(fav -> fav.userId == userId);
        System.out.println("All favorites cleared for user " + userId);
    }

    static void listFavorites(int userId) {
        System.out.println("Favorites for user " + userId + ":");
        favoriteDatabase.stream()
                .filter(fav -> fav.userId == userId)
                .forEach(fav -> System.out.println(" - Item " + fav.itemId));
    }
}
