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
import ku.cs.model.OrderList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class ReceiptController {

    @FXML private Label warningLabel;
    @FXML private Button cancelButton;
    @FXML private Button makeReceiptButton;
    @FXML private ListView<OrderList> orderListListView;

    private Contract contract;
    private OrderList orderList;

    @FXML private void initialize() {
        disableButton();
        getCurrentContract();
        showOrderList();
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
            String sqlText = "SELECT `OL_ID`, `Status`, `Con_ID`  FROM `order_list` WHERE `Status` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "W");
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                orderList = new OrderList(
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3)
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

                        // order not in contract
                        if (contract.getCon_Id() != newValue.getCon_ID()) {
                            cancelButton.setDisable(false);
                            makeReceiptButton.setDisable(true);
                            warningLabel.setText("This order is not in current contract");
                            return;
                        }

                        // order in contract
                        warningLabel.setText("");
                        cancelButton.setDisable(true);
                        makeReceiptButton.setDisable(false);
                    }
                });
    }

    @FXML private void handleMakeReceiptButton(ActionEvent event) {
        // TODO: code here

        try {
            Router.goTo("menu_manager");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า menu_manager จาก receipt ไม่ได้");
        }
    }

    @FXML private void handleCancelButton(ActionEvent event) {
        // TODO: code here

        try {
            Router.goTo("menu_manager");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า menu_manager จาก receipt ไม่ได้");
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("menu_manager");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า menu_manager จาก receipt ไม่ได้");
        }
    }

}
