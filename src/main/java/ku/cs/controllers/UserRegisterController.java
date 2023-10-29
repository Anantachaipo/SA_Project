package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.DBConnect;
import ku.cs.service.Account;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserRegisterController implements ActionListener {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ImageView profile;
    @FXML
    private Label registerLabel;
    private Account account = new Account();
    private String pathProfile = "images/user.png";

    @FXML
    public void initialize(){
// String path ใส่รูป
    }
    public DBConnect db;

    @FXML
    private void registerNewMember(){
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String email = emailTextField.getText();
        String number = numberTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword =  confirmPasswordField.getText();
        String status = account.recordAccount(username,name,surname,number,email,password,confirmPassword,pathProfile);
        registerLabel.setText(status);

        db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM user.users;");


        String sql = String.format("INSERT INTO users (username,name,surname,email,password) VALUES('%s','%s','%s','%s','%s');",name,surname,username,email,password);

        if(status.equals("P")){
            try{
                db.getUpdate(sql);
                com.github.saacsos.FXRouter.goTo("user_login");
            }catch (IOException e) {
                System.err.println("ไปที่หน้า user_login ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }

    }

    @FXML
    public void backToUserLogin(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

    }
}
