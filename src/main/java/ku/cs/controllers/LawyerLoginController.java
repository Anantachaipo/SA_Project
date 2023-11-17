package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.DBConnect;
import ku.cs.model.Lawyer;




import java.io.IOException;
import java.sql.ResultSet;

public class LawyerLoginController {
    public static Lawyer lawyer;
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Label loginLabel;

    @FXML
    public void login () throws IOException{
        String userName = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        loginLabel.setText("ไม่สามารถ login ได้ โปรดลองอีกครั้ง");
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = null;
            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_username = '%s' AND L_password = '%s'",userName,password);
            rs = db.getConnect(sql);

            if(rs.next()){
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
                        rs.getString(20),
                        rs.getString(21));
                rs.close();
                System.out.println("login successful");
                System.out.println("Current user = " + lawyer.getUsernameLawyer());
                System.out.println( lawyer.getNameLawyer());
                System.out.println( lawyer.getSurnameLawyer());

                if(lawyer.getStatus().equals("0")){
                    System.out.println("รอการตรวขสอบบัญญชีผู้ใช้");
                }else if(lawyer.getStatus().equals("1")){
                    System.out.println("Login สำเร็จ");
                    com.github.saacsos.FXRouter.goTo("lawyer_home_page");
                }

            }else {
                System.out.println("login fail");
                rs.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ไม่สามารถ login ได้");
        }
    }


//    public boolean loginUser() {
//        String userName = usernameTextField.getText();
//        String password = passwordPasswordField.getText();
//        loginLabel.setText("ไม่สามารถ login ได้ โปรดลองอีกครั้ง");
//        try {
//            DBConnect db = new DBConnect();
//            ResultSet rs = null;
//            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_username = '%s' AND L_password = '%s'",userName,password);
//            rs = db.getConnect(sql);
//            return rs.next();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }
//    @FXML
//    public void login () throws IOException{
//        if (loginUser()) {
//            try {
//                com.github.saacsos.FXRouter.goTo("lawyer_home_page");
//            } catch (IOException e) {
//                System.err.println("ไปที่หน้า ีlawyer_home_page ไม่ได้");
//                System.err.println("ตรวจสอบความถูกต้องของ username และ password");
//                System.err.println("ให้ตรวจสอบการกำหนด router");
//            }
//        } else {
//            JOptionPane.showMessageDialog(null,"login false");
//        }
//    }


    @FXML
    public void backToUserLogin(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void goToLawyerRegister(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}

