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
import ku.cs.model.Contract;
import ku.cs.model.Customer;
import ku.cs.model.Order;
import ku.cs.model.OrderList;
import ku.cs.service.PageChanger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static ku.cs.controller.LoginController.connection;

public class ManageManagerOrderController {

    // Orderlist
    @FXML private Hyperlink cidHyperlink;
    @FXML private Label cNameLabel;
    @FXML private Label olidLabel;
    @FXML private Label numProductLabel;
    @FXML private Label totalBidLabel;
    @FXML private Label statusLabel;
    // Order
    @FXML private Label productNameLabel;
    @FXML private Label qtyLabel;
    @FXML private Label bidLabel;
    @FXML private Label detailLabel;

    @FXML private ListView<OrderList> orderListListView;
    @FXML private ListView<Order> orderListView;

    @FXML private Button acButton;
    @FXML private Button rejectButton;

    private Order order;
    private OrderList orderList;
    private static Contract contract;
    private static Customer customer;
    private static HashMap<Integer, String> prodMap = new HashMap<>();
    private static HashMap<Integer, String> custMap = new HashMap<>();

    @FXML private void initialize() {
        checkActiveContract();
        clearSelectedOrderList();
        clearSelectedOrder();
        mapIDtoProductName();
        disableButtons();
        showOrderList();
        handleSelectedOrderListListView();
        handleSelectedOrderListView();
    }

    private void showOrder(OrderList orderList) {
        try {
            orderList.clearOrder();
            orderListView.getItems().clear();
            // Read Order
            String sqlText = "SELECT `P_ID`, `qty`, `bid`, `detail` FROM `order` " +
                    "WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, orderList.getOL_ID());
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
            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void handleSelectedOrderListView() {
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
        productNameLabel.setText(prodMap.get(order.getP_ID()));
        qtyLabel.setText(String.valueOf(order.getQty()));
        bidLabel.setText(String.valueOf(order.getBid()));
        detailLabel.setText(order.getDetail());
    }

    private void clearSelectedOrder() {
        orderListView.getItems().clear();

        this.order = null;

        productNameLabel.setText("");
        qtyLabel.setText("");
        bidLabel.setText("");
        detailLabel.setText("");
    }

    private void showOrderList() {
        try {
            // Read OrderList
            String sqlText = "SELECT `C_ID`, `OL_ID`, `C_Name`, `Status`, `Con_ID` FROM `order_list` NATURAL JOIN `customer` " +
                    "WHERE `Status` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "P");
            ResultSet result = pst.executeQuery();

            while (result.next()) {

                custMap.put(result.getInt(1), result.getString(3));
                orderList = new OrderList(
                        result.getInt(2),
                        result.getInt(1),
                        result.getString(4),
                        result.getInt(5)
                );
                orderListListView.getItems().add(orderList);
            }
            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void handleSelectedOrderListListView() {
        orderListListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<OrderList>() {
                    @Override
                    public void changed(ObservableValue<? extends OrderList> observable, OrderList oldValue, OrderList newValue) {
                        System.out.println(newValue + " is selected");
                        clearSelectedOrder();
                        showOrder(newValue);
                        disableButtons();
                        showSelectedOrderList(newValue);
                        numProductLabel.setText(String.valueOf(orderList.getNumOrder()));
                        totalBidLabel.setText(String.valueOf(orderList.getTotalBid()));
                    }
                });

    }

    private void showSelectedOrderList(OrderList orderList) {
        this.orderList = orderList;
        cidHyperlink.setDisable(false);

        System.out.println("C_ID = " + orderList.getC_ID());
        cidHyperlink.setText(String.valueOf(orderList.getC_ID()));
        cNameLabel.setText(custMap.get(orderList.getC_ID()));
        olidLabel.setText(String.valueOf(orderList.getOL_ID()));
        statusLabel.setText(OrderList.showStatus(orderList.getStatus()));

        if (contract.getC_Id() == orderList.getC_ID())
            enableButton();
        rejectButton.setDisable(false);
    }

    private void clearSelectedOrderList() {
        orderListListView.getItems().clear();

        this.orderList = null;
        this.order = null;

        cidHyperlink.setText("");
        cNameLabel.setText("");
        olidLabel.setText("");
        numProductLabel.setText("");
        totalBidLabel.setText("");
        statusLabel.setText("");

        cidHyperlink.setDisable(true);
        disableButtons();
    }

    private void disableButtons() {
        acButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    private void enableButton() {
        acButton.setDisable(false);
        rejectButton.setDisable(false);
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static Contract getContract() {
        return contract;
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

    private void checkActiveContract() {
        try {
            String sqlText = "SELECT * FROM `contract` WHERE `Con_status` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "V");
            ResultSet result = pst.executeQuery();

            if (result.next()) {
                this.contract = new Contract(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getString(4),
                        result.getInt(5)
                );
            } else {
                this.contract = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ sql ไม่ได้");
        }
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
                PageChanger.gotoPage("customer_detail");
            } else {
                System.err.println("user not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    @FXML private void handleAcceptButton(ActionEvent event) {
        try {
            String sqlText = "UPDATE `order_list` SET `Status` = ? , `Con_ID` = ? WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "A");
            pst.setInt(2, contract.getCon_Id());
            pst.setInt(3, orderList.getOL_ID());
            pst.executeUpdate();

            pst.close();
            clearSelectedOrder();
            clearSelectedOrderList();
            orderListListView.refresh();
            showOrderList();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    @FXML private void handleRejectButton(ActionEvent event) {
        try {
            String sqlText = "UPDATE `order_list` SET `Status` = ? WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "R");
            pst.setInt(2, orderList.getOL_ID());
            pst.executeUpdate();

            pst.close();
            clearSelectedOrder();
            clearSelectedOrderList();
            orderListListView.refresh();
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        PageChanger.gotoPage("menu_manager");
    }

}