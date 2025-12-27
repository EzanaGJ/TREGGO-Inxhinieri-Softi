package Test;

import DAO.JdbcFavoriteItemDAO;
import Service.FavoriteItemService;
import Model.FavoriteItem;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FavoriteItemServiceTest {

    private FavoriteItemService favoriteItemService;

    @BeforeEach
    void setUp() throws SQLException {
        favoriteItemService = new FavoriteItemService(new JdbcFavoriteItemDAO());

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM favorites");
            stmt.executeUpdate("DELETE FROM item WHERE title LIKE 'TestItem%'");
            stmt.executeUpdate("DELETE FROM user WHERE email LIKE 'test_user@example.com'");

            stmt.executeUpdate("INSERT INTO user (user_id, name, password, role_type, email) " +
                    "VALUES (1, 'TestUser', 'pass', 'USER', 'test_user@example.com')");

            stmt.executeUpdate("INSERT INTO item (item_id, user_id, title, price) " +
                    "VALUES (100, 1, 'TestItem1', 10.0), " +
                    "       (101, 1, 'TestItem2', 20.0), " +
                    "       (102, 1, 'TestItem3', 30.0), " +
                    "       (103, 1, 'TestItem4', 40.0)");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM favorites");
            stmt.executeUpdate("DELETE FROM item WHERE title LIKE 'TestItem%'");
            stmt.executeUpdate("DELETE FROM user WHERE email LIKE 'test_user@example.com'");
        }
    }

    @Test
    void testAddAndGetFavorite() throws SQLException {
        FavoriteItem f = favoriteItemService.addFavorite(1, 100);
        assertNotNull(f);

        FavoriteItem found = favoriteItemService.getFavorite(1, 100);
        assertNotNull(found);
        assertEquals(1, found.getUserId());
        assertEquals(100, found.getItemId());
    }

    @Test
    void testGetFavoritesByUser() throws SQLException {
        favoriteItemService.addFavorite(1, 101);
        favoriteItemService.addFavorite(1, 102);

        List<FavoriteItem> favorites = favoriteItemService.getFavoritesByUser(1);
        assertEquals(2, favorites.size());
    }

    @Test
    void testRemoveFavorite() throws SQLException {
        favoriteItemService.addFavorite(1, 103);
        favoriteItemService.removeFavorite(1, 103);

        FavoriteItem found = favoriteItemService.getFavorite(1, 103);
        assertNull(found);
    }

    @Test
    void testInvalidFavorite() {
        try {
            favoriteItemService.addFavorite(0, -1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }
}
