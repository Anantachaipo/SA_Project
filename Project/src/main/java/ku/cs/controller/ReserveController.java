package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import ku.cs.model.OrderList;
import ku.cs.service.Utilities;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class ReserveController {

    @FXML private Label nameLabel;
    @FXML private Label olidLabel;
    @FXML private Label numProductLabel;
    @FXML private Label totalBidLabel;

    @FXML private CheckBox conCheckbox;
    @FXML private Button placeReserveButton;

    private OrderList orderList = ManageOrderController.getOrderList();
    @FXML private void initialize() {
        nameLabel.setText(LoginController.user.getName());
        olidLabel.setText(String.valueOf(orderList.getOL_ID()));
        numProductLabel.setText(Utilities.thousandSeparator(orderList.getNumOrder()));
        totalBidLabel.setText(Utilities.thousandSeparator(orderList.getTotalBid()));
        placeReserveButton.setDisable(true);
    }

    @FXML private void handlePlaceReserveButton(ActionEvent event) {
        try {
            String sqlText = "UPDATE `order_list` SET `Status` = ? where `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "W");
            pst.setInt(2, orderList.getOL_ID());
            pst.executeUpdate();

            pst.close();

            Utilities.gotoPage("manage_order");
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleConCheckbox(ActionEvent event) {
        if (conCheckbox.isSelected())
            placeReserveButton.setDisable(false);
        else
            placeReserveButton.setDisable(true);
    }

    @FXML private void ManageLogoutButton(ActionEvent event) {
        Utilities.gotoPage("login");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        Utilities.gotoPage("manage_order");
    }
}