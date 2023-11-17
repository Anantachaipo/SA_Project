package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminHomePageController {

    @FXML
    public void goTomanage1(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_manage_1");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_manage_1 ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goTomanage2(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_manage_2");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_manage_2 ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }




    @FXML
    public void logout(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า logout ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
