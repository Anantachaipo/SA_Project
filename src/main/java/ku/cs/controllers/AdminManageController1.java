package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.DBConnect;
import ku.cs.model.Lawyer;
import ku.cs.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ku.cs.controllers.UserLoginController.user;

public class AdminManageController1 {


    private Integer uid ;
    private Integer lid ;


    @FXML
    private TableView<Lawyer> lawyerTableView;
    @FXML
    private TableView<User> userTableView;


    @FXML
    public void initialize() throws SQLException {
        lawyerTableView.refresh();
        userTableView.refresh();
        showLawyer();
        showUser();
        handleSelectedUser();
        handleSelectedLawyer();
    }

//    @FXML
//    private void handleToRefresh() throws SQLException {
//        lawyerTableView.getItems().clear();
//        userTableView.getItems().clear();
//    }

    private void showLawyer() throws SQLException {
        ArrayList<Lawyer> lawyer = getLawyer();

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

        TableColumn<Lawyer, String> attorneyLicenseNumberColumn = new TableColumn<>("หมายเลขใบอนุญาตทนาย");
        attorneyLicenseNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAttorneyLicensenumber()));
        lawyerTableView.getColumns().add(attorneyLicenseNumberColumn);

        TableColumn<Lawyer, String> lawOfficeColumn = new TableColumn<>("สำนักงานทนายความ");
        lawOfficeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLawOffice()));
        lawyerTableView.getColumns().add(lawOfficeColumn);



        lawyerTableView.getItems().addAll(lawyer);
        lawyerTableView.refresh();
    }

    private static ArrayList<Lawyer> getLawyer() throws SQLException {
        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.lawyer_information ");
        ResultSet rs = db.getConnect(sql);
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

    private void showUser() throws SQLException {
        ArrayList<User> user = getUser();

        userTableView.getColumns().clear();

        TableColumn<User, String> userIDColumn = new TableColumn<>("ID");
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTableView.getColumns().add(userIDColumn);

        TableColumn<User, String> userNameColumn = new TableColumn<>("ชื่อ");
        userNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccountName()));
        userTableView.getColumns().add(userNameColumn);

        TableColumn<User, String> surnameColumn = new TableColumn<>("นามสกุล");
        surnameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSurname()));
        userTableView.getColumns().add(surnameColumn);

        userTableView.getItems().addAll(user);
        userTableView.refresh();
    }

    private static ArrayList<User> getUser() throws SQLException {
        DBConnect db = new DBConnect();
        String sql = String.format("SELECT * FROM mydb.user_information ");
        ResultSet rs = db.getConnect(sql);
        ArrayList<User> userArrayList = new ArrayList<>();

        while (rs.next()) {
            user = new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8));
            userArrayList.add(user);
        }
        return userArrayList;
    }



    private void handleSelectedUser() {
        userTableView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends User> observable, User oldValue, User newValue) -> {
                    try {
                        showSelectedMember(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }


    private void showSelectedMember(User user) throws SQLException {
        if (user != null) {
            uid = user.getId();
            System.out.println(uid);
        }

    }
    private void handleSelectedLawyer() {
        lawyerTableView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Lawyer> observable, Lawyer oldValue, Lawyer newValue) -> {
                    try {
                        showSelectedMemberLawyer(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }


    private void showSelectedMemberLawyer(Lawyer lawyer) throws SQLException {
        lid = lawyer.getLawyerID();
        System.out.println(lid);

    }

    @FXML
    public void deleteUser(ActionEvent actionEvent) {
        // สร้างการเชื่อมต่อกับฐานข้อมูล
        DBConnect db = new DBConnect();

        try {
            // ดึงข้อมูลลักษณะของแถวที่คุณต้องการลบ
            String selectSql = String.format("SELECT * FROM mydb.user_information WHERE U_id = %d", uid);
            ResultSet rs = db.getConnect(selectSql);

            // ตรวจสอบว่ามีแถวที่ตรงกับ uid หรือไม่
            if (rs.next()) {
                // อัปเดตค่า U_id ในตาราง lawsuits_information เป็นค่าที่เหมาะสม (เช่น null)
                String updateSql = String.format("UPDATE mydb.lawsuits_information SET U_id = NULL WHERE U_id = %d", uid);
                db.getUpdate(updateSql);

                // ลบแถวที่ตรงกับ uid จาก user_information
                String deleteSql = String.format("DELETE FROM mydb.user_information WHERE U_id = %d", uid);
                db.getUpdate(deleteSql);
                userTableView.refresh();
            } else {
                // ไม่พบแถวที่ตรงกับ uid
                System.out.println("ไม่พบข้อมูลผู้ใช้ที่ต้องการลบ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void deleteLawyer(ActionEvent actionEvent) {
        // สร้างการเชื่อมต่อกับฐานข้อมูล
        DBConnect db = new DBConnect();

        try {
            // ดึงข้อมูลลักษณะของแถวที่คุณต้องการลบ
            String selectSql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_id = %d", lid);
            ResultSet rs = db.getConnect(selectSql);

            // ตรวจสอบว่ามีแถวที่ตรงกับ lid หรือไม่
            if (rs.next()) {
                // ลบแถวที่ตรงกับ lid
                String deleteSql = String.format("DELETE FROM mydb.lawyer_information WHERE L_id = %d", lid);
                db.getUpdate(deleteSql);
                lawyerTableView.refresh();
                com.github.saacsos.FXRouter.goTo("admin");
            } else {
                // ไม่พบแถวที่ตรงกับ lid
                System.out.println("ไม่พบข้อมูลทนายความที่ต้องการลบ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void goToFirstPage(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า first_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void backToAdminHomePage(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_home_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_home_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
