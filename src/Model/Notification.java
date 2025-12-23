package Model;

import java.util.Date;

public class Notification {
    private int notificationId;
    private int userId;
    private String type;
    private String messageText;
    private Date createdAt;
    private boolean read;

    public Notification(int userId,String type, String messageText) {
        this.userId = userId;
        this.type = type;
        this.messageText = messageText;
        this.createdAt = new Date();
        this.read = false;
    }

    public Notification(int notificationId, int userId, String type, String messageText,Date createdAt,boolean read) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.type=type;
        this.messageText = messageText;
        this.createdAt = createdAt;
        this.read = read;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getMessageText() {
        return messageText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return read;
    }
    }


