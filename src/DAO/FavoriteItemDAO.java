package DAO;

import Model.FavoriteItem;

import java.util.ArrayList;
import java.util.List;

public class FavoriteItemDAO {

    private static final List<FavoriteItem> favoriteDatabase = new ArrayList<>();
    private static int idCounter = 1;

    public FavoriteItem save(int userId, int itemId) {
        FavoriteItem favorite = new FavoriteItem(idCounter++, userId, itemId);
        favoriteDatabase.add(favorite);
        return favorite;
    }

    public void delete(int userId, int itemId) {
        favoriteDatabase.removeIf(
                fav -> fav.getUserId() == userId && fav.getItemId() == itemId
        );
    }

    public boolean exists(int userId, int itemId) {
        return favoriteDatabase.stream()
                .anyMatch(fav -> fav.getUserId() == userId && fav.getItemId() == itemId);
    }

    public List<FavoriteItem> findByUser(int userId) {
        return favoriteDatabase.stream()
                .filter(fav -> fav.getUserId() == userId)
                .toList();
    }

    public static void clearDatabase() {
        favoriteDatabase.clear();
        idCounter = 1;
    }
}