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

public class LoginControlller {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel;

    public static Customer user;

    public void handleLoginButton(ActionEvent actionEvent) {
        Connection con = DatabaseConnection.connect("customer");

        String username = usernameTextField.getText();
        String password = passwordField.getText();

        // check empty input
        if (username.isBlank() || password.isBlank()) {
            loginMessageLabel.setText("Invalid username or password");
        }

        try {
            String sqlText = "select * FROM customer WHERE C_Username = ? AND C_Password = ?";
            PreparedStatement pst = con.prepareStatement(sqlText);

            // TODO: เช็คการทำงาน mysql กับ query ให้ตรง
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet result = pst.executeQuery();

            if (username.equals("Manager") && password.equals("password")) {
                Router.goTo("menu_manager");

            // TODO: เช็คการทำงาน mysql กับ query ให้ตรง
            } else if (result.next()) {
                user = new Customer(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
                result.close();
                pst.close();
                Router.goTo("menu");
            } else {
                loginMessageLabel.setText("Invalid username or password");
            }

        } catch (IOException e) {
            System.err.println("ไปหน้า menu ไม่ได้");
            e.printStackTrace();
        } catch (SQLException e) {
            loginMessageLabel.setText("Invalid username or password");
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
