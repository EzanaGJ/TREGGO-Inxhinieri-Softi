package Test;

import DAO.JdbcMessagesDAO;
import Model.Messages;
import Service.MessagesService;
import db.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MessagesServiceTest {

    private static MessagesService service;

    private static int senderId;
    private static int receiverId;

    private int messageId;

    @BeforeAll
    static void setup() {
        service = new MessagesService(new JdbcMessagesDAO());

        try (Connection conn = DatabaseManager.getConnection()) {
            // INSERT sender
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user (name, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, "sender");
                ps.setString(2, "123");
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) senderId = rs.getInt(1);
                }
            }

            // INSERT receiver
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user (name, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, "receiver");
                ps.setString(2, "123");
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) receiverId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @AfterEach
    void cleanup() {
        if (messageId > 0) {
            try {
                service.deleteMessage(messageId);
                messageId = 0;
            } catch (Exception e) {
                fail("Cleanup failed: " + e.getMessage());
            }
        }
    }

    @AfterAll
    static void cleanupAll() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM messages");
            stmt.executeUpdate("DELETE FROM user");
        } catch (SQLException e) {
            fail("CleanupAll failed: " + e.getMessage());
        }
    }

    @Test
    void testSendMessage_success() {
        try {
            Messages m = service.sendMessage(senderId, receiverId, "Hello!");
            messageId = m.getMessageId();

            assertEquals(senderId, m.getSenderId());
            assertEquals(receiverId, m.getReceiverId());
            assertEquals("Hello!", m.getContent());
            assertFalse(m.isRead());
            assertNotNull(m.getTimestamp());

        } catch (Exception e) {
            fail("testSendMessage_success failed: " + e.getMessage());
        }
    }

    @Test
    void testSendMessage_emptyContent() {
        try {
            service.sendMessage(senderId, receiverId, "");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testSendMessage_nullContent() {
        try {
            service.sendMessage(senderId, receiverId, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testSendMessage_invalidUserIds() {
        try {
            service.sendMessage(0, receiverId, "Hi");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        try {
            service.sendMessage(senderId, -1, "Hi");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetMessageById_success() {
        try {
            Messages m = service.sendMessage(senderId, receiverId, "Test");
            messageId = m.getMessageId();

            Messages found = service.getMessageById(messageId);
            assertNotNull(found);
            assertEquals("Test", found.getContent());

        } catch (Exception e) {
            fail("testGetMessageById_success failed: " + e.getMessage());
        }
    }

    @Test
    void testGetMessageById_notFound() {
        try {
            Messages found = null;
            try {
                found = service.getMessageById(9999);
            } catch (Exception ignored) {}
            assertNull(found);
        } catch (Exception e) {
            fail("testGetMessageById_notFound failed: " + e.getMessage());
        }
    }

    @Test
    void testUpdateMessage_success() {
        try {
            Messages m = service.sendMessage(senderId, receiverId, "Original");
            messageId = m.getMessageId();

            m.setContent("Updated");
            m.setRead(true);

            Messages updated = service.updateMessage(m);
            assertEquals("Updated", updated.getContent());
            assertTrue(updated.isRead());

        } catch (Exception e) {
            fail("testUpdateMessage_success failed: " + e.getMessage());
        }
    }

    @Test
    void testUpdateMessage_null() {
        try {
            service.updateMessage(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
            // Ky është rezultati i pritur
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testDeleteMessage_success() {
        try {
            Messages m = service.sendMessage(senderId, receiverId, "To delete");
            int idToDelete = m.getMessageId();

            service.deleteMessage(idToDelete);

            Messages deleted = null;
            try {
                deleted = service.getMessageById(idToDelete);
            } catch (Exception ignored) {}
            assertNull(deleted);

        } catch (Exception e) {
            fail("testDeleteMessage_success failed: " + e.getMessage());
        }
    }

    @Test
    void testDeleteMessage_notFound() {
        try {
            assertDoesNotThrow(() -> service.deleteMessage(9999));
        } catch (Exception e) {
            fail("testDeleteMessage_notFound failed: " + e.getMessage());
        }
    }
}
