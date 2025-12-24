package Test;

import Model.User;
import DAO.JdbcUserDAO;
import Service.UserService;
import db.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() throws SQLException {
        userService = new UserService(new JdbcUserDAO());
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM user WHERE email LIKE 'test_%@example.com'");

        }
    }

    @Test
    void testCreateAndFindUser() throws SQLException {
        User u = userService.createUser("Ezana", "pass123", "USER", "test_ezana@example.com");
        Assertions.assertNotEquals(0, u.getUserId());

        User found = userService.getUserById(u.getUserId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Ezana", found.getName());
        Assertions.assertEquals("test_ezana@example.com", found.getEmail());
    }

    @Test
    void testUpdateUser() throws SQLException {
        User u = userService.createUser("Maria", "pass", "USER", "test_maria@example.com");

        userService.updateUser(u.getUserId(), "Maria2", "newpass", "test_maria2@example.com");

        User updated = userService.getUserById(u.getUserId());
        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Maria2", updated.getName());
        Assertions.assertEquals("newpass", updated.getPassword());
        Assertions.assertEquals("test_maria2@example.com", updated.getEmail());
    }

    @Test
    void testDeleteUser() throws SQLException {
        User u = userService.createUser("Marsi", "123", "USER", "test_marsi@example.com");
        userService.deleteUser(u.getUserId());

        User found = userService.getUserById(u.getUserId());
        Assertions.assertNull(found);
    }

    @Test
    void testListProductAndPayment() throws SQLException {
        User u = userService.createUser("Ezana", "pass123", "USER", "test_ezana2@example.com");

        userService.listProduct(u, "Cool Shirt");
        userService.makePayment(u, 49.99);

    }
}
