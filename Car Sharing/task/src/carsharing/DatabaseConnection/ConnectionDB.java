package carsharing.DatabaseConnection;

import org.h2.tools.DeleteDbFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String jdbcURL = "jdbc:h2:C:/Users/Rahma/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/carsharing";

    public ConnectionDB() {
    }
    public static Connection getConnection() throws ClassNotFoundException{
        DeleteDbFiles.execute("C:/Users/Rahma/IdeaProjects/Car Sharing/Car Sharing" +
                "/task/src/carsharing/db/", "carsharing", true);
        Class.forName("org.h2.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
