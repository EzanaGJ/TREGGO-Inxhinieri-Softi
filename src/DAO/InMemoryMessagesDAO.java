package DAO;

import Model.Messages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMessagesDAO implements MessagesDAO {

    private final List<Messages> storage = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public Messages create(Messages message) throws SQLException {
        message = new Messages(idCounter++, message.getSenderId(), message.getReceiverId(),
                message.getContent(), message.getTimestamp(), message.isRead());
        storage.add(message);
        return message;
    }

    @Override
    public Messages getMessageById(int id) throws SQLException {
        return storage.stream().filter(m -> m.getMessageId() == id).findFirst().orElse(null);
    }

    @Override
    public Messages update(Messages message) throws SQLException {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getMessageId() == message.getMessageId()) {
                storage.set(i, message);
                return message;
            }
        }
        throw new SQLException("Message not found");
    }

    @Override
    public void delete(int id) throws SQLException {
        storage.removeIf(m -> m.getMessageId() == id);
    }
}
