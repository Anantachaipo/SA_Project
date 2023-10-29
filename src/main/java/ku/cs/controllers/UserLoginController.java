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

public class UserLoginController {

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
            String sql = String.format("SELECT * FROM mydb.user_information WHERE U_username = '%s' AND U_password = '%s'",userName,password);
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
                com.github.saacsos.FXRouter.goTo("user_home_page");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า ีuser_menu ไม่ได้");
                System.err.println("ตรวจสอบความถูกต้องของ username และ password");
                System.err.println("ให้ตรวจสอบการกำหนด router");
            }
        } else {
            JOptionPane.showMessageDialog(null,"login false");
        }
    }








//                if (user instanceof Admin) {
//                    try {
//                        com.github.saacsos.FXRouter.goTo("admin_main");
//                    } catch (IOException e) {
//                        System.err.println("ไปที่หน้า admin_main ไม่ได้");
//                        System.err.println("ให้ตรวจสอบ Username หรือ Password");
//                        System.err.println("ให้ตรวจสอบการกำหนด route");
//                    }
//                } else if (user instanceof Officer) {
//                    officer = (Officer) user;
//                    try {
//                        com.github.saacsos.FXRouter.goTo("officer_main");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        System.err.println("ไปที่หน้า officer_main ไม่ได้");
//                        System.err.println("ให้ตรวจสอบ Username หรือ Password");
//                        System.err.println("ให้ตรวจสอบการกำหนด route");
//                    }
//                } else {
//                    try {
//                        com.github.saacsos.FXRouter.goTo("menu");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        System.err.println("ไปที่หน้า menu ไม่ได้");
//                        System.err.println("ให้ตรวจสอบ Username หรือ Password");
//                        System.err.println("ให้ตรวจสอบการกำหนด route");
//                    }
//                }
//            }else{
//
//                loginLabel.setText("บัญชีของคุณถูกระงับการใช้งาน");
//            }
//
//        }


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
    public void goToUserRegister(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
