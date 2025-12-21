package DAO;

import Model.Messages;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDAO {

    private static final List<Messages> messageDB = new ArrayList<>();
    private static int idCounter = 1;

    public Messages save(int senderId, int receiverId,
                         String content, Date timestamp, boolean read) {

        Messages message = new Messages(
                idCounter++,
                senderId,
                receiverId,
                content,
                timestamp,
                read
        );
        messageDB.add(message);
        return message;
    }

    public List<Messages> findByReceiver(int receiverId) {
        List<Messages> result = new ArrayList<>();
        for (Messages m : messageDB) {
            if (m.getReceiverId() == receiverId) {
                result.add(m);
            }
        }
        return result;
    }

    public void markAsRead(int messageId) {
        for (Messages m : messageDB) {
            if (m.getMessageId() == messageId) {
                m.setRead(true);
                return;
            }
        }
    }

    public void delete(int messageId) {
        messageDB.removeIf(m -> m.getMessageId() == messageId);
    }

    // vetëm për testim
    public void clear() {
        messageDB.clear();
        idCounter = 1;
    }
}
