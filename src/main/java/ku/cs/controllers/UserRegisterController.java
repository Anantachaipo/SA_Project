package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ku.cs.DBConnect;
import ku.cs.service.Account;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;

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
        ResultSet rs = db.getConnect("SELECT * FROM mydb.user_information;");


        String sql = String.format("INSERT INTO user_information (U_username,U_name,U_surname,U_number,U_email,U_password) VALUES('%s','%s','%s','%s','%s','%s');",username,name,surname,number,email,password);

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
