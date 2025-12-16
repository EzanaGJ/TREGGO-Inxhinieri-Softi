package Test;

import Model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @BeforeEach
    void setUp() {
        Notification.notificationDatabase.clear();
        Notification.idCounter = 1;
    }

    @Test
    void testNotificationCreation() {
        Notification n = new Notification(101, "Hello");

        assertEquals(101, n.userId);
        assertEquals("Hello", n.messageText);
        assertFalse(n.read);
        assertNotNull(n.createdAt);
        assertEquals(1, n.notificationId);
    }

    @Test
    void testMarkAsRead() {
        Notification n = new Notification(101, "Read this");
        n.markAsRead();
        assertTrue(n.read);
    }

    @Test
    void testCreateNotification() {
        Notification n = new Notification(101, "Add to DB");
        n.createNotification();

        assertEquals(1, Notification.notificationDatabase.size());
        assertEquals(n, Notification.notificationDatabase.get(0));
    }

    @Test
    void testDeleteNotification() {
        Notification n1 = new Notification(101, "Delete me");
        Notification n2 = new Notification(102, "Keep me");

        n1.createNotification();
        n2.createNotification();

        assertEquals(2, Notification.notificationDatabase.size());

        n1.deleteNotification();

        assertEquals(1, Notification.notificationDatabase.size());
        assertEquals(n2, Notification.notificationDatabase.get(0));
    }
}
