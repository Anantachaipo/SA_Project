package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LawyerHistoryController {
    // ปุ่มไปหน้าต่างๆ
    @FXML
    public void goToLawyerWarn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_warn");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void goToLawyerHomePage(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_home_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToLawyerConsultService(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_consultation_service");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}

