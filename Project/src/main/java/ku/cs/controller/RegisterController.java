package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.Router;
import ku.cs.connector.DatabaseConnection;
import ku.cs.service.Account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class RegisterController {
    private static final String TAG = "*";
    private Account account = new Account();
    @FXML
    private Label nameTag;
    @FXML
    private Label usernameTag;
    @FXML
    private Label telTag;
    @FXML
    private Label passwordTag;
    @FXML
    private Label conPasswordTag;


    @FXML
    private TextField nameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField telTextFleld;
    @FXML
    private PasswordField conPasswordField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label registerMessageLabel;


    @FXML
    void handleRegisterButton(ActionEvent event) {
        Connection con = DatabaseConnection.connect("customer");;
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String tel = telTextFleld.getText();
        String password = passwordField.getText();
        String conPassword = conPasswordField.getText();

        boolean validRegis = true;
        boolean validPassword = false;
        boolean validUsername = false;

        // check empty input
        if (name.isBlank()) {
            nameTag.setText(TAG);
            validRegis = false;
        }
        if (username.isBlank()) {
            usernameTag.setText(TAG);
            validRegis = false;
        }
        // Thailand phone len range 9 - 10
        if (tel.isBlank() || !(tel.length() == 9 || tel.length() == 10) ){
            telTag.setText(TAG);
            validRegis = false;
        }
        if (password.isBlank()) {
            passwordTag.setText(TAG);
            validRegis = false;
        }
        if (conPassword.isBlank()) {
            conPasswordTag.setText(TAG);
            validRegis = false;
        }

        // check username and password
        if (validRegis) {

            // TODO: ทำให้ check username ถูกต้อง
            // check duplicate username
            try {
                String sqlText = "select * FROM customer WHERE C_Username = ?";
                PreparedStatement pst = con.prepareStatement(sqlText);
                pst.setString(1, username);
                ResultSet result = pst.executeQuery();

                if (result.next()) {
                    validUsername = false;
                    result.close();
                    pst.close();
                }
                else {
                    validUsername = true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // check password match
            validPassword = password.equals(conPassword);
        }

        if (validRegis && validUsername && validPassword) {
            // TODO: ใส่ข้อมูลลง DB

            try {
                registerMessageLabel.setText("Register successfully");
                TimeUnit.SECONDS.wait(1);
                Router.goTo("login");
            } catch (IOException e) {
                System.err.println("ไปหน้า login ไป");
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.err.println("TimeUnit.SECONDS.wait() มีปัญหา");
                e.printStackTrace();
            }
        } else {
            if (!validRegis)
                registerMessageLabel.setText("Invalid information");
            else if (!validUsername)
                registerMessageLabel.setText("Username is already used");
            else if (!validPassword)
                registerMessageLabel.setText("Password is not matched");
        }
    }



}
