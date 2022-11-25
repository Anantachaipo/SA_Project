package ku.cs.controller;

import com.mysql.cj.conf.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.Router;
import ku.cs.model.Customer;
import ku.cs.service.PageChanger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ku.cs.controller.LoginController.connection;

public class NewContractController {
    private Customer customer;

    private static final String TAG = "*";
    @FXML private Label cIdTag;
    @FXML private Label conLenTag;
    @FXML private Label depositTag;
    @FXML private Label newContractMessageLabel;
    @FXML private TextField cIdTextField;
    @FXML private TextField conLenTextField;
    @FXML private TextField depositTextField;

    @FXML private void initialize() {

        // Check for user ID from CustomerDetail
        customer = CustomerDetailController.getCustomer();
        if (customer==null)
            cIdTextField.clear();
        else
            cIdTextField.setText(String.valueOf(customer.getId()));
    }

    @FXML private void handleAddNewContractButton(ActionEvent event) {
        resetTagAndMessage();
        /*
            index 0 -> check empty input
            index 1 -> check non-numeric input
            index 2 -> check valid customer ID
            index 3 -> check valid input size
         */
        List<Boolean> check = Arrays.asList(true, true, false, true);

        // check for empty input
        if (cIdTextField.getText().isBlank()) {
            check.set(0, false);
            cIdTag.setText(TAG);
        }
        if (conLenTextField.getText().isBlank()) {
            check.set(0, false);
            conLenTag.setText(TAG);
        }
        if (depositTextField.getText().isBlank()) {
            check.set(0, false);
            depositTag.setText(TAG);
        }

        if (!check.get(0)) {
            newContractMessageLabel.setText("Input field must not be empty");
            return;
        }

        int cid = 0;
        int depos = 0;
        int len = 0;

        // check for non-numeric input
        try {
            cid = Integer.parseInt(cIdTextField.getText());
        } catch (IllegalArgumentException e) {
            check.set(1, false);
            cIdTag.setText(TAG);
        }
        try {
            depos = Integer.parseInt(depositTextField.getText());
        } catch (IllegalArgumentException e) {
            check.set(1, false);
            depositTag.setText(TAG);
        }
        try {
            len = Integer.parseInt(conLenTextField.getText());
        } catch (IllegalArgumentException e) {
            check.set(1, false);
            conLenTag.setText(TAG);
        }

        if (!check.get(1)) {
            newContractMessageLabel.setText("Input field can only contain numeric values");
            return;
        }

        // check for Customer ID
        try {
            String sqlText = "SELECT `C_ID` FROM customer ";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                int cidDB = result.getInt(1);
                if (cid == cidDB) {
                    check.set(2, true);
                    result.close();
                    pst.close();
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!check.get(2)) {
                newContractMessageLabel.setText("Customer not found");
                cIdTag.setText(TAG);
                return;
            }
        }

        // check deposit value
        if (depos <= 0 || depos > 1000000) {
            check.set(3, false);
            depositTag.setText(TAG);
        }

        // check contract length value
        if (len <= 0 || len > 365) {
            check.set(3, false);
            conLenTag.setText(TAG);
        }

        if (!check.get(3)) {
            newContractMessageLabel.setText("Value range out of bound");
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

            PageChanger.gotoPage("menu_manager");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void resetTagAndMessage() {
        cIdTag.setText("");
        conLenTag.setText("");
        depositTag.setText("");
        newContractMessageLabel.setText("");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        CustomerDetailController.setNullCustomer();
        PageChanger.gotoPage("manage_contract");
    }
}
