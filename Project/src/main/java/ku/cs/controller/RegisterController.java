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
import static ku.cs.controller.LoginController.user;

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

        /*
            * -> check empty input
            * -> check non-numeric input (telephone)
            * -> check result value doesn't exceed DB size
                ** name     (50)
                ** username (30)
                ** telephone (10)
                ** password (20)
            * -> check duplicated username
            * -> check password match
        */

        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String tel = telTextFleld.getText();
        String password = passwordField.getText();
        String conPassword = conPasswordField.getText();

        boolean checkBlank = true;

        // check empty input
        if (name.isBlank()) {
            nameTag.setText(TAG);
            checkBlank = false;
        }
        if (username.isBlank()) {
            usernameTag.setText(TAG);
            checkBlank = false;
        }
        if (tel.isBlank()) {
            telTag.setText(TAG);
            checkBlank = false;
        }
        if (password.isBlank()) {
            passwordTag.setText(TAG);
            checkBlank = false;
        }
        if (conPassword.isBlank()) {
            conPasswordTag.setText(TAG);
            checkBlank = false;
        }
        if (!checkBlank) {
            registerMessageLabel.setText("Input field must not be empty");
            return;
        }

        // check non-numeric input (telephone)
        try {
            int localtel = Integer.parseInt(tel);
            if (localtel <= 0) {
                telTag.setText(TAG);
                registerMessageLabel.setText("Input field can only contain numeric values");
                return;
            }
        } catch (IllegalArgumentException e) {
            telTag.setText(TAG);
            registerMessageLabel.setText("Input field can only contain numeric values");
            return;
        }

        /*
         * check result value doesn't exceed DB size
         ** name     (50)
         ** username (30)
         ** telephone (10)
         ** password (20)
        */
        if (name.length() > 50) {
            nameTag.setText(TAG);
            registerMessageLabel.setText("Characters input limit exceeded (50)");
            return;
        } else if (username.length() > 30) {
            usernameTag.setText(TAG);
            registerMessageLabel.setText("Characters input limit exceeded (30)");
            return;
        } else if (!(tel.length() == 9 || tel.length() == 10)) {
            telTag.setText(TAG);
            registerMessageLabel.setText("Invalid Telephone number");
            return;
        } else if (password.length() > 20) {
            passwordTag.setText(TAG);
            registerMessageLabel.setText("Characters input limit exceeded (20)");
            return;
        }

        // check duplicate username
        try {
            String sqlText = "select * FROM customer WHERE C_Username = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, username);
            ResultSet result = pst.executeQuery();

            if (result.next()) {
                usernameTag.setText(TAG);
                registerMessageLabel.setText("Username is already used");

                result.close();
                pst.close();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // check password match
        if (!password.equals(conPassword)) {
            registerMessageLabel.setText("Unmatched password");
            passwordTag.setText(TAG);
            conPasswordTag.setText(TAG);
            return;
        }

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
            registerMessageLabel.setText("Invalid Register, contract application provider");
            e.printStackTrace();
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
