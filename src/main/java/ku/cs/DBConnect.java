package ku.cs;
import java.sql.*;

public class DBConnect {
    public Connection conn = null;
    public Statement stm;
    public ResultSet rs;
    private String url = "jdbc:mysql://localhost:3306/mydb";
    private String username = "root";
    private String password = "123456789";
    public ResultSet getConnect(String sql) {
        try {
            conn = DriverManager.getConnection(url,username,password);
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public void getUpdate(String sql) {
        try {
            stm = conn.createStatement();
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void disconnect() {
        try {
            stm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
