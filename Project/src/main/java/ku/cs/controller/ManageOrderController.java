package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.Router;
import ku.cs.model.Order;
import ku.cs.model.OrderList;

import java.io.IOException;

public class ManageOrderController {

    @FXML private Label detailLabel;
    @FXML private Label nameLabel;
    @FXML private Label oidLabel;
    @FXML private Label qtyLabel;
    @FXML private Label statusLabel;
    @FXML private Label bidLabel;
    @FXML private Button reserveButton;
    @FXML private ListView<Order> orderListView;

    private static Order order;
    @FXML
    private void initialize() {
        nameLabel.setText(LoginController.user.getName());
        reserveButton.setDisable(true);
        showOrderList();
        clearSelectedOrder();
        handleSelectedListView();
    }

    private void handleSelectedListView() {
        orderListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Order>() {
                    @Override
                    public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedOrder(newValue);
                    }
                });
    }
    private void showSelectedOrder(Order order) {
        this.order = order;
        oidLabel.setText(String.valueOf(this.order.getOid()));
        qtyLabel.setText(String.valueOf(this.order.getQty()));
        bidLabel.setText(String.valueOf(this.order.getBid()));
        statusLabel.setText(showStatus(this.order.getStatus()));
        detailLabel.setText(this.order.getDetail());
        if (!this.order.getStatus().equals("A"))
            reserveButton.setDisable(true);
        reserveButton.setDisable(false);
    }
    private void clearSelectedOrder() {
        oidLabel.setText("-");
        qtyLabel.setText("-");
        statusLabel.setText("-");
        detailLabel.setText("-");
        bidLabel.setText("-");
    }
    private void showOrderList() {
        OrderList orderList = new OrderList();
        // TODO: ใช้ DB เอาข้อมูล orderList

        orderListView.getItems().addAll(orderList.getOrders());
    }

    private String showStatus(String status) {
        return switch (status) {
            case "A" -> "Accepted";
            case "P" -> "Pending";
            case "R" -> "Rejected";
            case "W" -> "Waiting";
            case "S" -> "Success";
            case "F" -> "Fail";
            default -> "-";
        };
    }

    @FXML private void ManageLogoutButton(ActionEvent event) {
        try {
            Router.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปหน้า login จาก manage_order ไม่ได้");
        }
    }
    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("menu");
        } catch (IOException e) {
            System.err.println("ไปหน้า menu จาก manage_order ไม่ได้");
        }
    }
    @FXML private void handleNewOrderButton(ActionEvent event) {
        try {
            Router.goTo("new_order");
        } catch (IOException e) {
            System.err.println("ไปหน้า new_order จาก manage_order ไม่ได้");
        }
    }
    @FXML private void handleReserveButton(ActionEvent event) {
        try {
            Router.goTo("reserve");
        } catch (IOException e) {
            System.err.println("ไปหน้า reserve จาก manage_order ไม่ได้");
        }
    }

    public static Order getSelectedOrder() {
        return order;
    }

}
