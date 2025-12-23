package DAO;
import Model.Notification;
import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO {
    Notification create(Notification notification) throws SQLException;

    Notification getById(int id) throws SQLException;

    List<Notification> getNotificationforUser(int userId) throws SQLException;

    boolean markAsRead(int id) throws SQLException;

    void delete(int id) throws SQLException;
}
