package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.Router;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class NewContractController {

    private static final String TAG = "*";
    @FXML private Label cIdTag;
    @FXML private Label conLenTag;
    @FXML private Label depositTag;
    @FXML private Label newContractMessageLabel;
    @FXML private TextField cIdTextField;
    @FXML private TextField conLenTextField;
    @FXML private TextField depositTextField;
    @FXML private void handleAddNewContractButton(ActionEvent event) {
        resetTagAndMessage();
        boolean validInput = true;
        boolean validLen = true;
        boolean validDeposit = true;
        boolean validID = true;

        // check for empty input
        if (cIdTextField.getText().isBlank()) {
            validInput = false;
            cIdTag.setText(TAG);
        }
        if (conLenTextField.getText().isBlank()) {
            validInput = false;
            conLenTag.setText(TAG);
        }
        if (depositTextField.getText().isBlank()) {
            validInput = false;
            depositTag.setText(TAG);
        }
        if (!validInput) {
            newContractMessageLabel.setText("Invalid contract");
            return;
        }

        int cid = 0;
        int depos = 0;
        int len = 0;
        try {
            cid = Integer.parseInt(cIdTextField.getText());
        } catch (IllegalArgumentException e) {
            validID = false;
        }
        try {
            depos = Integer.parseInt(depositTextField.getText());
        } catch (IllegalArgumentException e) {
            validDeposit = false;
        }
        try {
            len = Integer.parseInt(conLenTextField.getText());
        } catch (IllegalArgumentException e) {
            validLen = false;
        }

        if (!validDeposit) {
            depositTag.setText(TAG);
            newContractMessageLabel.setText("Invalid deposit");
            return;
        }
        if (!validLen) {
            conLenTag.setText(TAG);
            newContractMessageLabel.setText("Invalid length");
            return;
        }
        try {
            String sqlText = "INSERT INTO `contract` (`Con_length`, `Con_Deposit`, `Con_Status`, `C_ID`) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, len);
            pst.setInt(2, depos);
            pst.setString(3, "V");
            pst.setInt(4, cid);
            pst.executeUpdate();

            pst.close();

            Router.goTo("manage_contract");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า manage_contract จาก new_contract ไม่ได้");
        }
    }

    private void resetTagAndMessage() {
        cIdTag.setText("");
        conLenTag.setText("");
        depositTag.setText("");
        newContractMessageLabel.setText("");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("manage_contract");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า manage_contract จาก new_contract ไม่ได้");
        }
    }
}
