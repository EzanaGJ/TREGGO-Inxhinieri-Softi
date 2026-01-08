package Test;

import Model.User;
import DAO.JdbcUserDAO;
import Service.UserService;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

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
        User u = userService.createUser(
                "Ezana", "pass123", "USER", "test_ezana@example.com");

        assertNotEquals(0, u.getUserId());

        User found = userService.getUserById(u.getUserId());
        assertNotNull(found);
        assertEquals("Ezana", found.getName());
        assertEquals("test_ezana@example.com", found.getEmail());
    }

    @Test
    void testCreateUserWithNoName() {
        try {
            userService.createUser("", "pass", "USER", "test_1@example.com");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }

    @Test
    void testCreateUserWithNoPassword() {
        try {
            userService.createUser("Name", "", "USER", "test_2@example.com");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }

    @Test
    void testCreateUserWithNoEmail() {
        try {
            userService.createUser("Name", "pass", "USER", "");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }
    @Test
    void testGetUserById() throws SQLException {
        User u = userService.createUser("TestUser", "pass123", "USER", "test_user@example.com");

        User found = userService.getUserById(u.getUserId());

        assertNotNull(found);
        assertEquals(u.getUserId(), found.getUserId());
        assertEquals("TestUser", found.getName());
        assertEquals("test_user@example.com", found.getEmail());
    }


    @Test
    void testGetUserNotFound() throws SQLException {
        User u = userService.getUserById(999999);
        assertNull(u);
    }

    @Test
    void testUpdateUserFull() throws SQLException {
        User u = userService.createUser(
                "Maria", "pass", "USER", "test_maria@example.com");

        userService.updateUser(
                u.getUserId(),
                "Maria2",
                "newpass",
                "test_maria2@example.com"
        );

        User updated = userService.getUserById(u.getUserId());
        assertNotNull(updated);
        assertEquals("Maria2", updated.getName());
        assertEquals("newpass", updated.getPassword());
        assertEquals("test_maria2@example.com", updated.getEmail());
    }

    @Test
    void testUpdateUserPartialFields() throws SQLException {
        User u = userService.createUser(
                "Ana", "123", "USER", "test_ana@example.com");

        userService.updateUser(u.getUserId(), null, "", null);

        User updated = userService.getUserById(u.getUserId());
        assertEquals("Ana", updated.getName());
        assertEquals("123", updated.getPassword());
        assertEquals("test_ana@example.com", updated.getEmail());
    }

    @Test
    void testUpdateUserNotFound() throws SQLException {
        userService.updateUser(999999, "X", "Y", "Z@example.com");
    }


    @Test
    void testDeleteUser() throws SQLException {
        User u = userService.createUser(
                "Marsi", "123", "USER", "test_marsi@example.com");

        userService.deleteUser(u.getUserId());

        User found = userService.getUserById(u.getUserId());
        assertNull(found);
    }


    @Test
    void testListProductAndPayment() throws SQLException {
        User u = userService.createUser(
                "Ezana", "pass123", "USER", "test_ezana2@example.com");

        userService.listProduct(u, "Cool Shirt");
        userService.makePayment(u, 49.99);

        var actions = userService.getActions();

        assertTrue(actions.contains("LIST_PRODUCT:" + u.getUserId() + ":Cool Shirt"));
        assertTrue(actions.contains("MAKE_PAYMENT:" + u.getUserId() + ":49.99"));
    }
    @Test
    void testLoginSuccess() throws SQLException {
        User u = userService.createUser("LoginUser", "mypassword", "USER", "test_login@example.com");

        try {
            User loggedIn = userService.login("test_login@example.com", "mypassword");
            assertNotNull(loggedIn);
            assertEquals(u.getUserId(), loggedIn.getUserId());

            var actions = userService.getActions();
            assertTrue(actions.contains("LOGIN:" + u.getUserId()));
        } catch (IllegalArgumentException e) {
            fail("Login should have succeeded but threw exception: " + e.getMessage());
        }
    }

    @Test
    void testLoginWrongPassword() throws SQLException {
        userService.createUser("LoginUser2", "mypassword", "USER", "test_login2@example.com");

        try {
            userService.login("test_login2@example.com", "wrongpass");
            fail("Expected IllegalArgumentException for wrong password");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testLoginEmailNotFound() throws SQLException {
        try {
            userService.login("nonexistent@example.com", "pass");
            fail("Expected IllegalArgumentException for non-existing email");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void testLogout() throws SQLException {
        User u = userService.createUser("LogoutUser", "pass123", "USER", "test_logout@example.com");

        userService.logout(u);

        var actions = userService.getActions();
        assertTrue(actions.contains("LOGOUT:" + u.getUserId()));
    }

}
