package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.DBConnect;

import javax.swing.*;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LawyerRegisterController implements ActionListener {

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
    private TextField idcardField;
    @FXML
    private DatePicker myDateOfBirthDatePicker;
    @FXML
    private DatePicker cardReplacementDatePicker;
    @FXML
    private DatePicker cardIssueDatePicker;
    @FXML
    private TextField lawOfficeField;
    //    @FXML
//    private TextField attorneyLicensenumberField;
    @FXML
    private TextField sexField;

    @FXML
    private Label registerLabel;
    @FXML
    private MenuButton menuButton ;
    @FXML
    private MenuButton menuButton2 ;

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
    private Label idCardTag;
    @FXML
    private Label lawOfficeTag;
    @FXML
    private Label countyTag;
    @FXML
    private Label caseAptitudeTag;
    @FXML
    private Label sexTag;


    @FXML
    private Label surnameTag;
    @FXML
    private Label registerMessageLabel;




    private String pathProfile = "images/user.png";

    private String attorneyLicenseImage ;
    private String status = "0";




    @FXML
    public void initialize(){

    }
    public DBConnect db;

    @FXML
    private void registerNewLawyer()  {

        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String email = emailTextField.getText();
        String number = numberTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String idCard = idcardField.getText();
        String lawOffice = lawOfficeField.getText();
        String county = menuButton.getText();
        String caseAptitude = menuButton2.getText();
        String sex = sexField.getText();
//        String attorneyLicensenumber = attorneyLicensenumberField.getText();
        Integer countConsult = 0;
        Integer countProgress = 0;
        Integer countSuccess = 0;
        if (cardReplacementDatePicker.getValue()==null) {
            registerMessageLabel.setText("cardReplacement Date must not be empty");
        }
        if (cardIssueDatePicker.getValue()==null) {
            registerMessageLabel.setText("CardIssue Date must not be empty");
        }
        if (myDateOfBirthDatePicker.getValue()==null) {
            registerMessageLabel.setText("Birth date must not be empty");
        }
        LocalDate localDate1 = myDateOfBirthDatePicker.getValue();
        LocalDate localDate2 = cardIssueDatePicker.getValue();
        LocalDate localDate3 = cardReplacementDatePicker.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        String dateOfBirth = localDate1.format(formatter);
        String cardIssueDate = localDate2.format(formatter);
        String cardReplacementDate = localDate3.format(formatter);

        boolean checkBlank = true;

        if (surname.isBlank()) {
            surnameTag.setText(TAG);
            checkBlank = false;
        }
        if (email.isBlank()) {
            emailTag.setText(TAG);
            checkBlank = false;
        }
        if (name.isBlank()) {
            nameTag.setText(TAG);
            checkBlank = false;
        }
        if (number.isBlank()) {
            numberTag.setText(TAG);
            checkBlank = false;
        }
        if (username.isBlank()) {
            usernameTag.setText(TAG);
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

        if (idCard.isBlank()) {
            idCardTag.setText(TAG);
        }
        if (lawOffice.isBlank()) {
            lawOfficeTag.setText(TAG);
        }
        if (county.isBlank()) {
            countyTag.setText(TAG);
        }
        if (caseAptitude.isBlank()) {
            caseAptitudeTag.setText(TAG);
        }
        if (sex.isBlank()) {
            sexTag.setText(TAG);
        }

        if (!checkBlank) {
            registerMessageLabel.setText("Input field must not be empty");
            return;
        }

        try {
            int localtel = Integer.parseInt(number);
            if (localtel <= 0) {
                numberTag.setText(TAG);
                registerMessageLabel.setText("Number can only contain numeric values");
                return;
            }
        } catch (IllegalArgumentException e) {
            numberTag.setText(TAG);
            registerMessageLabel.setText("Number Input field can only contain numeric values");
            return;
        }

//
//        try {
//            int localid = Integer.parseInt(idCard);
//            if (localid <= 0) {
//                idCardTag.setText(TAG);
//                registerMessageLabel.setText("ID Card can only contain numeric values");
//                return;
//            }
//        } catch (IllegalArgumentException e) {
//            idCardTag.setText(TAG);
//            registerMessageLabel.setText("ID Input field can only contain numeric values");
//
//            return;
//        }

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
        else if (!(idCard.length() == 13)) {
            numberTag.setText(TAG);
            registerMessageLabel.setText("Invalid ID Card");
            return;
        }
        else {
            registerMessageLabel.setText("");
        }






        db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawyer_information;");

        try {
            DBConnect db = new DBConnect();

            String sql = String.format("SELECT * FROM mydb.lawyer_information WHERE L_username = '%s'",username);
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
        if (!password.equals(confirmPassword)) {
            registerMessageLabel.setText("Unmatched password");
            passwordTag.setText(TAG);
            comPasswordTag.setText(TAG);
            return;
        }
        else {
            registerMessageLabel.setText("");
        }




        String sql = String.format("INSERT INTO lawyer_information (L_username,L_name,L_surname,L_sex,L_number,L_email,L_password,L_dateOfBirth,L_attorneyLicensenumber,L_idCard,L_cardIssueDate,L_cardReplacementDate,L_lawOffice,L_county,L_caseAptitude,L_countConsult,L_countProgress,L_countSuccess,L_status,L_pathProfile) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%d','%d','%s','%s');",username,  name,  surname,sex, number, email ,password, dateOfBirth,  attorneyLicenseImage, idCard, cardIssueDate,cardReplacementDate,lawOffice,county,caseAptitude,countConsult,countProgress,countSuccess,status,pathProfile);
        //String username,String name,String surname,String password,Loca dateOfBirth, String attorneyLicensenumber,String idCard, String lawyerTicket,String cardIssueDate,String cardReplacementDate,String number, String email ,String pathProfile, String lawOffice ,String county

        try{
            db.getUpdate(sql);
            com.github.saacsos.FXRouter.goTo("lawyer_login");
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
    public void handleToAddAttorneyLicense(ActionEvent actionEvent) {

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
//                profile.setImage(new Image(target.toUri().toString()));
                attorneyLicenseImage = destDir + "/" + filename;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            registerMessageLabel.setText("File must not be empty");
        }

    }







    @FXML
    public void backToUserLogin(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleToItem11(){
        menuButton2.setText("การขโมย");
    }
    @FXML
    public void handleToItem12(){
        menuButton2.setText("คุกคามเพศ");
    }
    @FXML
    public void handleToItem13(){
        menuButton2.setText("มรดกและพินัยกรรม");
    }
    @FXML
    public void handleToItem14(){
        menuButton2.setText("กฏหมายทรรัพย์สินทางปัญญา");
    }
    @FXML
    public void handleToItem15(){
        menuButton2.setText("อุบัติเหตุและการจราจรทั่วไป");
    }
    @FXML
    public void handleToItem16(){
        menuButton2.setText("การโกงออนไลน์และซื้อขายแลกเปลี่ยน");
    }
    @FXML
    public void handleToItem17(){
        menuButton2.setText("การหย่าร้างและครอบครัว");
    }






    @FXML
    public void handleToItem1(){
        menuButton.setText("กรุงเทพมหานคร (Bangkok)");
    }
    @FXML
    public void handleToItem2(){
        menuButton.setText("เชียงใหม่ (Chiang Mai)");
    }
    @FXML
    public void handleToItem3(){
        menuButton.setText("ขอนแก่น (Khon Kaen)");
    }
    @FXML
    public void handleToItem4(){
        menuButton.setText("นครราชสีมา (Nakhon Ratchasima)");
    }
    @FXML
    public void handleToItem5(){
        menuButton.setText("ชลบุรี (Chon Buri)");
    }
    @FXML
    public void handleToItem6(){
        menuButton.setText("นครศรีธรรมราช (Nakhon Si Thammarat)");
    }


    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

    }
}

//package ku.cs.controllers;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.stage.FileChooser;
//import ku.cs.DBConnect;
//
//import java.awt.event.ActionListener;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardCopyOption;
//import java.sql.ResultSet;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//
//public class LawyerRegisterController implements ActionListener {
//
//    @FXML
//    private TextField usernameTextField;
//    @FXML
//    private TextField nameTextField;
//    @FXML
//    private TextField surnameTextField;
//    @FXML
//    private TextField emailTextField;
//    @FXML
//    private TextField numberTextField;
//    @FXML
//    private PasswordField passwordField;
//    @FXML
//    private PasswordField confirmPasswordField;
//    @FXML
//    private ImageView profile;
//
//    @FXML
//    private TextField idcardField;
//    @FXML
//    private DatePicker myDateOfBirthDatePicker;
//    @FXML
//    private DatePicker cardReplacementDatePicker;
//    @FXML
//    private DatePicker cardIssueDatePicker;
//    @FXML
//    private TextField lawOfficeField;
////    @FXML
////    private TextField attorneyLicensenumberField;
//    @FXML
//    private TextField sexField;
//
//    @FXML
//    private Label registerLabel;
//    @FXML
//    private MenuButton menuButton ;
//    @FXML
//    private MenuButton menuButton2 ;
//
//    @FXML
//    private Label registerMessageLabel;
//
//
//
//    private String pathProfile = "images/user.png";
//
//    private String attorneyLicenseImage ;
//    private String status = "0";
//
//
//
//
//    @FXML
//    public void initialize(){
//
//    }
//    public DBConnect db;
//
//    @FXML
//    private void registerNewLawyer()  {
//
//        String name = nameTextField.getText();
//        String surname = surnameTextField.getText();
//        String email = emailTextField.getText();
//        String number = numberTextField.getText();
//        String username = usernameTextField.getText();
//        String password = passwordField.getText();
//        String confirmPassword = confirmPasswordField.getText();
//        String idCard = idcardField.getText();
//        String lawOffice = lawOfficeField.getText();
//        String county = menuButton.getText();
//        String caseAptitude = menuButton2.getText();
//        String sex = sexField.getText();
////        String attorneyLicensenumber = attorneyLicensenumberField.getText();
//        Integer countConsult = 0;
//        Integer countProgress = 0;
//        Integer countSuccess = 0;
//        LocalDate localDate1 = myDateOfBirthDatePicker.getValue();
//        LocalDate localDate2 = cardIssueDatePicker.getValue();
//        LocalDate localDate3 = cardReplacementDatePicker.getValue();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String dateOfBirth = localDate1.format(formatter);
//        String cardIssueDate = localDate2.format(formatter);
//        String cardReplacementDate = localDate3.format(formatter);
//
//
//
//
//
//
//        db = new DBConnect();
//        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawyer_information;");
//
//        String sql = String.format("INSERT INTO lawyer_information (L_username,L_name,L_surname,L_sex,L_number,L_email,L_password,L_dateOfBirth,L_attorneyLicensenumber,L_idCard,L_cardIssueDate,L_cardReplacementDate,L_lawOffice,L_county,L_caseAptitude,L_countConsult,L_countProgress,L_countSuccess,L_status,L_pathProfile) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%d','%d','%s','%s');",username,  name,  surname,sex, number, email ,password, dateOfBirth,  attorneyLicenseImage, idCard, cardIssueDate,cardReplacementDate,lawOffice,county,caseAptitude,countConsult,countProgress,countSuccess,status,pathProfile);
//        //String username,String name,String surname,String password,Loca dateOfBirth, String attorneyLicensenumber,String idCard, String lawyerTicket,String cardIssueDate,String cardReplacementDate,String number, String email ,String pathProfile, String lawOffice ,String county
//
//        try{
//            db.getUpdate(sql);
//            com.github.saacsos.FXRouter.goTo("lawyer_login");
//        }catch (IOException e) {
//            System.err.println("ไปที่หน้า user_login ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//
//
//
//
//    }
//    @FXML
//    public void handleToChangeImage(ActionEvent actionEvent) {
//
//        FileChooser chooser = new FileChooser();
//        // SET FILECHOOSER INITIAL DIRECTORY
//        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//        // DEFINE ACCEPTABLE FILE EXTENSION
//        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
//        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
//        Node source = (Node) actionEvent.getSource();
//        //เลือกรูป
//        File file = chooser.showOpenDialog(source.getScene().getWindow());
//        if (file != null) {
//            try {
//                // CREATE FOLDER IF NOT EXIST
//                File destDir = new File("images");
//                if (!destDir.exists()) destDir.mkdirs();
//                // RENAME FILE
//                String[] fileSplit = file.getName().split("\\.");
//                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
//                        + fileSplit[fileSplit.length - 1];
//                Path target = FileSystems.getDefault().getPath(
//                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
//                );
//                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
//                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
//                // SET NEW FILE PATH TO IMAGE
//                profile.setImage(new Image(target.toUri().toString()));
//                pathProfile = destDir + "/" + filename;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    @FXML
//    public void handleToAddAttorneyLicense(ActionEvent actionEvent) {
//
//        FileChooser chooser = new FileChooser();
//        // SET FILECHOOSER INITIAL DIRECTORY
//        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//        // DEFINE ACCEPTABLE FILE EXTENSION
//        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
//        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
//        Node source = (Node) actionEvent.getSource();
//        //เลือกรูป
//        File file = chooser.showOpenDialog(source.getScene().getWindow());
//        if (file != null) {
//            try {
//                // CREATE FOLDER IF NOT EXIST
//                File destDir = new File("images");
//                if (!destDir.exists()) destDir.mkdirs();
//                // RENAME FILE
//                String[] fileSplit = file.getName().split("\\.");
//                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
//                        + fileSplit[fileSplit.length - 1];
//                Path target = FileSystems.getDefault().getPath(
//                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
//                );
//                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
//                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
//                // SET NEW FILE PATH TO IMAGE
////                profile.setImage(new Image(target.toUri().toString()));
//                attorneyLicenseImage = destDir + "/" + filename;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//
//
//
//
//
//
//    @FXML
//    public void backToUserLogin(ActionEvent actionEvent) {
//        try {
//            com.github.saacsos.FXRouter.goTo("lawyer_login");
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า help ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
//
//    @FXML
//    public void handleToItem11(){
//        menuButton2.setText("การขโมย");
//    }
//    @FXML
//    public void handleToItem12(){
//        menuButton2.setText("คุกคามเพศ");
//    }
//    @FXML
//    public void handleToItem13(){
//        menuButton2.setText("มรดกและพินัยกรรม");
//    }
//    @FXML
//    public void handleToItem14(){
//        menuButton2.setText("กฏหมายทรรัพย์สินทางปัญญา");
//    }
//    @FXML
//    public void handleToItem15(){
//        menuButton2.setText("อุบัติเหตุและการจราจรทั่วไป");
//    }
//    @FXML
//    public void handleToItem16(){
//        menuButton2.setText("การโกงออนไลน์และซื้อขายแลกเปลี่ยน");
//    }
//    @FXML
//    public void handleToItem17(){
//        menuButton2.setText("การหย่าร้างและครอบครัว");
//    }
//
//
//
//
//
//
//    @FXML
//    public void handleToItem1(){
//        menuButton.setText("กรุงเทพมหานคร (Bangkok)");
//    }
//    @FXML
//    public void handleToItem2(){
//        menuButton.setText("เชียงใหม่ (Chiang Mai)");
//    }
//    @FXML
//    public void handleToItem3(){
//        menuButton.setText("ขอนแก่น (Khon Kaen)");
//    }
//    @FXML
//    public void handleToItem4(){
//        menuButton.setText("นครราชสีมา (Nakhon Ratchasima)");
//    }
//    @FXML
//    public void handleToItem5(){
//        menuButton.setText("ชลบุรี (Chon Buri)");
//    }
//    @FXML
//    public void handleToItem6(){
//        menuButton.setText("นครศรีธรรมราช (Nakhon Si Thammarat)");
//    }
//
//
//    @Override
//    public void actionPerformed(java.awt.event.ActionEvent e) {
//
//    }
//}