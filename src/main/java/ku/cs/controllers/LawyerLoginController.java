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

    @FXML
    public void login () throws IOException {
        String userName = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        loginLabel.setText("ไม่สามารถ login ได้ โปรดลองอีกครั้ง");
        user = account.checkUserName(userName, password);



        if (user != null) {
            if (user instanceof Lawyer) {
                lawyer = (Lawyer) user;
                try {
                    com.github.saacsos.FXRouter.goTo("lawyer_home_page");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("ไปที่หน้า Lawyer_menu ไม่ได้");
                    System.err.println("ให้ตรวจสอบ Username หรือ Password");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            } else {
                try {
                    com.github.saacsos.FXRouter.goTo("first_page");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("ไปที่หน้า Login ไม่ถูกต้อง");
                    System.err.println("ให้ตรวจสอบ Username หรือ Password");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
//        } else if (user instanceof Lawyer) {
//            lawyer = (Lawyer) user;
//            try {
//                com.github.saacsos.FXRouter.goTo("lawyer_home_page");
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.err.println("ไปที่หน้า Lawyer_menu ไม่ได้");
//                System.err.println("ให้ตรวจสอบ Username หรือ Password");
//                System.err.println("ให้ตรวจสอบการกำหนด route");
//            }
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
    public void goToLawyerRegister(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}

