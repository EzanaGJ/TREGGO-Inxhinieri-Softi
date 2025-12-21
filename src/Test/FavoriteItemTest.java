package Test;

import DAO.FavoriteItemDAO;
import Service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteServiceTest {

    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        FavoriteItemDAO.clearDatabase();
        favoriteService = new FavoriteService();
    }

    @Test
    void testAddToFavorites() {
        favoriteService.addToFavorites(1, 10);

        assertTrue(favoriteService.isFavorite(1, 10));
    }

    @Test
    void testRemoveFromFavorites() {
        favoriteService.addToFavorites(1, 10);
        favoriteService.removeFromFavorites(1, 10);

        assertFalse(favoriteService.isFavorite(1, 10));
    }

    @Test
    void testAddDuplicateFavoriteThrowsException() {
        favoriteService.addToFavorites(1, 10);

        assertThrows(IllegalStateException.class,
                () -> favoriteService.addToFavorites(1, 10));
    }

    @Test
    void testClearUserFavorites() {
        favoriteService.addToFavorites(1, 10);
        favoriteService.addToFavorites(1, 20);

        favoriteService.clearUserFavorites(1);

        assertEquals(0, favoriteService.getUserFavorites(1).size());
    }
}
