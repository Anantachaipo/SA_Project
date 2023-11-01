package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import ku.cs.DBConnect;
import ku.cs.model.Lawyer;
import ku.cs.service.Account;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.UserLoginController.user;

public class SearchLawyerController {
    private DBConnect db;
    private Account account = new Account();
    @FXML
    private ListView<Lawyer> lawyerListListView;
    @FXML
    private TextField searchTextField;







    @FXML
    public void initialize() throws SQLException {
        showLawyerList();
    }


    private void showLawyerList() {
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = null;
            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_county = 'กรุงเทพมหานคร (Bangkok)';");
            rs = db.getConnect(sql);

            ArrayList<Lawyer> lawyerArrayList = new ArrayList<>();

            while (rs.next()) {
                Lawyer law = new Lawyer(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17)
                );
                lawyerArrayList.add(law);
            }

            ObservableList<Lawyer> lawyerObservableList = FXCollections.observableArrayList(lawyerArrayList);
            lawyerListListView.setItems(lawyerObservableList);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }
    private void showSearchLawyerList(String s) {
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = null;
            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_name = '%s';",s);
            rs = db.getConnect(sql);
            ArrayList<Lawyer> lawyerArrayList = new ArrayList<>();

            while (rs.next()) {
                Lawyer law = new Lawyer(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17)
                );
                lawyerArrayList.add(law);
            }

            ObservableList<Lawyer> lawyerObservableList = FXCollections.observableArrayList(lawyerArrayList);
            lawyerListListView.setItems(lawyerObservableList);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }


    @FXML
    public void goToSearchLawyerPage(ActionEvent actionEvent) {
        String text = searchTextField.getText();
        lawyerListListView.getItems().clear();
        showSearchLawyerList(text);

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
