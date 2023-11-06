package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.DBConnect;
import ku.cs.model.LawsuitsInformation;


import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.LawyerLoginController.lawyer;


public class LawyerConsultationServiceController2 {

    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;



    @FXML
    private TextArea informationTextArea;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private TableView<LawsuitsInformation> lawsuitsInformationTableView;

    Integer ls_id;


    @FXML
    public void initialize() throws SQLException {
        showLawsuitsInformationTableView();
        handleSelectedListView();
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
        String sql = String.format("SELECT * FROM mydb.lawsuits_information WHERE LS_status = 'B' AND L_id = %d ", lawyer.getLawyerID());
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
            informationTextArea.setText(lawsuitsInformation.getInformation());
            ls_id = lawsuitsInformation.getId();


        }

    }


    @FXML
    private void  SubmitLawsuitDetails() {

        String comment = commentTextArea.getText();

        DBConnect db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawsuits_information INNER JOIN mydb.comment_lawsuits ON lawsuits_information.LS_id = comment_lawsuits.LS_id;");
        String sql = String.format("INSERT INTO comment_lawsuits (C_lawsuits_comment,LS_id)  VALUES('%s','%d') ",comment,ls_id);
        String sql2 = String.format("UPDATE lawsuits_information SET LS_status = 'C' WHERE  LS_id = '%d' ",ls_id);

        try {
            db.getUpdate(sql);
            db.getUpdate(sql2);
            com.github.saacsos.FXRouter.goTo("lawyer_consultation_service2");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า lawyer_home_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }














    @FXML
    public void goToBack(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_consultation_service");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า lawyer_consultation_service ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
