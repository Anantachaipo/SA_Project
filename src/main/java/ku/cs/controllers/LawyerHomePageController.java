package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

import static ku.cs.controllers.LawyerLoginController.lawyer;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LawyerHomePageController {

    @FXML
    private Label noticeLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label attorneyLicensenumberLabel;
    @FXML
    private Label lawOfficeLabel;
    @FXML
    private Label countyLabel;
    @FXML
    private Label countProgressLabel;
    @FXML
    private Label countSuccessLabel;
    @FXML
    private ImageView profile;


    @FXML
    public void initialize() throws SQLException {

        if (lawyer != null) {
            usernameLabel.setText(lawyer.getUsernameLawyer());
            nameLabel.setText(lawyer.getNameLawyer());
            surnameLabel.setText(lawyer.getSurnameLawyer());
            attorneyLicensenumberLabel.setText(lawyer.getAttorneyLicensenumber());
            lawOfficeLabel.setText(lawyer.getLawOffice());
            countyLabel.setText(lawyer.getCounty());
            countProgressLabel.setText(String.valueOf(lawyer.getCountProgress()));
            countSuccessLabel.setText(String.valueOf(lawyer.getCountSuccess()));

            int notice = lawyer.getCountConsult();
            if (notice == 0) {
                noticeLabel.setText("");
            } else {
                noticeLabel.setText(String.format("มีการแจ้งเตือน %d รายการ", notice));
            }

            String profilePath = lawyer.getPathProfile();
            if (profilePath != null) {
                profile.setImage(new Image("file:" + profilePath, true));
            }
        } else {
            // กรณี `lawyer` เป็น null
            usernameLabel.setText("");
            nameLabel.setText("");
            surnameLabel.setText("");
            attorneyLicensenumberLabel.setText("");
            lawOfficeLabel.setText("");
            countyLabel.setText("");
            noticeLabel.setText("");
            profile.setImage(null); // ลบรูปภาพ
        }

    }


    @FXML
    public void  goToLawyerQuestion(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_question");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    // ปุ่มไปหน้าต่างๆ
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
    public void goToLawyerWarn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_warn");
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
