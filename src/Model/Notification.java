package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notification {
    public int notificationId;
    public int userId;
    public String messageText;
    public Date createdAt;
    public boolean read;

    public static List<Notification> notificationDatabase = new ArrayList<>();
    public static int idCounter = 1;

    public Notification(int userId, String messageText) {
        this.notificationId = idCounter++;
        this.userId = userId;
        this.messageText = messageText;
        this.createdAt = new Date();
        this.read = false;
    }
    public void createNotification() {
        notificationDatabase.add(this);
        System.out.println("Notification " + notificationId + " created for user " + userId + ": " + messageText);
    }

    public void markAsRead() {
        this.read = true;
        System.out.println("Notification " + notificationId + " marked as read.");
    }

    public void deleteNotification() {
        notificationDatabase.removeIf(n -> n.notificationId == this.notificationId);
        System.out.println("Notification " + notificationId + " deleted.");
    }

}
