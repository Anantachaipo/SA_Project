package ku.cs.connector;

import java.sql.*;

public class DatabaseConnection {
    private static Connection databaseLink;
    private static String databaseName = "sa_project";
    private static String databaseUser = "root";
    private static String databasePassword = "";
    private static String url = "jdbc:mysql://localhost/" + databaseName;
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
