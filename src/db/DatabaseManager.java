package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static Properties properties = new Properties();

    public DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
    }

    static {
        try (FileInputStream in = new FileInputStream("db.properties")) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Error: db.properties file not found!");
        }
    }

}
