package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.Router;
import ku.cs.service.Account;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CreateAccountController {
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
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String tel = telTextFleld.getText();
        String password = passwordField.getText();
        String conPassword = conPasswordField.getText();

        boolean validPassword = true;
        boolean validRegis = true;
        boolean validUsername = true;

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

        // check duplicate usernames, only if register is valid (not empty)
        if (validRegis) {
            validUsername = account.checkUsername(username);
            validPassword = account.checkPassword(password, conPassword);
        }

        if (validRegis && validUsername && validPassword) {
            // TODO: ใส่ข้อมูลลง DB
            try {
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
