
package DAO;

import Model.User;
import java.sql.SQLException;

public interface UserDAO {
    User create(User var1) throws SQLException;

    User getUserById(int var1) throws SQLException;

    User update(User var1) throws SQLException;

    void delete(int var1) throws SQLException;
}
