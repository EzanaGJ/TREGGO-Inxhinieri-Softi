package Service;

import DAO.FavoriteItemDAO;
import Model.FavoriteItem;

import java.util.List;

public class FavoriteItemService {

    private final FavoriteItemDAO favoriteDAO = new FavoriteItemDAO();

    public FavoriteItem addToFavorites(int userId, int itemId) {
        if (favoriteDAO.exists(userId, itemId)) {
            throw new IllegalStateException("Item already in favorites");
        }

        FavoriteItem favorite = favoriteDAO.save(userId, itemId);
        System.out.println("Item " + itemId + " added to favorites");
        return favorite;
    }

    public void removeFromFavorites(int userId, int itemId) {
        favoriteDAO.delete(userId, itemId);
        System.out.println("Item " + itemId + " removed from favorites");
    }

    public boolean isFavorite(int userId, int itemId) {
        return favoriteDAO.exists(userId, itemId);
    }

    public List<FavoriteItem> getUserFavorites(int userId) {
        return favoriteDAO.findByUser(userId);
    }

    public void clearUserFavorites(int userId) {
        favoriteDAO.findByUser(userId)
                .forEach(fav -> favoriteDAO.delete(userId, fav.getItemId()));
    }
}