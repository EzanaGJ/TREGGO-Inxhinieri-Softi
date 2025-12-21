package Test;

import Model.Messages;
import Service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private MessageService messageService;

    @BeforeEach
    void setup() {
        messageService = new MessageService();
        messageService.clearAll();
    }

    @Test
    void testSendMessage() {
        Messages msg = messageService.sendMessage(1, 2, "Hello");

        assertNotNull(msg);
        assertEquals(1, msg.getSenderId());
        assertEquals(2, msg.getReceiverId());
        assertEquals("Hello", msg.getContent());
        assertFalse(msg.isRead());
    }

    @Test
    void testGetMessagesForUser() {
        messageService.sendMessage(1, 2, "Hi");
        messageService.sendMessage(3, 2, "Hey");

        List<Messages> messages = messageService.getMessagesForUser(2);

        assertEquals(2, messages.size());
    }

    @Test
    void testMarkAsRead() {
        Messages msg = messageService.sendMessage(1, 2, "Test");
        messageService.markAsRead(msg.getMessageId());

        List<Messages> messages = messageService.getMessagesForUser(2);
        assertTrue(messages.get(0).isRead());
    }

    @Test
    void testDeleteMessage() {
        Messages msg = messageService.sendMessage(1, 2, "Delete me");
        messageService.deleteMessage(msg.getMessageId());

        List<Messages> messages = messageService.getMessagesForUser(2);
        assertTrue(messages.isEmpty());
    }
}
