package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.model.OrderList;
import ku.cs.model.Product;
import ku.cs.model.ProductList;
import ku.cs.service.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.user;

public class MenuController {

    @FXML
    private Label nameLabel;

    @FXML
    private ListView<OrderList> orderListView;

    @FXML
    private ListView<Product> productListView;

    @FXML
    public void initialize() {
        nameLabel.setText(user.getName());
        showOrderList();
        showProductList();
    }
    private void showOrderList() {
        OrderList orderList;
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

                orderListView.getItems().add(orderList);
            }

        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    private void showProductList() {
        ProductList productList = new ProductList();

        try {
            String sqlText = "SELECT * FROM `product`";
            PreparedStatement pst = LoginController.connection.prepareStatement(sqlText);
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                productList.addProduct(new Product(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getInt(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }

        productListView.getItems().addAll(productList.getProducts());
    }

    @FXML
    private void ManageLogoutButton(ActionEvent event) {
        Utilities.gotoPage("login");
    }
    @FXML
    private void handleManageOrderButton(ActionEvent event) {
        Utilities.gotoPage("manage_order");
    }
    @FXML private void handleViewContractButton(ActionEvent actionEvent) {
        Utilities.gotoPage("view_contract");
    }

    @FXML private void handleViewReceiptButton(ActionEvent actionEvent) {
        Utilities.gotoPage("view_receipt");
    }
}
