package Test;
import Model.User;

import Model.Enum.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @BeforeEach
    void setUp() {
        User.userDatabase.clear();
    }

    @Test
    void testCreateUser() {
        User u = new User("Mars", "1234", RoleType.USER);
        u.createUser();

        assertEquals(1, User.userDatabase.size());
        assertEquals("Mars", User.userDatabase.get(0).name);
    }

    @Test
    void testGetUserById() {
        User u1 = new User("A", "pass", RoleType.USER);
        User u2 = new User("B", "pass", RoleType.ADMIN);

        u1.createUser();
        u2.createUser();

        User found = User.getUserById(u2.userId);
        assertNotNull(found);
        assertEquals("B", found.name);
    }

    @Test
    void testUpdateUser() {
        User u = new User("OldName", "123", RoleType.USER);
        u.createUser();

        u.updateData("NewName", "456");

        assertEquals("NewName", u.name);
        assertEquals("456", u.password);
    }

    @Test
    void testDeleteUser() {
        User u = new User("Test", "pass", RoleType.USER);
        u.createUser();
        u.deleteData();

        assertTrue(User.userDatabase.isEmpty());
    }

    @Test
    void testNotifications() {
        User u = new User("NotifUser", "pass", RoleType.USER);
        u.addNotification("Welcome");

        assertEquals(1, u.notifications.size());
        assertEquals("Welcome", u.notifications.get(0).message);
    }
}

