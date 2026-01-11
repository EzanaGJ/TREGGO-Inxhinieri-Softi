package Test;

import DAO.InMemoryMessagesDAO;
import Model.Messages;
import Service.MessagesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MessagesServiceTest {

    private MessagesService messagesService;

    @BeforeEach
    void setUp() {
        InMemoryMessagesDAO dao = new InMemoryMessagesDAO();
        messagesService = new MessagesService(dao);
    }

    @Test
    void testSendMessage() throws Exception {
        Messages msg = messagesService.sendMessage(1, 2, "Hello!");
        assertNotNull(msg);
        assertEquals(1, msg.getSenderId());
        assertEquals(2, msg.getReceiverId());
        assertEquals("Hello!", msg.getContent());
    }

    @Test
    void testSendMessage_InvalidIds() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () ->
                messagesService.sendMessage(0, 2, "Hi"));
        assertEquals("Invalid sender or receiver ID", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, () ->
                messagesService.sendMessage(1, -5, "Hi"));
        assertEquals("Invalid sender or receiver ID", ex2.getMessage());
    }

    @Test
    void testSendMessage_EmptyOrNullContent() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () ->
                messagesService.sendMessage(1, 2, ""));
        assertEquals("Content cannot be empty", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, () ->
                messagesService.sendMessage(1, 2, null));
        assertEquals("Content cannot be empty", ex2.getMessage());
    }

    @Test
    void testGetMessageById() throws Exception {
        Messages msg = messagesService.sendMessage(1, 2, "Hello!");
        Messages found = messagesService.getMessageById(msg.getMessageId());
        assertNotNull(found);
        assertEquals("Hello!", found.getContent());

        // Test id invalid
        assertNull(messagesService.getMessageById(0));
        assertNull(messagesService.getMessageById(-1));
    }

    @Test
    void testUpdateMessage() throws Exception {
        Messages msg = messagesService.sendMessage(1, 2, "Hello!");
        msg.setContent("Updated");
        Messages updated = messagesService.updateMessage(msg);
        assertEquals("Updated", updated.getContent());
    }

    @Test
    void testUpdateMessage_Invalid() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                messagesService.updateMessage(null));
        assertEquals("Message cannot be null", ex.getMessage());
    }

    @Test
    void testDeleteMessage() throws Exception {
        Messages msg = messagesService.sendMessage(1, 2, "Hello!");
        messagesService.deleteMessage(msg.getMessageId());
        assertNull(messagesService.getMessageById(msg.getMessageId()));

        // test id invalid (0, -1) -> should do nothing
        messagesService.deleteMessage(0);
        messagesService.deleteMessage(-5);
    }
}

