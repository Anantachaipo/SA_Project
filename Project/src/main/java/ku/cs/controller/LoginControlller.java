package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.Router;
import ku.cs.model.Customer;
import ku.cs.service.Account;

import java.io.IOException;

public class LoginControlller {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMessageLabel;

    public static Customer user;
    private Account account = new Account();
    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        user = account.checkLogin(username, password);
        if (user == null) {
            loginMessageLabel.setText("username or password is incorrect");
            return;
        }
        try {
            Router.goTo("menu");
        } catch (IOException e) {
            System.err.println("ไปหน้า menu ไม่ได้");
            e.printStackTrace();
        }
    }
    public void handleRegisterButton(ActionEvent actionEvent) {
        try {
            Router.goTo("create_account");
        } catch (IOException e) {
            System.err.println("ไปหน้า create_accout ไม่ได้");
            e.printStackTrace();
        }
    }
}
