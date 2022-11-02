package ku.cs.connector;

import java.sql.*;

public class DatabaseConnection {
    private static Connection databaseLink;
    public static Connection connect(String databaseName) {
        String databaseUser = "";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
