package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ku.cs.model.Receipt;
import ku.cs.service.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class ManagerReceiptController {

    @FXML private ListView<Receipt> receiptListView;

    @FXML private void initialize() {
        showReceipt();
    }

    private void showReceipt() {
        try {
            String sqlText = "SELECT * FROM `receipt`";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                Receipt receipt = new Receipt(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3)
                );
                receiptListView.getItems().add(receipt);
            }
            result.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        Utilities.gotoPage("menu_manager");
    }

}
