package Model;

public class Notification {
    public String message;
    boolean read;

    Notification(String message) {
        this.message = message;
        this.read = false;
    }

    void markAsRead() {
        this.read = true;
    }
}
