package Test;

import Model.FavoriteItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteItemTest {

    @BeforeEach
    void setUp() {
        FavoriteItem.favoriteDatabase.clear();
        FavoriteItem.idCounter = 1;
    }

    @Test
    void testMarkAsFavorite() {
        FavoriteItem fav = new FavoriteItem(101, 5001);
        fav.markAsFavorite();

        assertEquals(1, FavoriteItem.favoriteDatabase.size());
        assertTrue(fav.isFavorite());
    }

    @Test
    void testRemoveFavorite() {
        FavoriteItem fav = new FavoriteItem(101, 5001);
        fav.markAsFavorite();
        fav.removeFavorite();

        assertFalse(fav.isFavorite());
        assertEquals(0, FavoriteItem.favoriteDatabase.size());
    }

    @Test
    void testIsFavorite() {
        FavoriteItem fav1 = new FavoriteItem(101, 5001);
        FavoriteItem fav2 = new FavoriteItem(101, 5002);

        fav1.markAsFavorite();

        assertTrue(fav1.isFavorite());
        assertFalse(fav2.isFavorite());
    }

    @Test
    void testClearFavorites() {
        FavoriteItem fav1 = new FavoriteItem(101, 5001);
        FavoriteItem fav2 = new FavoriteItem(101, 5002);
        FavoriteItem fav3 = new FavoriteItem(102, 5003);

        fav1.markAsFavorite();
        fav2.markAsFavorite();
        fav3.markAsFavorite();

        FavoriteItem.clearFavorites(101);

        assertFalse(fav1.isFavorite());
        assertFalse(fav2.isFavorite());
        assertTrue(fav3.isFavorite());
        assertEquals(1, FavoriteItem.favoriteDatabase.size());
    }

    @Test
    void testMultipleFavorites() {
        FavoriteItem fav1 = new FavoriteItem(101, 5001);
        FavoriteItem fav2 = new FavoriteItem(101, 5002);

        fav1.markAsFavorite();
        fav2.markAsFavorite();

        assertTrue(fav1.isFavorite());
        assertTrue(fav2.isFavorite());
        assertEquals(2, FavoriteItem.favoriteDatabase.size());
    }
}
