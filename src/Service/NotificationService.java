package Service;

import DAO.NotificationDAO;
import Model.Notification;

import java.sql.SQLException;
import java.util.List;

public class NotificationService {

    private final NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    public Notification createNotification(int userId, String type, String messageText) throws SQLException {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Notification type cannot be empty");
        }

        if (messageText == null || messageText.isBlank()) {
            throw new IllegalArgumentException("Notification message cannot be empty");
        }

        Notification notification = new Notification(userId, type, messageText);
        return notificationDAO.create(notification);

    }

    public Notification getNotificationById(int id) throws SQLException {
        return notificationDAO.getById(id);
    }

    public List<Notification> getNotificationsForUser(int userId) throws SQLException {
        return notificationDAO.getNotificationforUser(userId);
    }

    public boolean markNotificationAsRead(int id) throws SQLException {
        return notificationDAO.markAsRead(id);
    }

    public void deleteNotification(int id) throws SQLException {
        notificationDAO.delete(id);
    }
}
