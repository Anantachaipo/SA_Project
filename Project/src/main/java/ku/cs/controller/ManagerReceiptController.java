package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ku.cs.Router;
import ku.cs.model.Receipt;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

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
        try {
            Router.goTo("menu_manager");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า menu_manager จาก manager_manage_order ไม่ได้");
        }
    }

}
