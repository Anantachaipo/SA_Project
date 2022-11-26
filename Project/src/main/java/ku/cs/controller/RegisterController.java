package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.service.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class RegisterController {
    private static final String TAG = "*";

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
        resetTagAndMessage();

        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String tel = telTextFleld.getText();
        String password = passwordField.getText();
        String conPassword = conPasswordField.getText();

        boolean validRegis = true;
        boolean validUsername = false;
        boolean validPassword;

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
        try {
            Integer.parseInt(tel);
            if (tel.isBlank() || !(tel.length() == 9 || tel.length() == 10)){
                telTag.setText(TAG);
                validRegis = false;
            }
        } catch (IllegalArgumentException e) {
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

        // if input is not blank, check username
        if (validRegis) {

            // check duplicate username
            try {
                String sqlText = "select * FROM customer WHERE C_Username = ?";
                PreparedStatement pst = connection.prepareStatement(sqlText);
                pst.setString(1, username);
                ResultSet result = pst.executeQuery();

                if (result.next()) {
                    validUsername = false;
                    result.close();
                    pst.close();
                } else {
                    validUsername = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        // check password match
        validPassword = password.equals(conPassword);

        if (validRegis && validUsername && validPassword) {
            try {
                String sqlText = "insert into customer (C_Name, C_Username, C_Password, C_Tel) values (?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(sqlText);

                pst.setString(1, name);
                pst.setString(2, username);
                pst.setString(3, password);
                pst.setString(4, tel);
                pst.executeUpdate();

                pst.close();

                registerMessageLabel.setText("Register successfully");
                Utilities.gotoPage("login");
            } catch (SQLException e) {
                System.err.println("ใช้ SQL ไม่ได้");
                registerMessageLabel.setText("Invalid Register");
                e.printStackTrace();
            }
        } else {
            if (!validRegis)
                registerMessageLabel.setText("Invalid information");
            else if (!validUsername) {
                registerMessageLabel.setText("Username is already used");
                usernameTag.setText(TAG);
            }
            else if (!validPassword) {
                registerMessageLabel.setText("Password is not matched");
                passwordTag.setText(TAG);
                conPasswordTag.setText(TAG);
            }
        }
    }

    @FXML
    void handleBackButton(ActionEvent actionEvent) {
        Utilities.gotoPage("login");
    }

    private void resetTagAndMessage() {
        conPasswordTag.setText("");
        telTag.setText("");
        nameTag.setText("");
        usernameTag.setText("");
        passwordTag.setText("");
        conPasswordTag.setText("");
        registerMessageLabel.setText("");
    }

}
