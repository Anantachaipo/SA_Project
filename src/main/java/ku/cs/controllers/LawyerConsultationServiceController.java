package ku.cs.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ku.cs.DBConnect;
import ku.cs.model.LawsuitsInformation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LawyerConsultationServiceController {

    @FXML
    private TableView<LawsuitsInformation> lsTableView;

    @FXML
    private ListView<LawsuitsInformation> lsListListView;

    @FXML
    public void initialize() throws SQLException {
        showTheConsultationRequest();
    }

    private void showTheConsultationRequest() throws SQLException {
        DBConnect db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawsuits_information;");
        ArrayList<LawsuitsInformation> lawsuitsInformationsArrayList = new ArrayList<>();

        while (rs.next()) {
            LawsuitsInformation ls = new LawsuitsInformation(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getInt(6));
            lawsuitsInformationsArrayList.add(ls);
            System.out.println(rs.getString(2));

        }

        lsTableView.getColumns().clear();

//        TableColumn<LawsuitsInformation, Integer> lsNameColumn = new TableColumn<>("ชื่อ");
//        lsNameColumn.setCellValueFactory(data -> data.getValue().getlID());
//        lsTableView.getColumns().add(lsNameColumn);
        TableColumn<LawsuitsInformation, Integer> lsNameColumn = new TableColumn<>("ชื่อ");
        lsNameColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getuID()).asObject());
        lsTableView.getColumns().add(lsNameColumn);



        TableColumn<LawsuitsInformation, String> lsTypeColumn = new TableColumn<>("ประเภทคดี");
        lsTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));
        lsTableView.getColumns().add(lsTypeColumn);

        TableColumn<LawsuitsInformation, String> lsDateColumn = new TableColumn<>("วัน - เวลา");
        lsDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
        lsTableView.getColumns().add(lsDateColumn);


        lsTableView.getItems().addAll(lawsuitsInformationsArrayList);
        lsTableView.refresh();
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
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
