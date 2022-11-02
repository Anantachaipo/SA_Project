package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.Router;
import ku.cs.connector.DatabaseConnection;
import ku.cs.model.Customer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel;

    public static Customer user;
    public static Connection connection = DatabaseConnection.connect();
    public void handleLoginButton(ActionEvent actionEvent) {
        loginMessageLabel.setText("");


        String username = usernameTextField.getText();
        String password = passwordField.getText();

        // check empty input
        if (username.isBlank() || password.isBlank()) {
            System.out.println("In 'blank field'");
            loginMessageLabel.setText("Invalid username or password");
            return;
        }

        try {
            if (username.equals("Manager") && password.equals("password")) {
                System.out.println("In 'manager login'");
                Router.goTo("menu_manager");
                return;
            }

            String sqlText = "select * FROM customer WHERE C_Username = ? AND C_Password = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);

            // TODO: เช็คการทำงาน mysql กับ query ให้ตรง
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet result = pst.executeQuery();


            // TODO: เช็คการทำงาน mysql กับ query ให้ตรง
            if (result.next()) {
                user = new Customer(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5));
                result.close();
                pst.close();
                System.out.println("Current user = " + user.toString());
                Router.goTo("menu");
            } else {
                System.out.println("In 'not found user'");
                loginMessageLabel.setText("Invalid username or password");
            }

        } catch (IOException e) {
            System.err.println("ไปหน้า menu ไม่ได้");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            loginMessageLabel.setText("Invalid username or password");
            e.printStackTrace();
        }
    }
    public void handleRegisterButton(ActionEvent actionEvent) {
        try {
            Router.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปหน้า register ไม่ได้");
            e.printStackTrace();
        }
    }
}
