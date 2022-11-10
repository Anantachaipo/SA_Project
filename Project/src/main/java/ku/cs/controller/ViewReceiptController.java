package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import ku.cs.Router;
import ku.cs.model.Order;
import ku.cs.model.OrderList;
import ku.cs.model.Receipt;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

public class ViewReceiptController {

    @FXML private Label nameLabel;
    // Receipt
    @FXML private Label olidLabel;
    @FXML private Label numProductLabel;
    @FXML private Label totalBidLabel;
    // Detail
    @FXML private Label productNameLabel;
    @FXML private Label qtyLabel;
    @FXML private Label bidLabel;
    @FXML private Label detailLabel;
    @FXML private ListView<Order> orderListView;
    @FXML private ListView<Receipt> receiptListView;

    private HashMap<Integer, String> prodMap = new HashMap<>();
    private Receipt receipt;
    private OrderList orderList;
    private Order order;

    @FXML private void initialize() {
        nameLabel.setText(user.getName());
        mapIDtoProductName();
        clearSelectedReceipt();
        clearSelectedOrder();
        showReceipt();
        handleSelectedReceipt();
        handleSelectedOrder();
    }

    private void clearSelectedOrder() {
        orderListView.getItems().clear();

        this.order = null;

        productNameLabel.setText("-");
        qtyLabel.setText("-");
        bidLabel.setText("-");
        detailLabel.setText("-");
    }

    private void handleSelectedReceipt() {
        receiptListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Receipt>() {
                    @Override
                    public void changed(ObservableValue<? extends Receipt> observable, Receipt oldValue, Receipt newValue) {
                        clearSelectedReceipt();
                        clearSelectedOrder();
                        System.out.println(newValue + " + is selected");
                        showSelectedReceipt(newValue);

                    }
                }
        );
    }

    private void handleSelectedOrder() {
        orderListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Order>() {
                    @Override
                    public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                        System.out.println(newValue + " + is selected");
                        showSelectedOrder(newValue);
                    }
                }
        );
    }

    private void showSelectedReceipt(Receipt receipt) {
        this.receipt = receipt;
        this.orderList = new OrderList();
        showOrder();

        olidLabel.setText(String.valueOf(receipt.getOL_ID()));
        totalBidLabel.setText(String.valueOf(orderList.getTotalBid()));
        numProductLabel.setText(String.valueOf(orderList.getNumOrder()));
    }

    private void showSelectedOrder(Order order) {
        this.order = order;

        productNameLabel.setText(prodMap.get(order.getP_ID()));
        qtyLabel.setText(String.valueOf(order.getQty()));
        bidLabel.setText(String.valueOf(order.getBid()));
        detailLabel.setText(order.getDetail());
    }

    private void showReceipt() {
        try {
            String sqlText = "SELECT * FROM `receipt` WHERE `C_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, user.getId());
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

    private void clearSelectedReceipt() {
        this.receipt = null;
        clearSelectedOrder();

        olidLabel.setText("-");
        numProductLabel.setText("-");
        totalBidLabel.setText("-");
    }

    private void showOrder() {
        orderList = new OrderList(receipt.getOL_ID());

        try {
            String sqlText = "SELECT `P_ID`, `qty`, `bid`, `detail` FROM `order` WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, receipt.getOL_ID());
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                Order order = new Order(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getString(4)
                );
                orderList.addOrder(order);
                orderListView.getItems().add(order);
            }

            result.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void mapIDtoProductName() {
        try {
            String sqlText = "SELECT  `P_ID`, `P_Name` FROM `product`";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                prodMap.put(
                        result.getInt(1),
                        result.getString(2)
                );
            }

            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ sql ไม่ได้");
        }
    }

    @FXML private void handleManageReceiptButton(ActionEvent event) {
        try {
            Router.goTo("receipt");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า receipt จาก view_receipt ไม่ได้");
        }
    }

    @FXML private void ManageLogoutButton(ActionEvent event) {
        try {
            Router.goTo("login");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า login จาก view_receipt ไม่ได้");
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("menu");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า menu จาก view_receipt ไม่ได้");
        }
    }

}