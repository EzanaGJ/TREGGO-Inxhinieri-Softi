package Model;

import java.util.Date;

public class Messages {

    private int messageId;
    private int senderId;
    private int receiverId;
    private String content;
    private Date timestamp;
    private boolean read;

    // Constructor used by DAO
    public Messages(int messageId, int senderId, int receiverId,
                    String content, Date timestamp, boolean read) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
        this.read = read;
    }

    // Getters
    public int getMessageId() { return messageId; }
    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
    public boolean isRead() { return read; }

    // Setters
    public void setContent(String content) { this.content = content; }
    public void setRead(boolean read) { this.read = read; }
}
