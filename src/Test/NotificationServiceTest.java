package Test;

import DAO.JdbcNotificationDAO;
import Model.Notification;
import Service.NotificationService;
import Service.UserService;
import DAO.JdbcUserDAO;
import db.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NotificationServiceTest {

    private NotificationService notificationService;
    private UserService userService;
    private int testUserId;

    @BeforeEach
    void setUp() throws SQLException {
        notificationService = new NotificationService(new JdbcNotificationDAO());
        userService = new UserService(new JdbcUserDAO());

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement psUser = conn.prepareStatement(
                    "INSERT INTO user (name, password, role_type, email) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, "Ezana");
                psUser.setString(2, "testpass");
                psUser.setString(3, "USER");
                psUser.setString(4, "ezana_notification@example.com");
                psUser.executeUpdate();

                try (ResultSet rs = psUser.getGeneratedKeys()) {
                    if (rs.next()) testUserId = rs.getInt(1);
                }
            }
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM notifications WHERE user_id = " + testUserId);

            stmt.executeUpdate("DELETE FROM user WHERE user_id = " + testUserId);
        }
    }

    @Test
    void testCreateAndGetNotification() throws SQLException {
        Notification n = notificationService.createNotification(testUserId, "INFO", "Test message");
        Assertions.assertNotEquals(0, n.getNotificationId());

        Notification found = notificationService.getNotificationById(n.getNotificationId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals(testUserId, found.getUserId());
        Assertions.assertEquals("INFO", found.getType());
        Assertions.assertEquals("Test message", found.getMessageText());
        Assertions.assertFalse(found.isRead());
    }

    @Test
    void testGetNotificationByIdNotFound() throws SQLException {
        Notification found = notificationService.getNotificationById(999999);
        assertNull(found);
    }
    @Test
    void testCreateNotificationEmptyMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws SQLException {
                notificationService.createNotification(testUserId, "INFO", "");
            }
        });
        assertEquals("Notification message cannot be empty", exception.getMessage());
    }
    @Test
    void testDeleteNotification() throws SQLException {
        Notification n = notificationService.createNotification(testUserId, "ALERT", "Delete me");
        notificationService.deleteNotification(n.getNotificationId());

        Notification found = notificationService.getNotificationById(n.getNotificationId());
        Assertions.assertNull(found);
    }

    @Test
    void testGetNotificationsForUser() throws SQLException {
        notificationService.createNotification(testUserId, "INFO", "Msg 1");
        notificationService.createNotification(testUserId, "ALERT", "Msg 2");

        List<Notification> notifications = notificationService.getNotificationsForUser(testUserId);
        Assertions.assertEquals(2, notifications.size());
    }
    @Test
    void testGetNotificationsForUserEmpty() throws SQLException {
        List<Notification> notifications = notificationService.getNotificationsForUser(testUserId);
        assertTrue(notifications.isEmpty());
    }
    @Test
    void testMarkNotificationAsRead() throws SQLException {
        Notification n = notificationService.createNotification(testUserId, "INFO", "Read me");

        boolean updated = notificationService.markNotificationAsRead(n.getNotificationId());
        Assertions.assertTrue(updated);

        Notification found = notificationService.getNotificationById(n.getNotificationId());
        Assertions.assertTrue(found.isRead());
    }

    @Test
    void testCreateNotification_EmptyMessage() throws SQLException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws SQLException {
                notificationService.createNotification(testUserId, "INFO", "");
            }
        });
        assertEquals("Notification message cannot be empty", exception.getMessage());
    }

}
