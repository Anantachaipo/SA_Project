package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.DBConnect;

import java.awt.event.ActionListener;
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

public class UserRegisterController implements ActionListener {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ImageView profile;
    @FXML
    private Label registerLabel;
    private String pathProfile = "images/user.png";

    private static final String TAG = "*";
    @FXML
    private Label nameTag;
    @FXML
    private Label usernameTag;
    @FXML
    private Label numberTag;
    @FXML
    private Label passwordTag;
    @FXML
    private Label comPasswordTag;
    @FXML
    private Label emailTag;
    @FXML
    private Label surnameTag;
    @FXML
    private Label registerMessageLabel;

    @FXML
    public void initialize(){
// String path ใส่รูป
    }
    public DBConnect db;

    @FXML
    private void registerNewMember(){
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String email = emailTextField.getText();
        String number = numberTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword =  confirmPasswordField.getText();

        boolean checkBlank = true;

        if (name.isBlank()) {
            surnameTag.setText(TAG);
            checkBlank = false;
        }
        if (name.isBlank()) {
            emailTag.setText(TAG);
            checkBlank = false;
        }
        if (name.isBlank()) {
            nameTag.setText(TAG);
            checkBlank = false;
        }
        if (username.isBlank()) {
            usernameTag.setText(TAG);
            checkBlank = false;
        }
        if (number.isBlank()) {
            numberTag.setText(TAG);
            checkBlank = false;
        }
        if (password.isBlank()) {
            passwordTag.setText(TAG);
            checkBlank = false;
        }
        if (confirmPassword.isBlank()) {
            comPasswordTag.setText(TAG);
            checkBlank = false;
        }
        if (!checkBlank) {
            registerMessageLabel.setText("Input field must not be empty");
            return;
        }

        try {
            int localtel = Integer.parseInt(number);
            if (localtel <= 0) {
                numberTag.setText(TAG);
                registerMessageLabel.setText("Input field can only contain numeric values");
                return;
            }
        } catch (IllegalArgumentException e) {
            numberTag.setText(TAG);
            registerMessageLabel.setText("Input field can only contain numeric values");
            return;
        }

        /*
         * check result value doesn't exceed DB size
         ** name     (50)
         ** username (30)
         ** telephone (10)
         ** password (20)
         */
        if (name.length() > 50) {
            nameTag.setText(TAG);
            registerMessageLabel.setText("Characters input limit exceeded (50)");
            return;
        } else if (username.length() > 30) {
            usernameTag.setText(TAG);
            registerMessageLabel.setText("Characters input limit exceeded (30)");
            return;
        } else if (!(number.length() == 9 || number.length() == 10)) {
            numberTag.setText(TAG);
            registerMessageLabel.setText("Invalid Telephone number");
            return;
        } else if (password.length() > 20||password.length()<4) {
            passwordTag.setText(TAG);
            registerMessageLabel.setText("Characters input limit exceeded 4-20");
            return;
        }
        else {
            registerMessageLabel.setText("");
        }




        db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.user_information;");

        try {
            DBConnect db = new DBConnect();

            String sql = String.format("SELECT * FROM mydb.user_information WHERE U_username = '%s'",username);
//            PreparedStatement pst = (PreparedStatement) db.getConnect(sql);
//            pst.setString(1, username);
            ResultSet result = db.getConnect(sql);

            if (result.next()) {
                usernameTag.setText(TAG);
                registerMessageLabel.setText("Username is already used");

                result.close();
//                pst.close();
                return;
            }
            else {
                registerMessageLabel.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // check password match
        if (!password.equals(confirmPassword)) {
            registerMessageLabel.setText("Unmatched password");
            passwordTag.setText(TAG);
            comPasswordTag.setText(TAG);
            return;
        }
        else {
            registerMessageLabel.setText("");
        }



        String sql = String.format("INSERT INTO user_information (U_username,U_name,U_surname,U_number,U_email,U_password,U_pathProfile) VALUES('%s','%s','%s','%s','%s','%s','%s');",username,name,surname,number,email,password,pathProfile);


        try{
            db.getUpdate(sql);
            com.github.saacsos.FXRouter.goTo("user_login");
        }catch (IOException e) {
            System.err.println("ไปที่หน้า user_login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
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
                profile.setImage(new Image(target.toUri().toString()));
                pathProfile = destDir + "/" + filename;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void backToUserLogin(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

    }
}
