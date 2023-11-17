package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.DBConnect;
import ku.cs.model.LawsuitsInformation;
import ku.cs.model.Lawyer;
import ku.cs.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.UserLoginController.user;

public class AdminManageController2 {

    @FXML
    private TableView<Lawyer> lawyerTableView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label sexLabel;
    @FXML
    private Label dateBirthLabel;
    @FXML
    private Label idCardLabel;
    @FXML
    private Label attorneyLicensenumberLabel;
    @FXML
    private Label cardIssueDateLabel;
    @FXML
    private Label cardReplacementDateLabel;
    @FXML
    private Label lawOfficeLabel;
    @FXML
    private Label countyLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label numberLabel;

    @FXML
    private ImageView profile;

    @FXML
    private Integer l_id;






    @FXML
    public void initialize() throws SQLException {
        clearSelectedMember();
        showLawyerTableView();
        handleSelectedListView();
    }

    private void clearSelectedMember() {
        nameLabel.setText("-");
        surnameLabel.setText("-");
        dateBirthLabel.setText("-");
        sexLabel.setText("-");
        idCardLabel.setText("-");
        attorneyLicensenumberLabel.setText("-");
        cardIssueDateLabel.setText("-");
        cardReplacementDateLabel.setText("-");
        lawOfficeLabel.setText("-");
        countyLabel.setText("-");
        emailLabel.setText("-");
        numberLabel.setText("-");

    }


    private void showLawyerTableView() throws SQLException {
        ArrayList<Lawyer> lawyerArrayList = getLawyers();

        lawyerTableView.getColumns().clear();

        TableColumn<Lawyer, String> lawyerIDColumn = new TableColumn<>("ID");
        lawyerIDColumn.setCellValueFactory(new PropertyValueFactory<>("lawyerID"));
        lawyerTableView.getColumns().add(lawyerIDColumn);

        TableColumn<Lawyer, String> lawyerNameColumn = new TableColumn<>("ชื่อ");
        lawyerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNameLawyer()));
        lawyerTableView.getColumns().add(lawyerNameColumn);

        TableColumn<Lawyer, String> lawyerSurnameColumn = new TableColumn<>("นามสกุล");
        lawyerSurnameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSurnameLawyer()));
        lawyerTableView.getColumns().add(lawyerSurnameColumn);

        TableColumn<Lawyer, String> lawOfficeColumn = new TableColumn<>("สำนักงานทนายความ");
        lawOfficeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLawOffice()));
        lawyerTableView.getColumns().add(lawOfficeColumn);


        lawyerTableView.getItems().addAll(lawyerArrayList);
        lawyerTableView.refresh();
    }

    private static ArrayList<Lawyer> getLawyers() throws SQLException {
        DBConnect db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawyer_information WHERE L_status = '0';");
        ArrayList<Lawyer> lawyerArrayList = new ArrayList<>();

        while (rs.next()) {
            Lawyer law = new Lawyer(
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
                    rs.getString(20),
                    rs.getString(21)
            );
            lawyerArrayList.add(law);
        }
        return lawyerArrayList;
    }



    private void handleSelectedListView() {
        lawyerTableView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Lawyer> observable, Lawyer oldValue, Lawyer newValue) -> {
                    try {
                        showSelectedMember(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }


    private void showSelectedMember(Lawyer lawyer) throws SQLException {
        if (lawyer != null) {

            nameLabel.setText(lawyer.getNameLawyer());
            surnameLabel.setText(lawyer.getSurnameLawyer());
            dateBirthLabel.setText(lawyer.getDateOfBirth());
            sexLabel.setText(lawyer.getSex());
            idCardLabel.setText(lawyer.getIdCard());
            attorneyLicensenumberLabel.setText(lawyer.getAttorneyLicensenumber());
            cardIssueDateLabel.setText(lawyer.getCardIssueDate());
            cardReplacementDateLabel.setText(lawyer.getCardReplacementDate());
            lawOfficeLabel.setText(lawyer.getLawOffice());
            countyLabel.setText(lawyer.getCounty());
            emailLabel.setText(lawyer.getEmail());
            numberLabel.setText(lawyer.getNumber());
            profile.setImage(new Image("file:" + lawyer.getPathProfile(), true));

            l_id = lawyer.getLawyerID();
            System.out.println(lawyer.getLawyerID());

            }

    }


    @FXML
    private void  summitLawyer() {

        DBConnect db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawyer_information ");

        String sql = String.format("UPDATE lawyer_information SET L_status = '1' WHERE  L_id = '%d' " ,l_id);

        try {
            db.getUpdate(sql);
            lawyerTableView.refresh();
            com.github.saacsos.FXRouter.goTo("admin_home_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_home_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void backToAdminHomePage(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า logout ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
