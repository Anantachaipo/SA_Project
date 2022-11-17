package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

import static ku.cs.controller.LoginController.connection;

public class CustomerDetailController {

    // Customer
    @FXML private Label cidLabel;
    @FXML private Label cNameLabel;
    @FXML private Label cTelLabel;
    @FXML private Label numOrderLabel;
    @FXML private Label numContractLabel;
    // Order
    @FXML private Label olidLabel;
    @FXML private Label numProductLabel;
    @FXML private Label totalBidLabel;
    @FXML private Label statusLabel;

    @FXML private Button makeNewConButton;
    @FXML private ListView<OrderList> orderListListView;

    private static Customer customer;
    private static Contract contract;
    private OrderList orderList = new OrderList();
    private int numOrder = 0;
    private int numContract = 0;
    @FXML private void initialize() {
        // import data
        customer = ManageManagerOrderController.getCustomer();
        contract = ManageManagerOrderController.getContract();
        if (contract==null)
            makeNewConButton.setDisable(false);
        else
            makeNewConButton.setDisable(true);

        findAssociatedContract(customer);
        // show customer
        cidLabel.setText(String.valueOf(customer.getId()));
        cNameLabel.setText(customer.getName());
        cTelLabel.setText(customer.getTel());

        clearSelectedOrderList();
        showOrderList();
        handleSelectedOrderListListView();

        numOrderLabel.setText(String.valueOf(numOrder));
        numContractLabel.setText(String.valueOf(numContract));
    }

    private void findAssociatedContract(Customer customer) {
        try {
            String sqlText = "SELECT * FROM `contract` WHERE `C_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, customer.getId());
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                numContract++;
            }

            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void findAssociatedOrder() {
        try {
            String sqlText = "SELECT `P_ID`, `qty`, `bid` FROM `order` " +
                    "WHERE `OL_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, orderList.getOL_ID());
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                orderList.addOrder(new Order(
                    result.getInt(1),
                    result.getInt(2),
                    result.getInt(3)
                ));
            }

            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void showOrderList() {
        try {
            String sqlText = "SELECT `OL_ID`, `Status` FROM `order_list` NATURAL JOIN `customer` " +
                    "WHERE `C_ID` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, customer.getId());
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                orderList = new OrderList(
                        result.getInt(1),
                        result.getString(2)
                );
                findAssociatedOrder();
                numOrder++;
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
                        showSelectedOrderList(newValue);
                    }
                });
    }

    private void showSelectedOrderList(OrderList orderList) {
        olidLabel.setText(String.valueOf(orderList.getOL_ID()));
        numProductLabel.setText(String.valueOf(orderList.getNumOrder()));
        totalBidLabel.setText(String.valueOf(orderList.getTotalBid()));
        statusLabel.setText(OrderList.showStatus(orderList.getStatus()));
    }

    private void clearSelectedOrderList() {
        olidLabel.setText("-");
        numProductLabel.setText("-");
        totalBidLabel.setText("-");
        statusLabel.setText("-");
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setNullCustomer() {
        customer = null;
    }

    @FXML private void handleMakeNewConButton(ActionEvent event) {
        PageChanger.gotoPage("new_contract");
    }
    @FXML private void handleBackButton(ActionEvent event) {
        PageChanger.gotoPage("manage_manager_order");
    }

}
