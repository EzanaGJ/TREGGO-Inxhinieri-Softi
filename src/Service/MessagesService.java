package Service;

import DAO.MessagesDAO;
import Model.Messages;

import java.sql.SQLException;
import java.util.Date;

public class MessagesService {

    private final MessagesDAO messagesDAO;

    public MessagesService(MessagesDAO messagesDAO) {
        this.messagesDAO = messagesDAO;
    }

    // -------------------- SEND --------------------
    public Messages sendMessage(int senderId, int receiverId, String content) throws Exception {
        if (senderId <= 0 || receiverId <= 0) {
            throw new IllegalArgumentException("Invalid sender or receiver ID");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        Messages m = new Messages(0, senderId, receiverId, content, new Date(), false);
        return messagesDAO.create(m);
    }

    // -------------------- GET --------------------
    public Messages getMessageById(int id) {
        if (id <= 0) return null;
        try {
            return messagesDAO.getMessageById(id);
        } catch (SQLException e) {
            // Kthe null në rast se nuk gjendet ose exception
            return null;
        }
    }

    // -------------------- UPDATE --------------------
    public Messages updateMessage(Messages message) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        return messagesDAO.update(message);
    }

    // -------------------- DELETE --------------------
    public void deleteMessage(int id) {
        if (id <= 0) return;
        try {
            messagesDAO.delete(id);
        } catch (SQLException e) {
            // mos lejo exception për id jo-ekzistuese
        }
    }
}

