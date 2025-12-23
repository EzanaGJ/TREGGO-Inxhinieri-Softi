package Test;

import Model.User;
import DAO.JdbcUserDAO;
import Service.UserService;
import db.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statementmt = connection.createStatement()) {
            statementmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            statementmt.executeUpdate("DELETE FROM user");
            statementmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
        userService = new UserService(new JdbcUserDAO());
    }

    @Test
    void testCreateAndFindUser() throws SQLException {
        User u = userService.createUser("Ezana", "pass123", "USER", "ezana12@example.com");
        Assertions.assertNotEquals(0, u.getUserId());

        User found = userService.getUserById(u.getUserId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Ezana", found.getName());
        Assertions.assertEquals("ezana12@example.com", found.getEmail());
    }

    @Test
    void testUpdateUser() throws SQLException {
        User u = userService.createUser("Maria", "pass", "USER", "maria12@example.com");

        userService.updateUser(u.getUserId(), "Maria2", "newpass", "maria22@example.com");

        User updated = userService.getUserById(u.getUserId());
        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Maria2", updated.getName());
        Assertions.assertEquals("newpass", updated.getPassword());
        Assertions.assertEquals("maria22@example.com", updated.getEmail());
    }

    @Test
    void testDeleteUser() throws SQLException {
        User u = userService.createUser("Marsi", "123", "USER", "marsi3@example.com");
        userService.deleteUser(u.getUserId());

        User found = userService.getUserById(u.getUserId());
        Assertions.assertNull(found);
    }

    @Test
    void testListProductAndPayment() throws SQLException {
        User u = userService.createUser("Dana", "pass", "USER", "dana2@example.com");

        userService.listProduct(u, "Cool Shirt");
        userService.makePayment(u, 49.99);
    }
}
