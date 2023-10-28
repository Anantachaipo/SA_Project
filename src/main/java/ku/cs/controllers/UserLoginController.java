package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.model.Lawyer;
import ku.cs.model.User;
import ku.cs.service.Account;

import java.io.IOException;

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

    @FXML
    public void login () throws IOException{
        String userName = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        loginLabel.setText("ไม่สามารถ login ได้ โปรดลองอีกครั้ง");
        user = account.checkUserName(userName,password);
        if (user != null) {
            try{
                com.github.saacsos.FXRouter.goTo("user_home_page");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า ีuser_menu ไม่ได้");
                System.err.println("ตรวจสอบความถูกต้องของ username และ password");
                System.err.println("ให้ตรวจสอบการกำหนด router");
            }
        }else if (user instanceof Lawyer) {
            lawyer = (Lawyer) user;
            try {
                com.github.saacsos.FXRouter.goTo("lawyer_home_page");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า Lawyer_menu ไม่ได้");
                System.err.println("ให้ตรวจสอบ Username หรือ Password");
                System.err.println("ให้ตรวจสอบการกำหนด route");
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
    public void goToUserRegister(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
