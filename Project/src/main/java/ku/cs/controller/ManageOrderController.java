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
import ku.cs.model.Product;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

public class ManageOrderController {

    // User
    @FXML private Label nameLabel;
    // OrderList
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
    @FXML private Button reserveButton;

    private Order order;
    private static OrderList orderList;
    private static HashMap<Integer, String> map = new HashMap<>();
    @FXML private void initialize() {
        nameLabel.setText(LoginController.user.getName());
        reserveButton.setDisable(true);
        mapIDtoProductName();
        showOrderList();
        clearSelectedOrderList();
        clearSelectedOrder();
        handleSelectedOrderListListView();
        handleSelectedOrderListView();
    }

    private void showOrder() {
        try {
            // Read Order
            String sqlText = "SELECT `P_ID`, `qty`, `bid`, `detail` FROM `order` " +
                    "WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, orderList.getOL_ID());
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Order order = new Order(result.getInt(1),
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
        productNameLabel.setText(map.get(order.getP_ID()));
        qtyLabel.setText(String.valueOf(order.getQty()));
        bidLabel.setText(String.valueOf(order.getBid()));
        detailLabel.setText(order.getDetail());
    }

    private void clearSelectedOrder() {
        this.order = null;

        productNameLabel.setText("-");
        qtyLabel.setText("-");
        bidLabel.setText("-");
        detailLabel.setText("-");
    }

    private void showOrderList() {
        try {
            // Read OrderList
            String sqlText = "SELECT `OL_ID`, `Status` FROM `order_list` WHERE `C_ID` = ?";
            PreparedStatement pst = LoginController.connection.prepareStatement(sqlText);
            pst.setInt(1, user.getId());
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                orderList = new OrderList(
                        result.getInt(1),
                        result.getString(2)
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
                        showOrder();
                        showSelectedOrderList(newValue);
                    }
                }
        );
    }

    private void showSelectedOrderList(OrderList orderList) {
        this.orderList = orderList;
        olidLabel.setText(String.valueOf(orderList.getOL_ID()));
        numProductLabel.setText(String.valueOf(orderList.getNumOrder()));
        totalBidLabel.setText(String.valueOf(orderList.getTotalBid()));
        statusLabel.setText(OrderList.showStatus(orderList.getStatus()));
        if (!orderList.getStatus().equals("A"))
            reserveButton.setDisable(true);
        else
            reserveButton.setDisable(false);
    }

    private void clearSelectedOrderList() {
        orderList = null;
        olidLabel.setText("-");
        numProductLabel.setText("-");
        totalBidLabel.setText("-");
        statusLabel.setText("-");
    }

    public static OrderList getOrderList() {
        return orderList;
    }

    private  void mapIDtoProductName() {
        try {
            String sqlText = "SELECT  `P_ID`, `P_Name` FROM `product`";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                map.put(
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

    @FXML private void handleLogoutButton(ActionEvent event) {
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
}
