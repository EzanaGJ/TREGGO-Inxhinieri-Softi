package Service;

import DAO.UserDAO;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserDAO userDao;
    private final List<String> actions = new ArrayList<>();

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public User createUser(String name, String password, String roleType, String email) throws SQLException {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be empty");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email cannot be empty");

        User user = new User(name, password, roleType, email);
        userDao.create(user);
        return user;
    }

    public User getUserById(int id) throws SQLException {
        return userDao.getUserById(id);
    }

    public void updateUser(int id, String newName, String newPassword, String newEmail) throws SQLException {
        User user = userDao.getUserById(id);
        if (user != null) {
            if (newName != null && !newName.isBlank()) user.setName(newName);
            if (newPassword != null && !newPassword.isBlank()) user.setPassword(newPassword);
            if (newEmail != null && !newEmail.isBlank()) user.setEmail(newEmail);
            userDao.update(user);
        }
    }

    public void deleteUser(int id) throws SQLException {
        userDao.delete(id);
    }
    public User login(String email, String password) throws SQLException {
        if (email == null || email.isBlank() || password == null || password.isBlank())
            throw new IllegalArgumentException("Email and password cannot be empty");

        User user = userDao.getUserByEmail(email);
        if (user == null)
            throw new IllegalArgumentException("No user found with this email");

        if (!user.getPassword().equals(password)) // For real apps, use password hashing
            throw new IllegalArgumentException("Incorrect password");

        actions.add("LOGIN:" + user.getUserId());
        return user; // Successfully logged in
    }

    public void logout(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        actions.add("LOGOUT:" + user.getUserId());
    }

    public void listProduct(User user, String productName) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");
        if (productName == null || productName.isBlank())
            throw new IllegalArgumentException("Product name cannot be empty");

        actions.add("LIST_PRODUCT:" + user.getUserId() + ":" + productName);
    }

    public void makePayment(User user, double amount) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");
        if (amount <= 0)
            throw new IllegalArgumentException("Payment amount must be positive");

        actions.add("MAKE_PAYMENT:" + user.getUserId() + ":" + amount);
    }

    public List<String> getActions() {
        return List.copyOf(actions);
    }
}
