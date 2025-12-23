
package DAO;

import Model.User;
import java.sql.SQLException;

public interface UserDAO {
    User create(User user) throws SQLException;

    User getUserById(int id) throws SQLException;

    User update(User user) throws SQLException;

    void delete(int id) throws SQLException;
}
