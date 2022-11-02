package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import ku.cs.Router;
import ku.cs.model.Order;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.Chronology;
import java.util.Date;

public class ReserveController {

    @FXML private Label nameLabel;
    @FXML private Label oidLabel;
    @FXML private Label pidLabel;
    @FXML private Label qtyLabel;
    @FXML private Label bidLabel;
    @FXML private Label detailLabel;
    @FXML private Button placeReserveButton;
    @FXML private DatePicker datePicker;

    private Order order = ManageOrderController.getSelectedOrder();
    @FXML private void initialize() {
        nameLabel.setText(LoginController.user.getName());
        placeReserveButton.setDisable(true);
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

    @FXML private void handlePlaceReserveButton(ActionEvent event) throws ParseException {

        // TODO: insert เข้า order, orderList
        try {
            Router.goTo("manage_order");
        } catch (IOException e) {
            System.err.println("ไปหน้า manage_order จาก reserve ไม่ได้");
            e.printStackTrace();
        }
    }

    // TODO: เช็คว่าเลือกตรงแล้วปุ่มเปิดใช้งาน
    @FXML private void handleDatePicker(ActionEvent actionEvent) throws ParseException {
        String dp = datePicker.toString();
        Date res = new SimpleDateFormat("dd/MM/yyyy").parse(dp);
        Date now = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(System.currentTimeMillis()));
        if (dp != null && res.compareTo(now) > 0) {
            placeReserveButton.setDisable(false);
        }
    }
}
