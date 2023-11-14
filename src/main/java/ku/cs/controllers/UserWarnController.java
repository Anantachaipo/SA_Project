package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import ku.cs.DBConnect;
import ku.cs.model.LawsuitsInformation;
import ku.cs.model.LawsuitsInformationList;
import ku.cs.model.Lawyer;
import ku.cs.model.User;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.UserLoginController.user;

public class UserWarnController {


    @FXML
    private Text usernameLabel;


    @FXML
    private Text nameLabel;
    @FXML
    private Text typeLabel;
    @FXML
    private Text informationLabel;
    @FXML
    private Text statusLabel;


    @FXML
    private Text nameLawyerLabel;
    @FXML
    private Text surnameLawyerLabel;
    @FXML
    private Text lawyerOfficeLabel;


    @FXML
    private TableView<LawsuitsInformation> lawsuitsInformationTableView;
    private Lawyer lawyer;


    @FXML
    public void initialize() throws SQLException {
        clearSelectedMember();
        usernameLabel.setText(user.getUsername());
        showLawsuitsInformationTableView();
        handleSelectedListView();



    }
    private void clearSelectedMember() {
        nameLabel.setText("-");
        typeLabel.setText("-");
        informationLabel.setText("-");
        statusLabel.setText("-");

        nameLawyerLabel.setText("-");
        surnameLawyerLabel.setText("-");
        lawyerOfficeLabel.setText("-");
    }


//    public static LawsuitsInformation getSelectedLawsuits() {
//        return lawsuitsInformation;
//    }
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
        int uid = user.getId();

        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.lawsuits_information WHERE U_id = '%d'", uid);
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

            nameLabel.setText(lawsuitsInformation.getName());
            typeLabel.setText(lawsuitsInformation.getType());
            informationLabel.setText(lawsuitsInformation.getInformation());

            if (lawsuitsInformation.getStatus().equals("A")) {
                statusLabel.setText("รอการตอบรับการปรึกษา");
            } else if (lawsuitsInformation.getStatus().equals("B")) {
                statusLabel.setText("อยู่ระหว่างการปรึกษาทนาย");
            } else if (lawsuitsInformation.getStatus().equals("C")){
                statusLabel.setText("เสร้จสิ้น การแต่งตั้งทนาย");
            }

            Integer L_id = lawsuitsInformation.getlID();

            DBConnect db = new DBConnect();
            ResultSet rs = null;
            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_id = '%d'",L_id);
            rs = db.getConnect(sql);

            if(rs.next()) {
                lawyer = new Lawyer(
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

                nameLawyerLabel.setText(lawyer.getNameLawyer());
                surnameLawyerLabel.setText(lawyer.getSurnameLawyer());
                lawyerOfficeLabel.setText(lawyer.getLawOffice());


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
