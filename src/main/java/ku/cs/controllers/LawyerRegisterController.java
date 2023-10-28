package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.service.Account;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LawyerRegisterController {

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
    private TextField myDateOfBirth;
    @FXML
    private TextField cardReplacementDateField;
    @FXML
    private TextField cardIssueDateField;
    @FXML
    private TextField lawOfficeField;
    @FXML
    private TextField attorneyLicensenumberField;
    @FXML
    private TextField countyField;
    @FXML
    private Label registerLabel;

    private Account account = new Account();

    private String pathProfile = "images/user.png";
    private String idCard = "0" ;
    private String lawyerTicket = "00";



    @FXML
    public void initialize(){

    }
    @FXML
    private void registerNewLawyer() throws IOException{

        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String email = emailTextField.getText();
        String number = numberTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String dateOfBirth = myDateOfBirth.getText();

        String cardIssueDate = cardIssueDateField.getText();
        String cardReplacementDate = cardReplacementDateField.getText();
        String lawOffice = lawOfficeField.getText();
        String county = countyField.getText();
        String attorneyLicensenumber = attorneyLicensenumberField.getText();


        //String username,String name,String surname,String password,Loca dateOfBirth, String attorneyLicensenumber,String idCard, String lawyerTicket,String cardIssueDate,String cardReplacementDate,String number, String email ,String pathProfile, String lawOffice ,String county
        String status = account.recordAccountLawyer(username,name,surname,password,dateOfBirth ,attorneyLicensenumber,idCard,lawyerTicket,cardIssueDate,cardReplacementDate,number,email,pathProfile,lawOffice,county);
        registerLabel.setText(status);
        if(status.equals("P")){
            try{
                com.github.saacsos.FXRouter.goTo("lawyer_login");
            }catch (IOException e) {
                System.err.println("ไปที่หน้า user_login ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
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
            com.github.saacsos.FXRouter.goTo("lawyer_login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void handleToUserLogin(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("lawyer_login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}