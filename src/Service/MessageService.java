package Service;

import DAO.MessageDAO;
import Model.Messages;
import java.util.Date;
import java.util.List;

public class MessageService {

    private final MessageDAO messageDAO = new MessageDAO();

    public Messages sendMessage(int senderId, int receiverId, String content) {
        return messageDAO.save(
                senderId,
                receiverId,
                content,
                new Date(),
                false
        );
    }

    public List<Messages> getMessagesForUser(int userId) {
        return messageDAO.findByReceiver(userId);
    }

    public void markAsRead(int messageId) {
        messageDAO.markAsRead(messageId);
    }

    public void deleteMessage(int messageId) {
        messageDAO.delete(messageId);
    }

    // për unit test
    public void clearAll() {
        messageDAO.clear();
    }
}
