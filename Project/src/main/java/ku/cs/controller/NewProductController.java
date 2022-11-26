package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.service.Utilities;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static ku.cs.controller.LoginController.connection;
public class NewProductController {

    private static final String TAG = "*";
    @FXML private Label newProductMessageLabel;
    @FXML private Label productNameTag;
    @FXML private Label typeTag;
    @FXML private Label qtyTag;
    @FXML private Label ppuTag;
    @FXML private TextField productNameTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField qtyTextField;
    @FXML private TextField ppuTextField;

    @FXML private void handleAddNewProductButton(ActionEvent event) {
        resetTagAndMessage();
        /*
            index 0 -> check empty input
            index 1 -> check non-numeric input
            index 2 -> check valid input size (number)
            index 3 -> check valid input size (text)
        */
        List<Boolean> check = Arrays.asList(true, true, true, true);
        String productName, type;
        int qty = 0, ppu = 0;

        // check for empty input
        if (productNameTextField.getText().isBlank()) {
            check.set(0, false);
            productNameTag.setText(TAG);
        }
        if (typeTextField.getText().isBlank()) {
            check.set(0, false);
            typeTag.setText(TAG);
        }
        if (qtyTextField.getText().isBlank()) {
            check.set(0, false);
            qtyTag.setText(TAG);
        }
        if (ppuTextField.getText().isBlank()) {
            check.set(0, false);
            ppuTag.setText(TAG);
        }

        if (!check.get(0)) {
            newProductMessageLabel.setText("Input field must not be empty");
            return;
        }

        // check for non-numeric input
        try {
            qty = Integer.parseInt(qtyTextField.getText());

        } catch (IllegalArgumentException e) {
            System.err.println("cannot parse qtyTextField.getText()");
            check.set(1, false);
            qtyTag.setText(TAG);
        }
        try {
            ppu = Integer.parseInt(ppuTextField.getText());
        } catch (IllegalArgumentException e) {
            System.err.println("cannot parse ppuTextField.getText()");
            check.set(1, false);
            ppuTag.setText(TAG);
        }
        if (!check.get(1)) {
            newProductMessageLabel.setText("Input field can only contain numeric values");
            return;
        }

        // check valid input size (number)
        if (qty < 0 || qty > 9999) {
            check.set(2, false);
            qtyTag.setText(TAG);
        }
        if (ppu < 1 || ppu > 99999) {
            check.set(2, false);
            ppuTag.setText(TAG);
        }
        if (!check.get(2)) {
            newProductMessageLabel.setText("Value range out of bound");
            return;
        }

        // check valid input size (text)
        productName = productNameTextField.getText();
        type = typeTextField.getText();
        if (productName.length() > 50) {
            check.set(3, false);
            productNameTag.setText(TAG);
        }
        if (type.length() > 50) {
            check.set(3, false);
            typeTag.setText(TAG);
        }
        if (!check.get(3)) {
            newProductMessageLabel.setText("Characters input limit exceeded (50)");
            return;
        }

        try {
            String sqlText = "INSERT INTO product (P_Name, P_Type, P_Qty, P_PPU) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, productName);
            pst.setString(2, type);
            pst.setInt(3, qty);
            pst.setInt(4, ppu);
            pst.executeUpdate();

            pst.close();

            Utilities.gotoPage("manage_product");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void resetTagAndMessage() {
        ppuTag.setText("");
        typeTag.setText("");
        qtyTag.setText("");
        productNameTag.setText("");
        newProductMessageLabel.setText("");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        Utilities.gotoPage("manage_product");
    }

}