package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import ku.cs.Router;
import ku.cs.model.*;
import ku.cs.service.Utilities;


import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

public class ReceiptController {

    @FXML private Label numProductLabel;
    @FXML private Label totalBidLabel;
    @FXML private Label totalQtyLabel;
    @FXML private Label warningLabel;
    @FXML private Button cancelButton;
    @FXML private Button makeReceiptButton;
    @FXML private ListView<OrderList> orderListListView;

    private HashMap<Integer, String> nameMap = new HashMap<>();
    private HashMap<Integer, Integer> qtyMap = new HashMap<>();

    private Contract contract;
    private OrderList orderList;

    @FXML private void initialize() {
        mapProdID();
        disableButton();
        getCurrentContract();
        showOrderList();
        clearOrderContent();
        handleSelectedOrderList();
    }

    private void disableButton() {
        cancelButton.setDisable(true);
        makeReceiptButton.setDisable(true);
    }

    private void getCurrentContract() {
        try {
            String sqlText = "SELECT `Con_ID` FROM `contract` WHERE `Con_status` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "V");
            ResultSet result = pst.executeQuery();

            if (result.next())
                this.contract = new Contract(result.getInt(1));

            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void showOrderList() {
        try {
            // Read OrderList
            String sqlText = "SELECT `OL_ID`, `C_ID`,`Status`, `Con_ID`  FROM `order_list` WHERE `Status` = ? AND `C_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "W");
            pst.setInt(2, user.getId());
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                orderList = new OrderList(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getInt(4)
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

    private void handleSelectedOrderList() {
        orderListListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<OrderList>() {
                    @Override
                    public void changed(ObservableValue<? extends OrderList> observable, OrderList oldValue, OrderList newValue) {
                        System.out.println(newValue + " is selected");
                        clearOrderContent();
                        orderList.clearOrder();
                        orderList = newValue;
                        getOrderContent();
                        showOrderContent();
                        // order not in contract
                        if (contract == null) {
                            cancelButton.setDisable(false);
                            makeReceiptButton.setDisable(true);
                            warningLabel.setText("This order is not in current contract");
                            return;
                        }
                        // not enough available product
                        for (Order order : orderList.getOrders()) {
                            int pid = order.getP_ID();
                            if (order.getQty() > qtyMap.get(pid)) {

                                cancelButton.setDisable(false);
                                makeReceiptButton.setDisable(true);
                                warningLabel.setText("There is not enough product for one or more order");
                                return;
                            }
                        }

                        // order in contract
                        warningLabel.setText("");
                        cancelButton.setDisable(false);
                        makeReceiptButton.setDisable(false);
                    }
                });
    }

    @FXML private void handleMakeReceiptButton(ActionEvent event) {
        try {
            // Update Product
            String sqlText = "UPDATE `product` SET `P_QTY` = ? where `P_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            for (Order order : orderList.getOrders()) {
                int pid = order.getP_ID();
                pst.setInt(1, qtyMap.get(pid) - order.getQty());
                pst.setInt(2, pid);
                pst.executeUpdate();
            }

            // Update status of order_list
            String sqlText2 = "UPDATE `order_list` SET `Status` = ? WHERE `OL_ID` = ?";
            pst = connection.prepareStatement(sqlText2);
            pst.setString(1, "S");
            pst.setInt(2, orderList.getOL_ID());
            pst.executeUpdate();

            // Add new receipt
            String sqlText3 = "INSERT INTO `receipt`(`C_ID`, `OL_ID`) VALUES (?,?)";
            pst = connection.prepareStatement(sqlText3);
            pst.setInt(1, orderList.getC_ID());
            pst.setInt(2, orderList.getOL_ID());
            pst.executeUpdate();

            pst.close();

            Utilities.gotoPage("view_receipt");
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    private void getOrderContent() {
        try {
            String sqlText = "SELECT `P_ID`, `qty`, `bid` FROM `order` " +
                    "WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, orderList.getOL_ID());
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                Order order = new Order(result.getInt(1),
                        result.getInt(2),
                        result.getInt(3)
                );
                orderList.addOrder(order);

            }

            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void showOrderContent() {
        totalQtyLabel.setText(Utilities.thousandSeparator(orderList.getTotalQty()));
        numProductLabel.setText(Utilities.thousandSeparator(orderList.getNumOrder()));
        totalBidLabel.setText(Utilities.thousandSeparator(orderList.getTotalBid()));
    }

    private void clearOrderContent() {
        totalQtyLabel.setText("-");
        numProductLabel.setText("-");
        totalBidLabel.setText("-");
    }

    private  void mapProdID() {
        try {
            String sqlText = "SELECT  `P_ID`, `P_Name`, `P_Qty` FROM `product`";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                nameMap.put(
                        result.getInt(1),
                        result.getString(2)
                );
                qtyMap.put(
                        result.getInt(1),
                        result.getInt(3)
                );
            }
            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ sql ไม่ได้");
        }
    }

    @FXML private void handleCancelButton(ActionEvent event) {
        try {
            String sqlText = "UPDATE `order_list` SET `Status` = ? WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "F");
            pst.setInt(2, orderList.getOL_ID());
            pst.executeUpdate();

            pst.close();
            Utilities.gotoPage("view_receipt");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ sql ไม่ได้");
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        Utilities.gotoPage("view_receipt");
    }

}
