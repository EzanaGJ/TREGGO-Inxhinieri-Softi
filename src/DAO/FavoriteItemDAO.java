package DAO;

import Model.FavoriteItem;
import java.sql.SQLException;
import java.util.List;

public interface FavoriteItemDAO {
    FavoriteItem create(FavoriteItem favorite) throws SQLException;
    FavoriteItem getFavorite(int userId, int itemId) throws SQLException;
    List<FavoriteItem> getFavoritesByUser(int userId) throws SQLException;
    void delete(int userId, int itemId) throws SQLException;
}
