package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import ku.cs.Router;
import ku.cs.model.Order;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.Date;

import static ku.cs.controller.LoginController.connection;

public class ReserveController {

    @FXML private Label nameLabel;
    @FXML private Label oidLabel;
    @FXML private Label pidLabel;
    @FXML private Label qtyLabel;
    @FXML private Label bidLabel;
    @FXML private Label detailLabel;
    @FXML private Button placeReserveButton;

    private Order order = ManageOrderController.getSelectedOrder();
    @FXML private void initialize() {
        nameLabel.setText(LoginController.user.getName());
        oidLabel.setText(String.valueOf(order.getOid()));
        pidLabel.setText(String.valueOf(order.getPid()));
        qtyLabel.setText(String.valueOf(order.getQty()));
        bidLabel.setText(String.valueOf(order.getBid()));
        detailLabel.setText(order.getDetail());
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
            String sqlText = "update `order` set `status` = ? where `O_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "W");
            pst.setInt(2, order.getOid());
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
