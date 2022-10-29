package ku.cs.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnection() {
        String databaseName = "";
        String databaseUser = "";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

            Statement statement = databaseLink.createStatement();

//            ResultSet resultSet = statement.executeQuery("select * from ")
//
//            while (resultSet.next()) {
//
//            }

            databaseLink.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
