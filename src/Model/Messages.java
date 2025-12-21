package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Messages {
    int messageId;
    public int senderId;
    public int receiverId;
    public String content;
    Date timestamp;
    public boolean read;

    public static List<Messages> messageDatabase = new ArrayList<>();
    public static int idCounter = 1;

    public Messages(int senderId, int receiverId, String content) {
        this.messageId = idCounter++;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = new Date();
        this.read = false;
    }

    public void sendMessage() {
        messageDatabase.add(this);
        System.out.println("Message sent from user " + senderId + " to user " + receiverId + ": " + content);
    }

    public static List<Messages> getMessage(int userId) {
        List<Messages> userMessages = new ArrayList<>();
        for (Messages m : messageDatabase) {
            if (m.receiverId == userId) {
                userMessages.add(m);
            }
        }
        return userMessages;
    }

    public void markAsRead() {
        this.read = true;
        System.out.println("Message " + messageId + " marked as read.");
    }

    public void deleteMessage() {
        messageDatabase.removeIf(m -> m.messageId == this.messageId);
        System.out.println("Message " + messageId + " deleted.");
    }

    static void listMessages(int userId) {
        System.out.println("Messages for user " + userId + ":");
        for (Messages m : getMessage(userId)) {
            System.out.println(" - From: " + m.senderId + ", Content: " + m.content + ", Read: " + m.read + ", Time: " + m.timestamp);
        }
    }
}