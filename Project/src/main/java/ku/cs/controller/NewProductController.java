package ku.cs.controller;

import com.mysql.cj.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.Router;
import ku.cs.service.PageChanger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        boolean validInput = true;
        boolean validQty = true;
        boolean validPPU = true;
        String productName, type;
        int qty = 0, ppu = 0;

        // check for empty input
        if (productNameTextField.getText().isBlank()) {
            validInput = false;
            productNameTag.setText(TAG);
        }
        if (typeTextField.getText().isBlank()) {
            validInput = false;
            typeTag.setText(TAG);
        }
        if (qtyTextField.getText().isBlank()) {
            validInput = false;
            qtyTag.setText(TAG);
        }
        if (ppuTextField.getText().isBlank()) {
            validInput = false;
            ppuTag.setText(TAG);
        }

        if (!validInput) {
            newProductMessageLabel.setText("Input cannot be blank");
            return;
        }

        // check for non numeric qty and ppu
        try {
            qty = Integer.parseInt(qtyTextField.getText());
            if (qty < 1) {
                validQty = false;
                qtyTag.setText(TAG);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("cannot parse qtyTextField.getText()");
            validQty = false;
            qtyTag.setText(TAG);
        }
        try {
            ppu = Integer.parseInt(ppuTextField.getText());
            if (ppu < 1) {
                validPPU = false;
                ppuTag.setText(TAG);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("cannot parse ppuTextField.getText()");
            validPPU = false;
            ppuTag.setText(TAG);
        }
        if (!validQty || !validPPU) {
            newProductMessageLabel.setText("Invalid Input");
            return;
        }

        productName = productNameTextField.getText();
        type = typeTextField.getText();

        try {
            String sqlText = "insert into product (P_Name, P_Type, P_Qty, P_PPU) " +
                    "values (?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, productName);
            pst.setString(2, type);
            pst.setInt(3, qty);
            pst.setInt(4, ppu);
            pst.executeUpdate();

            pst.close();

            PageChanger.gotoPage("manage_product");
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
        PageChanger.gotoPage("manage_product");
    }

}