package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.Router;
import ku.cs.model.Order;
import ku.cs.model.OrderList;
import ku.cs.model.Product;
import ku.cs.model.ProductList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuController {

    @FXML
    private Label nameLabel;

    @FXML
    private ListView<Order> orderListView;

    @FXML
    private ListView<Product> productListView;

    @FXML
    public void initialize() {
        nameLabel.setText(LoginController.user.getName());
        showOrderList();
        showProductList();
    }
    private void showOrderList() {
        OrderList orderList = new OrderList();
        try {
            String sqlText = "select * FROM order WHERE C_ID = " + LoginController.user.getId();
            PreparedStatement pst = LoginController.connection.prepareStatement(sqlText);
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
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }

        orderListView.getItems().addAll(orderList.getOrders());
    }

    private void showProductList() {
        ProductList productList = new ProductList();

        try {
            String sqlText = "select * FROM product";
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
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }

        productListView.getItems().addAll(productList.getProducts());
    }

    @FXML
    private void ManageLogoutButton(ActionEvent event) {
        try {
            Router.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปหน้า login จาก menu ไม่ได้");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleManageOrderButton(ActionEvent event) {
        try {
            Router.goTo("manage_order");
        } catch (IOException e) {
            System.err.println("ไปหน้า manage_order จาก menu ไม่ได้");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleViewContractButton(ActionEvent actionEvent) {
        try {
            Router.goTo("view_contract");
        } catch (IOException e) {
            System.err.println("ไปหน้า view_contract จาก menu ไม่ได้");
            e.printStackTrace();
        }
    }
}
