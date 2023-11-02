package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.DBConnect;
import ku.cs.model.Lawyer;
import com.github.saacsos.FXRouter;
import ku.cs.model.LawyerList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.UserLoginController.user;

public class SearchLawyerController {
    @FXML
    private ListView<Lawyer> lawyerListListView;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Lawyer> lawyerTableView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label attorneyLicenseNumberLabel;
    @FXML
    private Label lawOfficeLabel;
    @FXML
    private Label countyLabel;

    @FXML
    private ImageView profileLawyerImage;

    private Lawyer lawyer;
    private LawyerList lawyerList;

    @FXML
    public void initialize() throws SQLException {

        clearSelectedMember();
        handleSelectedListView();
        showLawyerTableView();
    }
    @FXML
    private void handleToRefresh() throws SQLException {
        lawyerTableView.getItems().clear();
        clearSelectedMember();
        showLawyerTableView();
    }

    private void showLawyerTableView() throws SQLException {
        DBConnect db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawyer_information;");
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
                    rs.getString(17)

            );
            lawyerArrayList.add(law);
        }

        lawyerTableView.getColumns().clear();

        TableColumn<Lawyer, String> lawyerNameColumn = new TableColumn<>("ชื่อ");
        lawyerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNameLawyer()));
        lawyerTableView.getColumns().add(lawyerNameColumn);

        TableColumn<Lawyer, String> lawyerSurnameColumn = new TableColumn<>("นามสกุล");
        lawyerSurnameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSurnameLawyer()));
        lawyerTableView.getColumns().add(lawyerSurnameColumn);

        TableColumn<Lawyer, String> attorneyLicenseNumberColumn = new TableColumn<>("หมายเลขใบอนุญาตทนาย");
        attorneyLicenseNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAttorneyLicensenumber()));
        lawyerTableView.getColumns().add(attorneyLicenseNumberColumn);

        TableColumn<Lawyer, String> lawOfficeColumn = new TableColumn<>("สำนักงานทนายความ");
        lawOfficeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLawOffice()));
        lawyerTableView.getColumns().add(lawOfficeColumn);

        TableColumn<Lawyer, String> countyColumn = new TableColumn<>("จังหวัด");
        countyColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCounty()));
        lawyerTableView.getColumns().add(countyColumn);

        lawyerTableView.getItems().addAll(lawyerArrayList);
        lawyerTableView.refresh();
    }
    private void handleSelectedListView() {
        lawyerTableView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Lawyer> observable, Lawyer oldValue, Lawyer newValue) -> {
                    showSelectedMember(newValue);
                }
        );
    }
    private void showSelectedMember(Lawyer lawyer) {
        if (lawyer != null) {
            nameLabel.setText(lawyer.getNameLawyer());
            surnameLabel.setText(lawyer.getSurnameLawyer());
            attorneyLicenseNumberLabel.setText(lawyer.getAttorneyLicensenumber());
            lawOfficeLabel.setText(lawyer.getLawOffice());
            countyLabel.setText(lawyer.getCounty());
            profileLawyerImage.setImage(new Image("file:" + lawyer.getPathProfile(), true));
        }
    }

    @FXML
    public void searchLawyer(ActionEvent actionEvent) {
        String text = searchTextField.getText();
        lawyerTableView.getItems().clear();
        showSearchLawyerList(text);
    }




    @FXML
    public void goToSearchLawyerPage(ActionEvent actionEvent) {
        String text = searchTextField.getText();
        lawyerTableView.getItems().clear();
        showSearchLawyerList(text);
    }

    private void showSearchLawyerList(String searchString) {
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = db.getConnect("SELECT * FROM mydb.lawyer_information WHERE L_name = '" + searchString + "';");
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
                        rs.getString(17)
                );
                lawyerArrayList.add(law);
            }
            ObservableList<Lawyer> lawyerObservableList = FXCollections.observableArrayList(lawyerArrayList);
            lawyerTableView.setItems(lawyerObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void clearSelectedMember() {
        nameLabel.setText("-");
        surnameLabel.setText("-");
        attorneyLicenseNumberLabel.setText("-");
        lawOfficeLabel.setText("-");
        countyLabel.setText("-");
    }

    @FXML
    public void goToConsultLawyer(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_consult_lawyer");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void goToUserWarn(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_warn");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void goToUserHistory(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_history");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void goToHomePage(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_home_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void goToSettingsUser(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_settings");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
