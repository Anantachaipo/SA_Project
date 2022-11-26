package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.model.Order;
import ku.cs.model.OrderList;
import ku.cs.model.Product;
import ku.cs.model.ProductList;
import ku.cs.service.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewOrderController {

    private static final String TAG = "*";
    // customer name
    @FXML private Label productNameLabel;
    // Product info
    @FXML private Label nameLabel;
    @FXML private Label availableQtyLabel;
    @FXML private Label ppuLabel;
    // Customer inputs
    @FXML private TextField bidTextField;
    @FXML private TextField qtyTextField;
    @FXML private TextArea detailTextArea;
    // Tags and Message
    @FXML private Label qtyTag;
    @FXML private Label bidTag;
    @FXML private Label detailTag;
    @FXML private Label addOrderMessageLabel;
    // Buttons
    @FXML private Button addOrderButton;
    @FXML private Button checkoutButton;
    @FXML private Button clearOrderButton;
    // List of products
    @FXML private ListView<Product> productListView;

    private ProductList productList = new ProductList();
    private Product product;
    private static OrderList orderList = new OrderList();

    @FXML private void initialize() {
        orderList.clearOrder();
        nameLabel.setText(LoginController.user.getName());
        addOrderButton.setDisable(true);
        if (orderList.getNumOrder()<=0) {
            checkoutButton.setDisable(true);
            clearOrderButton.setDisable(true);
        }
        else {
            checkoutButton.setDisable(false);
            clearOrderButton.setDisable(false);
        }
        showProductList();
        clearSelectedProduct();
        handleSelectedListView();
    }

    private void handleSelectedListView() {
        productListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedProduct(newValue);
                    }
                }
        );
    }

    private void showSelectedProduct(Product product) {
        this.product = product;
        productNameLabel.setText(this.product.getName());
        availableQtyLabel.setText(String.valueOf(this.product.getQty()));
        ppuLabel.setText(String.valueOf(this.product.getPPU()));
        addOrderButton.setDisable(false);
    }

    private void clearSelectedProduct() {
        this.product = null;
        productNameLabel.setText("-");
        availableQtyLabel.setText("-");
        ppuLabel.setText("-");
        addOrderButton.setDisable(true);
    }

    private void showProductList() {
        productList = new ProductList();

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

    private void resetTagAndMessage() {
        bidTag.setText("");
        detailTag.setText("");
        qtyTag.setText("");
        addOrderMessageLabel.setText("");
    }

    private void clearText() {
        detailTextArea.clear();
        qtyTextField.clear();
        bidTextField.clear();
    }

    public static OrderList getOrderList() {
        return orderList;
    }
    @FXML private void handleAddOrderButton(ActionEvent event) {
        resetTagAndMessage();

        boolean validBid, validQty, validInput = true;

        // check for empty input
        if (qtyTextField.getText().isBlank()) {
            validInput = false;
            qtyTag.setText(TAG);
        }
        if (bidTextField.getText().isBlank()) {
            validInput = false;
            bidTag.setText(TAG);
        }
        if (detailTextArea.getText().isBlank()) {
            validInput = false;;
            detailTag.setText(TAG);
        }
        if (!validInput){
            addOrderMessageLabel.setText("Invalid order");
            return;
        }

        // check illegal input
        int bid = 0, qty = 0;
        try {
            qty = Integer.parseInt(qtyTextField.getText());
            validQty = qty > 0 && qty <= this.product.getQty();
        } catch (IllegalArgumentException e) {
            validQty = false;
            System.err.println("Illegal bid");
        }
        try {
            bid = Integer.parseInt(bidTextField.getText());
            int minimun = qty * this.product.getPPU();
            validBid = bid > 0 && bid >= minimun;
        } catch (IllegalArgumentException e) {
            validBid = false;
            System.err.println("Illegal bid");
        }

        String detail = detailTextArea.getText();

        // check for invalid input
        if (!validQty) {
            qtyTag.setText(TAG);
            addOrderMessageLabel.setText("Invalid quantity");
            return;
        }
        if (!validBid) {
            bidTag.setText(TAG);
            addOrderMessageLabel.setText("Invalid bid");
            return;
        }

        // check duplicate order
        if (orderList.getOrderByID(product.getPid()) != null) {
            // duplicated order found
            orderList.getOrderByID(product.getPid()).addBid(bid);
            orderList.getOrderByID(product.getPid()).addQty(qty);
            orderList.getOrderByID(product.getPid()).changeDetail(detail);
        } else {
            Order order = new Order(product.getPid(), qty, bid, detail);
            orderList.addOrder(order);
        }

        checkoutButton.setDisable(false);
        clearOrderButton.setDisable(false);

        clearSelectedProduct();
        clearText();
    }

    @FXML private void handleCheckoutButton(ActionEvent event) {
        Utilities.gotoPage("new_order_checkout");
    }

    @FXML private void handleClearOrderButton(ActionEvent event) {
        orderList.clearOrder();
        checkoutButton.setDisable(true);
        clearOrderButton.setDisable(true);
        clearSelectedProduct();
        clearText();
    }
    @FXML private void ManageLogoutButton(ActionEvent event) {
        Utilities.gotoPage("login");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        orderList.clearOrder();
        Utilities.gotoPage("manage_order");
    }

}
