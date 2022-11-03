package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.Router;
import ku.cs.model.Customer;
import ku.cs.model.Order;
import ku.cs.model.OrderList;
import ku.cs.model.Product;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static ku.cs.controller.LoginController.connection;

public class ManageManagerOrderController {

    private Order order;
    private OrderList orderList;
    private static Customer customer;
    @FXML private Hyperlink cidHyperlink;
    @FXML private Label oidLabel;
    @FXML private Label bidLabel;
    @FXML private Label qtyLabel;
    @FXML private Label statusLabel;
    @FXML private Label detailLabel;
    @FXML private ListView<Order> orderListListView;
    @FXML private Button acButton;
    @FXML private Button rejectButton;

    @FXML private void initialize() {
        disableButtons();
        showOrderListList();
        clearSelectedOrderList();
        handleSelectedListView();
    }

    private void showOrderListList() {
        orderList = new OrderList();

        try {
            String sqlText = "select * from `order` where `status` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "P");
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                orderList.addOrder(new Order(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getInt(4),
                        result.getInt(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8)
                ));
            }

            result.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }

        orderListListView.getItems().addAll(orderList.getOrders());
    }

    private void handleSelectedListView() {
        orderListListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Order>() {
                    @Override
                    public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedOrderList(newValue);
                    }
                });

    }

    private void showSelectedOrderList(Order order) {
        this.order = order;
        cidHyperlink.setText(String.valueOf(order.getCid()));
        oidLabel.setText(String.valueOf(order.getOid()));
        qtyLabel.setText(String.valueOf(order.getQty()));
        bidLabel.setText(String.valueOf(order.getBid()));
        statusLabel.setText(showStatus(order.getStatus()));
        detailLabel.setText(order.getDetail());

        if (order.getStatus().equals("P")) {
            enableButton();
        }
    }

    private void clearSelectedOrderList() {
        this.order = null;
        cidHyperlink.setText("");
        oidLabel.setText("");
        qtyLabel.setText("");
        bidLabel.setText("");
        statusLabel.setText("");
        detailLabel.setText("");
        acButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    private void disableButtons() {
        acButton.setDisable(true);
        rejectButton.setDisable(true);
    }
    private void enableButton() {
        acButton.setDisable(false);
        rejectButton.setDisable(false);
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

    @FXML private void handleCIDHyperlink(ActionEvent event) {
        try {
            String sqlText = "select * from `customer` where `C_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, Integer.parseInt(cidHyperlink.getText()));
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                customer = new Customer(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)
                );
                Router.goTo("customer_detail");
            } else {
                System.err.println("user not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า customer_detail จาก manager_manage_order ไม่ได้");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }
    @FXML private void handleAcceptButton(ActionEvent event) {
        try {
            order.setStatus("A");
            String sqlText = "update `order` set `status` = ? where `O_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "A");
            pst.setInt(2, order.getOid());
            pst.executeUpdate();

            pst.close();

            orderListListView.getItems().clear();
            showOrderListList();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    @FXML private void handleRejectButton(ActionEvent event) {
        try {
            order.setStatus("R");
            String sqlText = "update `order` set `status` = ? where `O_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "R");
            pst.setInt(2, order.getOid());
            pst.executeUpdate();

            pst.close();

            orderListListView.getItems().clear();
            showOrderListList();
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
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

    public static Customer getCustomerHyperlink() {
        return customer;
    }
}
