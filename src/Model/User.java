package Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    int userId;
    String name;
    String password;
    RoleType roleType;

    List<Address> addresses = new ArrayList<>();
    List<Notification> notifications = new ArrayList<>();

    public static List<User> userDatabase = new ArrayList<>();
    static int idCounter = 1;

    public User(String name, String password, RoleType roleType) {
        this.userId = idCounter++;
        this.name = name;
        this.password = password;
        this.roleType = roleType;
    }

    void createUser() {
        userDatabase.add(this);
        System.out.println("User " + name + " created with ID " + userId);
    }

    static User getUserById(int id) {
        return userDatabase.stream()
                .filter(u -> u.userId == id)
                .findFirst()
                .orElse(null);
    }

    void updateData(String newName, String newPassword) {
        this.name = newName;
        this.password = newPassword;
        System.out.println("User " + userId + " updated.");
    }

    void deleteData() {
        userDatabase.removeIf(u -> u.userId == this.userId);
        System.out.println("User " + userId + " deleted.");
    }

    void getNotification() {
        System.out.println("Notifications for " + name + ":");
        for (Notification n : notifications) {
            System.out.println(" - " + n.message + (n.read ? " (read)" : " (unread)"));
        }
    }

    void listProduct(String productName) {
        System.out.println(name + " listed product: " + productName);
    }

    void makePayment(double amount) {
        System.out.println(name + " made a payment of $" + amount);
    }

    void addAddress(Address address) {
        addresses.add(address);
        System.out.println("Address added for " + name);
    }

    void addNotification(String message) {
        notifications.add(new Notification(message));
    }
}