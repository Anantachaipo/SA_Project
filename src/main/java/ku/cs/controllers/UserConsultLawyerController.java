package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import ku.cs.DBConnect;
import ku.cs.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controllers.UserLoginController.user;

public class UserConsultLawyerController {
    @FXML
    private MenuButton ls_typeMenuButton ;
    @FXML
    private TextField ls_informationTextField ;
    @FXML
    private TextField u_idTextField ;
    @FXML
    private TextField ls_dateTextField ;
    public DBConnect db;
    @FXML
    public void initialize(){
        System.out.println(user.getId());

    }

    @FXML
    private void   SubmitLawsuitDetails() {
        String ls_information = ls_informationTextField.getText();
        String ls_type = ls_typeMenuButton.getText();
        String ls_date = ls_dateTextField.getText();
        Integer u_id = user.getId();

        db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawsuits_information;");
        String sql = String.format("INSERT INTO lawsuits_information (LS_type,LS_information,LS_date,U_id) VALUES('%s','%s','%s',%d) ;", ls_type, ls_information, ls_date,u_id);
        try {
            db.getUpdate(sql);
            com.github.saacsos.FXRouter.goTo("user_history");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    // ตัวเลือกประเภทคดีต่างๆ
    @FXML
    public void handleToType1(){
        ls_typeMenuButton.setText("คดีอาชญากรรม");
    }
    @FXML
    public void handleToType2(){
        ls_typeMenuButton.setText("คดีเชิงอาชญากรรม");
    }
    @FXML
    public void handleToType3(){
        ls_typeMenuButton.setText("คดีอาศัย");
    }
    @FXML
    public void handleToType4(){
        ls_typeMenuButton.setText("คดีแพ่ง");
    }
    @FXML
    public void handleToType5(){
        ls_typeMenuButton.setText("คดีที่เกี่ยวกับทรัพย์สินทางปัญญา");
    }
    @FXML
    public void handleToType6(){
        ls_typeMenuButton.setText("คดีด้านสุขภาพ");
    }
    @FXML
    public void handleToType7(){
        ls_typeMenuButton.setText("คดีแรงงาน");
    }
    @FXML
    public void handleToType8(){
        ls_typeMenuButton.setText("คดีที่เกี่ยวกับสิทธิและเสรีภาพ");
    }
    @FXML
    public void handleToType9(){
        ls_typeMenuButton.setText("คดีที่เกี่ยวกับทรัพย์สิน");
    }


}