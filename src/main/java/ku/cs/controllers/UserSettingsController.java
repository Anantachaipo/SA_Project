package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import ku.cs.DBConnect;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ku.cs.controllers.UserLoginController.user;

public class UserSettingsController {

    @FXML
    private ImageView profileImageView;

    @FXML
    private Text usernameText;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label statusLabel;

    private String pathProfile = user.getPathProfile();
    public DBConnect db;




    @FXML
    public void initialize() throws SQLException {
        usernameText.setText(user.getUsername());
        System.out.println(user.getPassword());
        profileImageView.setImage(new Image("file:" + user.getPathProfile(), true));


//        db = new DBConnect();
//        ResultSet rs = db.getConnect("SELECT * FROM mydb.user_information;");
//        String sql = String.format("UPDATE user_information SET U_pathProfile = '%s' WHERE U_id = '%s'",pathProfile,user.getId());
//        db.getUpdate(sql);
    }


    @FXML
    private void setNewPasswordAnd(){
        String olderp = user.getPassword();
        String oldp = oldPasswordField.getText();
        String newp = newPasswordField.getText();
        String conp = confirmPasswordField.getText();

        boolean checkBlank = true;

        if (oldp.isBlank()) {
            checkBlank = false;
        }
        if (newp.isBlank()) {
            checkBlank = false;
        }
        if (conp.isBlank()) {
            checkBlank = false;
        }
        if (!checkBlank) {
            return;
        }
        System.out.println("oldp = "+ oldp);
        System.out.println("olderp = "+ olderp);

        if (!oldp.equals(olderp)) {
            statusLabel.setText("Password ไม่ตรงกับ Password เดิม");
            System.out.println("Password ไม่ตรงกับ Password เดิม");
        }else if (!newp.equals(conp)) {
            statusLabel.setText("Password ไม่ตรงกัน กรุณากรอกใหม่อีกครั้ง");
        }else {
            db = new DBConnect();
            ResultSet rs = db.getConnect("SELECT * FROM mydb.user_information;");
            String sql = String.format("UPDATE user_information SET U_pathProfile = '%s',U_password = '%s' WHERE U_id = '%s'",pathProfile,newp,user.getId());
            db.getUpdate(sql);
            System.out.println("เปลี่ยน Password เรียบร้อยแล้ว");
            statusLabel.setText("");
            try {
                com.github.saacsos.FXRouter.goTo("user_home_page");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }



    }
    @FXML
    public void handleToChangeImage(ActionEvent actionEvent) {

        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) actionEvent.getSource();
        //เลือกรูป
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // SET NEW FILE PATH TO IMAGE
                profileImageView.setImage(new Image(target.toUri().toString()));
                pathProfile = destDir + "/" + filename;


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }





    @FXML
    public void backToUserHomePage(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_home_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void Logout(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
