package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

import static ku.cs.controllers.LawyerLoginController.lawyer;


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
//        System.out.println(lawyer);
//        System.out.println(lawyer.getUsernameLawyer());
//
//        int notice = lawyer.getCountConsult();
//
//        usernameLabel.setText(lawyer.getUsernameLawyer());
//        nameLabel.setText(lawyer.getNameLawyer());
//        surnameLabel.setText(lawyer.getSurnameLawyer());
//        attorneyLicensenumberLabel.setText(lawyer.getAttorneyLicensenumber());
//        lawOfficeLabel.setText(lawyer.getLawOffice());
//        countyLabel.setText(lawyer.getCounty());
//
//        if(notice == 0){
//            noticeLabel.setText("");
//        }else{
//            noticeLabel.setText(String.format("มีการแจ้งเตือน %d รายการ", notice));
//        }
//        profile.setImage(new Image("file:" + lawyer.getPathProfile(), true));



    }




}
