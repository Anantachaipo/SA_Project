package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.DBConnect;
import ku.cs.model.LawsuitsInformation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.LawyerLoginController.lawyer;
import static ku.cs.controllers.UserLoginController.user;

public class LawyerHistoryController {


    @FXML
    private TableView<LawsuitsInformation> lawsuitsInformationTableView1;
    @FXML
    private TableView<LawsuitsInformation> lawsuitsInformationTableView2;

    @FXML
    public void initialize() throws SQLException {

        showLawsuitsInformationTableView1();
        showLawsuitsInformationTableView2();
    }



    private void showLawsuitsInformationTableView1() throws SQLException {
        ArrayList<LawsuitsInformation> lawsuitsArrayList = getLawsuitsInformations();

        lawsuitsInformationTableView1.getColumns().clear();

        TableColumn<LawsuitsInformation, String> lawsuitsIDColumn = new TableColumn<>("ID");
        lawsuitsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lawsuitsInformationTableView1.getColumns().add(lawsuitsIDColumn);


        TableColumn<LawsuitsInformation, String> lawsuitsNameColumn = new TableColumn<>("ชื่อ");
        lawsuitsNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        lawsuitsInformationTableView1.getColumns().add(lawsuitsNameColumn);

        TableColumn<LawsuitsInformation, String> lawsuitsTypeColumn = new TableColumn<>("ประภทคดี");
        lawsuitsTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));
        lawsuitsInformationTableView1.getColumns().add(lawsuitsTypeColumn);

        TableColumn<LawsuitsInformation, String>  lawsuitsDateColumn = new TableColumn<>("วัน/เดือน/ปี");
        lawsuitsDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
        lawsuitsInformationTableView1.getColumns().add(lawsuitsDateColumn);

//        TableColumn<LawsuitsInformation, String> lawsuitsStatusColumn = new TableColumn<>("สถานะ");
//        lawsuitsStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
//        lawsuitsInformationTableView1.getColumns().add(lawsuitsStatusColumn);

        lawsuitsInformationTableView1.getItems().addAll(lawsuitsArrayList);
        lawsuitsInformationTableView1.refresh();
    }

    private static ArrayList<LawsuitsInformation> getLawsuitsInformations() throws SQLException {
        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.lawsuits_information WHERE L_id = %d;", lawyer.getLawyerID());
        ResultSet rs = db.getConnect(sql);
        ArrayList<LawsuitsInformation> lawsuitsArrayList = new ArrayList<>();

        while (rs.next()) {
            LawsuitsInformation lawsuitsInformation = new LawsuitsInformation(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getInt(8)

            );
            lawsuitsArrayList.add(lawsuitsInformation);
        }
        return lawsuitsArrayList;
    }
    private void showLawsuitsInformationTableView2() throws SQLException {
        ArrayList<LawsuitsInformation> lawsuitsArrayList = getLawsuitsInformations2();

        lawsuitsInformationTableView2.getColumns().clear();

        TableColumn<LawsuitsInformation, String> lawsuitsIDColumn = new TableColumn<>("ID");
        lawsuitsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lawsuitsInformationTableView2.getColumns().add(lawsuitsIDColumn);


        TableColumn<LawsuitsInformation, String> lawsuitsNameColumn = new TableColumn<>("ชื่อ");
        lawsuitsNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        lawsuitsInformationTableView2.getColumns().add(lawsuitsNameColumn);

        TableColumn<LawsuitsInformation, String> lawsuitsTypeColumn = new TableColumn<>("ประภทคดี");
        lawsuitsTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));
        lawsuitsInformationTableView2.getColumns().add(lawsuitsTypeColumn);

        TableColumn<LawsuitsInformation, String>  lawsuitsDateColumn = new TableColumn<>("วัน/เดือน/ปี");
        lawsuitsDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
        lawsuitsInformationTableView2.getColumns().add(lawsuitsDateColumn);

//        TableColumn<LawsuitsInformation, String> lawsuitsStatusColumn = new TableColumn<>("สถานะ");
//        lawsuitsStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
//        lawsuitsInformationTableView1.getColumns().add(lawsuitsStatusColumn);

        lawsuitsInformationTableView2.getItems().addAll(lawsuitsArrayList);
        lawsuitsInformationTableView2.refresh();
    }

    private static ArrayList<LawsuitsInformation> getLawsuitsInformations2() throws SQLException {
        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.lawsuits_information WHERE L_id = %d AND LS_status = 'C';", lawyer.getLawyerID());
        ResultSet rs = db.getConnect(sql);
        ArrayList<LawsuitsInformation> lawsuitsArrayList = new ArrayList<>();

        while (rs.next()) {
            LawsuitsInformation lawsuitsInformation = new LawsuitsInformation(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getInt(8)

            );
            lawsuitsArrayList.add(lawsuitsInformation);
        }
        return lawsuitsArrayList;
    }

    @FXML
    public void goToBack(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_home_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า lawyer_home_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }



//    // ปุ่มไปหน้าต่างๆ
//    @FXML
//    public void goToLawyerWarn(ActionEvent actionEvent) {
//        try {
//            com.github.saacsos.FXRouter.goTo("lawyer_warn");
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า help ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
//
//    @FXML
//    public void goToLawyerHomePage(ActionEvent actionEvent) {
//        try {
//            com.github.saacsos.FXRouter.goTo("lawyer_home_page");
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า help ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
//    @FXML
//    public void goToLawyerConsultService(ActionEvent actionEvent) {
//        try {
//            com.github.saacsos.FXRouter.goTo("lawyer_consultation_service");
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า help ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }

}

