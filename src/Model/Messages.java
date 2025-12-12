package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Messages {
    int messageId;
    int senderId;
    int receiverId;
    String content;
    Date timestamp;
    boolean read;

    static List<Messages> messageDatabase = new ArrayList<>();
    static int idCounter = 1;

    public Messages(int senderId, int receiverId, String content) {
        this.messageId = idCounter++;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = new Date();
        this.read = false;
    }

    void sendMessage() {
        messageDatabase.add(this);
        System.out.println("Message sent from user " + senderId + " to user " + receiverId + ": " + content);
    }

    static List<Messages> getMessage(int userId) {
        List<Messages> userMessages = new ArrayList<>();
        for (Messages m : messageDatabase) {
            if (m.receiverId == userId) {
                userMessages.add(m);
            }
        }
        return userMessages;
    }

    void markAsRead() {
        this.read = true;
        System.out.println("Message " + messageId + " marked as read.");
    }

    void deleteMessage() {
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