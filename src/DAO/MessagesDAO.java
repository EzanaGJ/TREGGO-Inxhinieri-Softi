package DAO;

import Model.Messages;
import java.sql.SQLException;

public interface MessagesDAO {

    Messages create(Messages message) throws SQLException;

    Messages getMessageById(int id) throws SQLException;

    Messages update(Messages message) throws SQLException;

    void delete(int id) throws SQLException;
}
