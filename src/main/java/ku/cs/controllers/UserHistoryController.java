package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.DBConnect;
import ku.cs.model.CommentLawsuits;
import ku.cs.model.LawsuitsInformation;
import ku.cs.model.Lawyer;


import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.UserLoginController.user;

public class UserHistoryController {


    @FXML
    private Text nameLawsuitText;
    @FXML
    private Text typsLawsuitsText;
    @FXML
    private TextArea informatonTextArea;



    @FXML
    private Text nameLawyerText;
    @FXML
    private Text surnameLawyerText;
    @FXML
    private Text attorneyLicensenumberText;
    @FXML
    private Text lawOfficeText;
    @FXML
    private Text numberText;
    @FXML
    private Text emailText;



    @FXML
    private TextArea commentLawsuitsText;






    @FXML
    private TableView<LawsuitsInformation> lawsuitsInformationTableView1;
    @FXML
    private TableView<LawsuitsInformation> lawsuitsInformationTableView2;



    @FXML
    public void initialize() throws SQLException {
        clearSelectedMember();
        showLawsuitsInformationTableView1();
        showLawsuitsInformationTableView2();
        handleSelectedListView();



    }

    private void clearSelectedMember() {
        nameLawyerText.setText("-");
        typsLawsuitsText.setText("-");
        informatonTextArea.setText("-");
        nameLawsuitText.setText("-");

        surnameLawyerText.setText("-");
        attorneyLicensenumberText.setText("-");
        lawOfficeText.setText("-");
        numberText.setText("-");
        emailText.setText("-");
        commentLawsuitsText.setText("-");
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

        TableColumn<LawsuitsInformation, String> lawsuitsStatusColumn = new TableColumn<>("สถานะ");
        lawsuitsStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        lawsuitsInformationTableView1.getColumns().add(lawsuitsStatusColumn);

        lawsuitsInformationTableView1.getItems().addAll(lawsuitsArrayList);
        lawsuitsInformationTableView1.refresh();
    }

    private static ArrayList<LawsuitsInformation> getLawsuitsInformations() throws SQLException {
        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.lawsuits_information WHERE U_id = %d;", user.getId());
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

        TableColumn<LawsuitsInformation, String> lawsuitsStatusColumn = new TableColumn<>("สถานะ");
        lawsuitsStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        lawsuitsInformationTableView2.getColumns().add(lawsuitsStatusColumn);

        lawsuitsInformationTableView2.getItems().addAll(lawsuitsArrayList);
        lawsuitsInformationTableView2.refresh();
    }

    private static ArrayList<LawsuitsInformation> getLawsuitsInformations2() throws SQLException {
        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.lawsuits_information WHERE U_id = %d AND LS_status = 'C';", user.getId());
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
        lawsuitsInformationTableView2.getSelectionModel().selectedItemProperty().addListener(
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

            nameLawsuitText.setText(lawsuitsInformation.getName());
            typsLawsuitsText.setText(lawsuitsInformation.getType());
            informatonTextArea.setText(lawsuitsInformation.getInformation());


            Integer L_id = lawsuitsInformation.getlID();

            DBConnect db = new DBConnect();
            ResultSet rs = null;
            ResultSet rs2 = null;

            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_id = '%d'",L_id);
            String sql2 = String.format("SELECT * FROM mydb.comment_lawsuits WHERE LS_id = '%d'", lawsuitsInformation.getId());
            rs = db.getConnect(sql);

            if(rs.next()) {
                Lawyer lawyer = new Lawyer(
                        rs.getInt(1),
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
                        rs.getInt(17),
                        rs.getInt(18),
                        rs.getInt(19),
                        rs.getString(20));
                rs.close();

                nameLawyerText.setText(lawyer.getNameLawyer());
                surnameLawyerText.setText(lawyer.getSurnameLawyer());
                lawOfficeText.setText(lawyer.getLawOffice());
                attorneyLicensenumberText.setText((lawyer.getAttorneyLicensenumber()));
                numberText.setText(lawyer.getNumber());
                emailText.setText(lawyer.getEmail());

            }
            rs2 = db.getConnect(sql2);
            if (rs2.next()){
                CommentLawsuits commentLawsuits = new CommentLawsuits(
                        rs2.getInt(1),
                        rs2.getString(2),
                        rs2.getInt(3));
                rs2.close();

                commentLawsuitsText.setText(commentLawsuits.getC_lawsuitsComment());

            }

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
}
