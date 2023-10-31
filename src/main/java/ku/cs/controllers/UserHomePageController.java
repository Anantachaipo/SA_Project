package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ku.cs.model.Lawyer;
import ku.cs.model.User;
import ku.cs.service.Account;
import ku.cs.DBConnect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private ImageView profile;

    private DBConnect db;
    private Account account = new Account();

    @FXML
    public void initialize() throws SQLException {
        System.out.println(user);
        System.out.println(user.getUsername());
        usernameText.setText(user.getUsername());
        nameText.setText(user.getAccountName());
        surnameText.setText(user.getSurname());
        profile.setImage(new Image("file:" + user.getPathProfile(), true));


//
//        DBConnect db = new DBConnect();
//        ResultSet rs = null;
//        String sql = String.format("SELECT * FROM mydb.lawyer_information ");
//        rs = db.getConnect(sql);
//
//        if(rs.next()){
//            user = new User(
//                    rs.getString(2),
//                    rs.getString(3),
//                    rs.getString(4),
//                    rs.getString(5),
//                    rs.getString(6),
//                    rs.getString(7),
//                    rs.getString(8));
//            rs.close();
//            System.out.println("login successful");
//            System.out.println("Current user = " + user.toString());
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
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


//    @FXML
//    public void goTo(ActionEvent actionEvent) throws SQLException {
//
//        db = new DBConnect();
//        ResultSet rs = db.getConnect("SELECT * FROM user.users;");
//        rs.next();
//        String name = rs.getString("name");
//        username.setText(name);
//
//
//    }



}

