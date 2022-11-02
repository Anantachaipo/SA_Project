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

public class MenuController {

    @FXML
    private Label nameLabel;

    @FXML
    private ListView<Order> orderListView;

    @FXML
    private ListView<Product> productListView;


    @FXML
    public void initialize() {
        nameLabel.setText(LoginControlller.user.getName());
        showOrderList();
        showProductList();
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


    private void showOrderList() {
        OrderList orderList = new OrderList();
        // TODO: ใช้ DB เอาข้อมูล orderList

        orderListView.getItems().addAll(orderList.getOrders());
    }
    private void showProductList() {
        ProductList productList = new ProductList();
        // TODO: ใช้ DB เอาข้อมูล productList

        productListView.getItems().addAll(productList.getProducts());
    }
}
