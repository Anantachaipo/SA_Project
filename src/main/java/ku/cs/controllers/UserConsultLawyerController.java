package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import ku.cs.DBConnect;
import ku.cs.model.Lawyer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ku.cs.controllers.UserLoginController.user;


public class UserConsultLawyerController {

    //ส่วนของ user
    @FXML
    private Label usernameLabel;

    //ส่วนของ lawyer
    @FXML
    private Label lawyernameLabel;
    @FXML
    private Label lawyersurnameLabel;

    @FXML
    private Label lawOfficeLabel;
    @FXML
    private Label countyLabel;
    @FXML
    private ImageView profileLawyer;


    @FXML
    private MenuButton menuButton ;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea informationTextArea ;
    @FXML
    private DatePicker datePicker;

    public DBConnect db;
    private Lawyer selectedLawyer;
    private Integer l_id;
    private String status = "A";


    @FXML
    public void initialize(){
        Lawyer selectedLawyer = SearchLawyerController.getSelectedLawyer();
        System.out.println("U_id = "+user.getId());
        l_id = selectedLawyer.getLawyerID();
        System.out.println("L_id = "+selectedLawyer.getLawyerID());

        if (selectedLawyer != null) {
            // นำข้อมูลจาก selectedLawyer มาใช้
            String lawyerName = selectedLawyer.getNameLawyer();
            String lawyerSurname = selectedLawyer.getSurnameLawyer();
            String attoneynumber = selectedLawyer.getAttorneyLicensenumber();
            String lawOffice = selectedLawyer.getLawOffice();
            String county = selectedLawyer.getCounty();
            String image = selectedLawyer.getPathProfile();

            lawyernameLabel.setText(lawyerName);
            lawyersurnameLabel.setText(lawyerSurname);
            lawOfficeLabel.setText(lawOffice);
            countyLabel.setText(county);
            profileLawyer.setImage(new Image("file:" + selectedLawyer.getPathProfile(), true));

            usernameLabel.setText(user.getUsername());

            System.out.println(l_id);
            System.out.println(lawyerName);
            System.out.println(lawyerSurname);
            // ... นำข้อมูลอื่น ๆ มาใช้ตามที่ต้องการ
        }
    }

    @FXML
    private void  SubmitLawsuitDetails() throws SQLException {
        Integer u_id = user.getId();
        String name = nameTextField.getText();
        String type = menuButton.getText();
        String information = informationTextArea.getText();
        LocalDate localDate = datePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = localDate.format(formatter);

        DBConnect db1 = new DBConnect();

        String sql1 = String.format("SELECT * FROM lawsuits_information WHERE LS_date = '%s'",date);
        ResultSet result = db1.getConnect(sql1);

        if (result.next()) {
//            registerMessageLabel.setText("Username is already used");
            System.out.println("ffff");

            result.close();
//                pst.close();
            return;
        }
        else {
//            registerMessageLabel.setText("");
            System.out.println("dddd");
        }
        db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawsuits_information;");

        String sql = String.format("INSERT INTO lawsuits_information (LS_name,LS_type,LS_information,LS_date,LS_status,U_id,L_id) VALUES('%s','%s','%s','%s','%s',%d,'%d') ;", name,type, information, date,status,u_id,l_id);
        try {
            db.getUpdate(sql);
            com.github.saacsos.FXRouter.goTo("user_history");
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
    public void goToSearchLawyer(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("search_lawyer");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }



//     ตัวเลือกประเภทคดีต่างๆ
    @FXML
    public void handleToType1(){
        menuButton.setText("คดีอาชญากรรม");
    }
    @FXML
    public void handleToType2(){
        menuButton.setText("คดีเชิงอาชญากรรม");
    }
    @FXML
    public void handleToType3(){
        menuButton.setText("คดีอาศัย");
    }
    @FXML
    public void handleToType4(){
        menuButton.setText("คดีแพ่ง");
    }
    @FXML
    public void handleToType5(){
        menuButton.setText("คดีที่เกี่ยวกับทรัพย์สินทางปัญญา");
    }
    @FXML
    public void handleToType6(){
        menuButton.setText("คดีด้านสุขภาพ");
    }
    @FXML
    public void handleToType7(){
        menuButton.setText("คดีแรงงาน");
    }
    @FXML
    public void handleToType8(){
        menuButton.setText("คดีที่เกี่ยวกับสิทธิและเสรีภาพ");
    }
    @FXML
    public void handleToType9(){
        menuButton.setText("คดีที่เกี่ยวกับทรัพย์สิน");
    }




}