package Service;

import DAO.FavoriteItemDAO;
import Model.FavoriteItem;

import java.sql.SQLException;
import java.util.List;

public class FavoriteItemService {

    private final FavoriteItemDAO favoriteItemDAO;

    public FavoriteItemService(FavoriteItemDAO favoriteItemDAO) {
        this.favoriteItemDAO = favoriteItemDAO;
    }

    public FavoriteItem addFavorite(int userId, int itemId) throws SQLException {
        if (userId <= 0 || itemId <= 0) throw new IllegalArgumentException("Invalid userId or itemId");
        FavoriteItem favorite = new FavoriteItem(userId, itemId);
        return favoriteItemDAO.create(favorite);
    }

    public FavoriteItem getFavorite(int userId, int itemId) throws SQLException {
        return favoriteItemDAO.getFavorite(userId, itemId);
    }

    public List<FavoriteItem> getFavoritesByUser(int userId) throws SQLException {
        return favoriteItemDAO.getFavoritesByUser(userId);
    }

    public void removeFavorite(int userId, int itemId) throws SQLException {
        favoriteItemDAO.delete(userId, itemId);
    }
}
