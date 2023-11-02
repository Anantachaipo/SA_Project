package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LawyerWarnController {
    @FXML
    public void goToLawyerConsultService(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_consultation_service");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToLawyerHistory(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_history");
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
}
