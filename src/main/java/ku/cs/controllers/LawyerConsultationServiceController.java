package ku.cs.controllers;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.DBConnect;
import ku.cs.model.LawsuitsInformation;
import ku.cs.model.Lawyer;
import ku.cs.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static ku.cs.controllers.LawyerLoginController.lawyer;
import static ku.cs.controllers.UserLoginController.user;


public class LawyerConsultationServiceController {

    @FXML
    private TableView<LawsuitsInformation> lawsuitsInformationTableView;

    @FXML
    private Label nameuserLabel;
    @FXML
    private Label surnameLabel;

    @FXML
    private Label nameLawsuitsLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private TextArea informationTextArea;
    @FXML
    private Label dateLabel;



    private User user;
    private DBConnect db;


    private Integer uid ;
    private Integer lawsuitsId;


    @FXML
    public void initialize() throws SQLException {
        clearSelectedMember();
        showLawsuitsInformationTableView();
        handleSelectedListView();

    }
    private void clearSelectedMember() {
        nameuserLabel.setText("-");
        surnameLabel.setText("-");
        nameLawsuitsLabel.setText("-");
        typeLabel.setText("-");
        informationTextArea.setText("-");
        dateLabel.setText("-");
    }

    private void showLawsuitsInformationTableView() throws SQLException {
        ArrayList<LawsuitsInformation> lawsuitsArrayList = getLawsuitsInformations();

        lawsuitsInformationTableView.getColumns().clear();

        TableColumn<LawsuitsInformation, String> lawsuitsIDColumn = new TableColumn<>("ID");
        lawsuitsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lawsuitsInformationTableView.getColumns().add(lawsuitsIDColumn);


        TableColumn<LawsuitsInformation, String> lawsuitsNameColumn = new TableColumn<>("ชื่อ");
        lawsuitsNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        lawsuitsInformationTableView.getColumns().add(lawsuitsNameColumn);

        TableColumn<LawsuitsInformation, String> lawsuitsTypeColumn = new TableColumn<>("ประภทคดี");
        lawsuitsTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));
        lawsuitsInformationTableView.getColumns().add(lawsuitsTypeColumn);

        TableColumn<LawsuitsInformation, String>  lawsuitsDateColumn = new TableColumn<>("วัน/เดือน/ปี");
        lawsuitsDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
        lawsuitsInformationTableView.getColumns().add(lawsuitsDateColumn);

        TableColumn<LawsuitsInformation, String> lawsuitsStatusColumn = new TableColumn<>("สถานะ");
        lawsuitsStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        lawsuitsInformationTableView.getColumns().add(lawsuitsStatusColumn);

        lawsuitsInformationTableView.getItems().addAll(lawsuitsArrayList);
        lawsuitsInformationTableView.refresh();
    }

    private static ArrayList<LawsuitsInformation> getLawsuitsInformations() throws SQLException {
        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.lawsuits_information WHERE LS_status = 'A' AND U_id IS NOT NULL AND L_id IS NOT NULL", lawyer.getLawyerID());
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

    private void handleSelectedListView() {
        lawsuitsInformationTableView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends LawsuitsInformation> observable, LawsuitsInformation oldValue, LawsuitsInformation newValue) -> {
                    try {
                        showSelectedMember(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }


    private void showSelectedMember(LawsuitsInformation lawsuitsInformation) throws SQLException {
        if (lawsuitsInformation != null) {

            nameLawsuitsLabel.setText(lawsuitsInformation.getName());
            typeLabel.setText(lawsuitsInformation.getType());
            informationTextArea.setText(lawsuitsInformation.getInformation());
//            informationLabel.setText(lawsuitsInformation.getInformation());
            dateLabel.setText(lawsuitsInformation.getDate());

            Integer U_id = lawsuitsInformation.getuID();

            DBConnect db = new DBConnect();
            ResultSet rs = null;
            String sql = String.format("SELECT * FROM mydb.User_information WHERE U_id = '%d'",lawsuitsInformation.getuID());
            rs = db.getConnect(sql);

            if(rs.next()) {
                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));
                rs.close();

                nameuserLabel.setText(user.getAccountName());
                surnameLabel.setText(user.getSurname());
                lawsuitsId = lawsuitsInformation.getId();
                System.out.println(lawsuitsInformation.getId());


            }

        }
    }
    @FXML
    private void  SubmitLawsuitDetails() {

        DBConnect db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawsuits_information ");

        String sql = String.format("UPDATE lawsuits_information SET LS_status = 'B' WHERE  LS_id = '%d' " ,lawsuitsId);

        try {
            db.getUpdate(sql);
            com.github.saacsos.FXRouter.goTo("lawyer_consultation_service2");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า lawyer_home_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }









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
            System.err.println("ไปที่หน้า lawyer_home_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
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

    @FXML
    public void goToConsult(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_consultation_service2");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า lawyer_consultation_service ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
