package Test;

import DAO.JdbcNotificationDAO;
import Model.Notification;
import Service.NotificationService;
import db.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NotificationServiceTest {

    private NotificationService notificationService;

    @BeforeEach
    void setUp() throws SQLException {

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM notifications");
        }

        notificationService = new NotificationService(new JdbcNotificationDAO());
    }

    @Test
    void testCreateAndGetNotification() throws SQLException {
        Notification n = notificationService.createNotification(89, "INFO", "Test message");
        Assertions.assertNotEquals(0, n.getNotificationId());

        Notification found = notificationService.getNotificationById(n.getNotificationId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals(89, found.getUserId());
        Assertions.assertEquals("INFO", found.getType());
        Assertions.assertEquals("Test message", found.getMessageText());
        Assertions.assertFalse(found.isRead());
    }

    @Test
    void testDeleteNotification() throws SQLException {
        Notification n = notificationService.createNotification(89, "ALERT", "Delete me");
        notificationService.deleteNotification(n.getNotificationId());

        Notification found = notificationService.getNotificationById(n.getNotificationId());
        Assertions.assertNull(found);
    }

    @Test
    void testGetNotificationsForUser() throws SQLException {
        notificationService.createNotification(89, "INFO", "Msg 1");
        notificationService.createNotification(89, "ALERT", "Msg 2");

        List<Notification> notifications = notificationService.getNotificationsForUser(89);
        Assertions.assertEquals(2, notifications.size());
    }

    @Test
    void testMarkNotificationAsRead() throws SQLException {
        Notification n = notificationService.createNotification(89, "INFO", "Read me");

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
                notificationService.createNotification(89, "INFO", "");
            }
        });
        assertEquals("Notification message cannot be empty", exception.getMessage());
    }


}
