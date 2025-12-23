package Service;

import DAO.UserDAO;
import Model.User;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public User createUser(String name, String password, String roleType, String email) throws SQLException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        User user = new User(name, password, roleType, email);
        this.userDao.create(user);
        System.out.println("User " + name + " created with ID " + user.getUserId());
        return user;
    }

    public User getUserById(int id) throws SQLException {
        return this.userDao.getUserById(id);
    }

    public void updateUser(int id, String newName, String newPassword, String newEmail) throws SQLException {
        User user = this.userDao.getUserById(id);
        if (user != null) {
            if (newName != null && !newName.isBlank()) user.setName(newName);
            if (newPassword != null && !newPassword.isBlank()) user.setPassword(newPassword);
            if (newEmail != null && !newEmail.isBlank()) user.setEmail(newEmail);
            this.userDao.update(user);
            System.out.println("User " + id + " updated");
        }
    }

    public void deleteUser(int id) throws SQLException {
        this.userDao.delete(id);
        System.out.println("User " + id + " deleted");
    }

    public void listProduct(User user, String productName) {
        System.out.println(user.getName() + " listed product: " + productName);
    }

    public void makePayment(User user, double amount) {
        System.out.println(user.getName() + " made payment: $" + amount);
    }
}
