package Model;

import java.util.Date;

class Notification {
    int notificationId;
    int userId;
    String type;
    String messageText;
    Date createdAt;
    boolean read;
    static List<Notification> notificationDatabase = new ArrayList<>();
    static int idCounter = 1;

    public Notification(int userId, String type, String messageText) {
        this.notificationId = idCounter++;
        this.userId = userId;
        this.type = type;
        this.messageText = messageText;
        this.createdAt = new Date();
        this.read = false;
    }
    void createNotification() {
        notificationDatabase.add(this);
        System.out.println("Notification " + notificationId + " created for user " + userId + ": " + messageText);
    }


    void modifyNotification(String newType, String newMessageText) {
        this.type = newType;
        this.messageText = newMessageText;
        this.createdAt = new Date();
        System.out.println("Notification " + notificationId + " modified.");
    }

    void sendNotification() {
        System.out.println("Notification sent to user " + userId + ": " + messageText);
    }


    void markAsRead() {
        this.read = true;
        System.out.println("Notification " + notificationId + " marked as read.");
    }

    void deleteNotification() {
        notificationDatabase.removeIf(n -> n.notificationId == this.notificationId);
        System.out.println("Notification " + notificationId + " deleted.");
    }

    static void listNotifications(int userId) {
        System.out.println("Notifications for user " + userId + ":");
        for (Notification n : notificationDatabase) {
            if (n.userId == userId) {
                System.out.println(" - [" + (n.read ? "Read" : "Unread") + "] " + n.type + ": " + n.messageText + " (created: " + n.createdAt + ")");
            }
        }
    }
}