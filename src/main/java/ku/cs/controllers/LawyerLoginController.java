package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.DBConnect;
import ku.cs.model.Lawyer;
import ku.cs.model.User;
import ku.cs.service.Account;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;

public class LawyerLoginController {
    public static Lawyer lawyer;
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Label loginLabel;


    public static User user ;

    private Account account = new Account();

    public boolean loginUser() {
        String userName = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        loginLabel.setText("ไม่สามารถ login ได้ โปรดลองอีกครั้ง");
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = null;
            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_username = '%s' AND L_password = '%s'",userName,password);
            rs = db.getConnect(sql);
            return rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    @FXML
    public void login () throws IOException{
        if (loginUser()) {
            try {
                com.github.saacsos.FXRouter.goTo("lawyer_home_page");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า ีlawyer_home_page ไม่ได้");
                System.err.println("ตรวจสอบความถูกต้องของ username และ password");
                System.err.println("ให้ตรวจสอบการกำหนด router");
            }
        } else {
            JOptionPane.showMessageDialog(null,"login false");
        }
    }


    @FXML
    public void backToUserLogin(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToLawyerRegister(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}

