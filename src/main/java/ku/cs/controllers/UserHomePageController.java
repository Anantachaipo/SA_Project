package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.scene.text.Text;
import ku.cs.model.Lawyer;

import ku.cs.DBConnect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//
//import javax.swing.text.html.ImageView;
//import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


import static ku.cs.controllers.UserLoginController.user;

public class UserHomePageController {

    @FXML
    private Text usernameText;
    @FXML
    private Text nameText;
    @FXML
    private Text surnameText;
    @FXML
    private Label countLawsuitsLabel;
    @FXML
    private Label countLawyerLabel;
    @FXML
    private ImageView profile;

    @FXML
    private ListView<Lawyer> lawyerListListView;


    private DBConnect db;


    @FXML
    public void initialize() throws SQLException {
        System.out.println(user);
        System.out.println(user.getUsername());
        usernameText.setText(user.getUsername());
        nameText.setText(user.getAccountName());
        surnameText.setText(user.getSurname());
        profile.setImage(new Image("file:" + user.getPathProfile(), true));
        showLabel();
    }





    private void showLabel() {
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = null;
            ResultSet rs2 = null;
            String sql = String.format("SELECT * FROM mydb.lawyer_information ;");
            String sql2 = String.format("SELECT * FROM mydb.lawsuits_information ;");
            rs = db.getConnect(sql);
            Integer i = 0 ;
            ArrayList<Lawyer> lawyerArrayList = new ArrayList<>();
            while (rs.next()) {
                i += 1;
            }
            rs2 = db.getConnect(sql2);
            Integer j = 0;
            while (rs2.next()){
                j += 1;
            }
            countLawyerLabel.setText(Integer.toString(i));
            countLawsuitsLabel.setText(Integer.toString(j));
            System.out.println(i + " - " + j);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }


    @FXML
    public void goToSettingsUser(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_settings");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToConsultLawyer(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_consult_lawyer");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToUserWarn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_warn");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToUserHistory(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_history");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToSearchLawyerPage(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("search_lawyer");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า search ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }




}

