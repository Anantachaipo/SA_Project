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
import ku.cs.service.Account;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.time.LocalDate;


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
        String dateOfBirth = myDateOfBirth.getText();
        String idCard = idcardField.getText();
        String cardIssueDate = cardIssueDateField.getText();
        String cardReplacementDate = cardReplacementDateField.getText();
        String lawOffice = lawOfficeField.getText();
        String county = countyField.getText();
        String attorneyLicensenumber = attorneyLicensenumberField.getText();
        String status = account.recordAccountLawyer( username,  name,  surname,  password,confirmPassword,  dateOfBirth,  attorneyLicensenumber, idCard, cardIssueDate ,cardReplacementDate, number, email,  lawOffice,  county, pathProfile);
        registerLabel.setText(status);

        db = new DBConnect();
        ResultSet rs = db.getConnect("SELECT * FROM mydb.lawyer_information;");

        String sql = String.format("INSERT INTO lawyer_information (L_username,L_name,L_surname,L_number,L_email,L_password,L_dateOfBirth,L_attorneyLicensenumber,L_idCard,L_cardIssueDate,L_cardReplacementDate,L_lawOffice,L_county,L_pathProfile) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",username,  name,  surname, number, email ,password, dateOfBirth,  attorneyLicensenumber, idCard, cardIssueDate,cardReplacementDate,lawOffice,county,pathProfile);
        //String username,String name,String surname,String password,Loca dateOfBirth, String attorneyLicensenumber,String idCard, String lawyerTicket,String cardIssueDate,String cardReplacementDate,String number, String email ,String pathProfile, String lawOffice ,String county

        if(status.equals("P")){
            try{
                db.getUpdate(sql);
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

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

    }
}