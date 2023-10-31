package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.DBConnect;
import ku.cs.model.Lawyer;
import ku.cs.model.LawyerList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchLawyerController {
    @FXML
    private ListView<LawyerList> lawyerListView ;
    @FXML
    private Label nameLabel ;
    public static ResultSet rs;
    @FXML
    public void initialize(){

        nameLabel.setText(Lawyer);

        showLawyer();
        System.out.println(L);

    }
    public void showLawyer(){
        LawyerList lawyerList = new LawyerList() ;
        try{
            DBConnect db = new DBConnect();
            ResultSet rs = null;
            String sql = String.format("SELECT L_name, L_surname, L_sex, L_attorneyLicensenumber, L_lawOffice, L_county, L_caseAptitude FROM lawyer_information");
            rs = db.getConnect(sql);
            ArrayList<LawyerList> arr = new ArrayList<>() ;
            while (rs.next()){
                arr.add(new LawyerList(
                        rs.getString(3),
                        rs.getString(4), rs.getString(5),
                        rs.getString(10),
                        rs.getString(14),
                        rs.getString(15), rs.getString(16)));
            }
            lawyerListView.getItems().addAll(arr);
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
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
    @FXML
    public void goToHomePage(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_home_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
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

}
