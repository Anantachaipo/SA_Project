package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.Router;
import ku.cs.model.Order;
import ku.cs.model.OrderList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

public class NewOrderCheckoutController {

    // Customer
    @FXML private Label nameLabel;
    // Orderlist
    @FXML private Label numProductLabel;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBidLabel;
    // Order
    @FXML private Label productNameLabel;
    @FXML private Label qtyLabel;
    @FXML private Label bidLabel;
    @FXML private Label detailLabel;

    @FXML private Button placeOrderButton;
    @FXML private CheckBox confirmCheckbox;
    @FXML private ListView<Order> orderListView;

    private OrderList orderList = NewOrderController.getOrderList();
    private HashMap<Integer, String> prodMap = new HashMap<>();

    @FXML private void initialize() {
        mapIDtoProductName();
        nameLabel.setText(user.getName());
        placeOrderButton.setDisable(true);
        showOrderList();
        showDescription();
        clearSelectedOrder();
        handleSelectedOrder();
    }

    private void handleSelectedOrder() {
        orderListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Order>() {
                    @Override
                    public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedOrder(newValue);
                    }
                }
        );
    }

    private void showSelectedOrder(Order order) {
        productNameLabel.setText(prodMap.get(order.getP_ID()));
        qtyLabel.setText(String.valueOf(order.getQty()));
        bidLabel.setText(String.valueOf(order.getBid()));
        detailLabel.setText(order.getDetail());
    }

    private void clearSelectedOrder() {
        productNameLabel.setText("");
        qtyLabel.setText("");
        bidLabel.setText("");
        detailLabel.setText("");
    }

    private void showOrderList() {
        orderListView.getItems().addAll(orderList.getOrders());
    }

    private void showDescription() {
        numProductLabel.setText(String.valueOf(orderList.getNumOrder()));
        totalQtyLabel.setText(String.valueOf(orderList.getTotalQty()));
        totalBidLabel.setText(String.valueOf(orderList.getTotalBid()));
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

    @FXML private void handleConfirmCheckbox(ActionEvent event) {
        if (confirmCheckbox.isSelected())
            placeOrderButton.setDisable(false);
        else
            placeOrderButton.setDisable(true);
    }

    @FXML private void handlePlaceOrderButton(ActionEvent event) {
        try {
            // Select OL_ID that exist in Database
            String sqlText1 = "SELECT `OL_ID` FROM `order_list` WHERE `C_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText1);
            pst.setInt(1, user.getId());
            ResultSet result = pst.executeQuery();

            ArrayList<Integer> arr = new ArrayList<>();
            while(result.next()) {
                arr.add(result.getInt(1));
            }

            result.close();

            // Insert into order_list
            String sqlText2 = "INSERT INTO `order_list` (`C_ID`,`Status`) " +
                    "VALUES (?,?)";
            pst = connection.prepareStatement(sqlText2);
            pst.setInt(1, user.getId());
            pst.setString(2, "P");
            pst.executeUpdate();

            // Select new OL_ID
            String sqlText3 = "SELECT `OL_ID` FROM `order_list` WHERE `C_ID` = ?";
            pst = connection.prepareStatement(sqlText3);
            pst.setInt(1, user.getId());
            ResultSet result2 = pst.executeQuery();

            int OL_ID = -1;
            while(result2.next()) {
                System.out.println("Current OL_ID = " + result2.getInt(1));
                if (!arr.contains(result2.getInt(1)))
                    OL_ID = result2.getInt(1);
            }

            result2.close();

            // Insert into order
            String sqlText4 = "INSERT INTO `order`(`OL_ID`, `P_ID`, `qty`, `bid`, `detail`) " +
                    "VALUES (?,?,?,?,?)";
            pst = connection.prepareStatement(sqlText4);

            for (Order order : orderList.getOrders()) {
                pst.setInt(1, OL_ID);
                pst.setInt(2, order.getP_ID());
                pst.setInt(3, order.getQty());
                pst.setInt(4, order.getBid());
                pst.setString(5, order.getDetail());
                pst.executeUpdate();
            }

            pst.close();

            Router.goTo("menu");
        } catch (IOException e) {
            System.err.println("ไปหน้า menu จาก new_order ไม่ได้");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("new_order");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า new_order จาก new_order_checkout ไม่ได้");
        }
    }

    @FXML private void handleLogoutButton(ActionEvent event) {
        try {
            Router.goTo("login");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า login จาก new_order_checkout ไม่ได้");
        }
    }

}
