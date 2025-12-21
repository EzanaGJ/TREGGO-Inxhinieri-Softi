package Test;

import Model.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessagesTest {

    @BeforeEach
    void setUp() {
        Messages.messageDatabase.clear();
        Messages.idCounter = 1;
    }

    @Test
    void testSendMessage() {
        Messages msg = new Messages(1, 2, "Hello!");
        msg.sendMessage();

        assertEquals(1, Messages.messageDatabase.size());
        Messages stored = Messages.messageDatabase.get(0);
        assertEquals(1, stored.senderId);
        assertEquals(2, stored.receiverId);
        assertEquals("Hello!", stored.content);
        assertFalse(stored.read);
    }

    @Test
    void testGetMessage() {
        Messages m1 = new Messages(1, 2, "Hi");
        Messages m2 = new Messages(2, 1, "Hey");
        m1.sendMessage();
        m2.sendMessage();

        List<Messages> user2Messages = Messages.getMessage(2);
        assertEquals(1, user2Messages.size());
        assertEquals("Hi", user2Messages.get(0).content);

        List<Messages> user1Messages = Messages.getMessage(1);
        assertEquals(1, user1Messages.size());
        assertEquals("Hey", user1Messages.get(0).content);
    }

    @Test
    void testMarkAsRead() {
        Messages msg = new Messages(1, 2, "Read me");
        msg.sendMessage();

        assertFalse(msg.read);
        msg.markAsRead();
        assertTrue(msg.read);
    }

    @Test
    void testDeleteMessage() {
        Messages msg1 = new Messages(1, 2, "Msg1");
        Messages msg2 = new Messages(2, 1, "Msg2");
        msg1.sendMessage();
        msg2.sendMessage();

        assertEquals(2, Messages.messageDatabase.size());
        msg1.deleteMessage();
        assertEquals(1, Messages.messageDatabase.size());
        assertEquals("Msg2", Messages.messageDatabase.get(0).content);
    }

    @Test
    void testListMessages() {
        Messages msg1 = new Messages(1, 2, "First");
        Messages msg2 = new Messages(3, 2, "Second");
        msg1.sendMessage();
        msg2.sendMessage();

        List<Messages> messages = Messages.getMessage(2);
        assertEquals(2, messages.size());
        assertEquals("First", messages.get(0).content);
        assertEquals("Second", messages.get(1).content);
    }
}
