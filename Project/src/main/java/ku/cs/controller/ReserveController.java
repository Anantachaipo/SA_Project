package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ku.cs.Router;
import ku.cs.model.OrderList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class ReserveController {

    @FXML private Label nameLabel;
    @FXML private Label olidLabel;
    @FXML private Label numProductLabel;
    @FXML private Label totalBidLabel;
    @FXML private Button placeReserveButton;

    private OrderList orderList = ManageOrderController.getOrderList();
    @FXML private void initialize() {
        nameLabel.setText(LoginController.user.getName());
        olidLabel.setText(String.valueOf(orderList.getOL_ID()));
        numProductLabel.setText(String.valueOf(orderList.getNumOrder()));
        totalBidLabel.setText(String.valueOf(orderList.getTotalBid()));
    }
    @FXML void ManageLogoutButton(ActionEvent event) {
        try {
            Router.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปหน้า login จาก menu ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("manage_order");
        } catch (IOException e) {
            System.err.println("ไปหน้า manage_order จาก reserve ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handlePlaceReserveButton(ActionEvent event) {
        try {
            String sqlText = "UPDATE `order_list` SET `Status` = ? where `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "W");
            pst.setInt(2, orderList.getOL_ID());
            pst.executeUpdate();

            pst.close();

            Router.goTo("manage_order");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า manage_order จาก reserve ไม่ได้");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

}
