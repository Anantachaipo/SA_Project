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
import java.util.Arrays;
import java.util.List;

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
        nameLabel.setText(LoginController.user.getName());
        addOrderButton.setDisable(true);

        checkOrder();
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
                        clearText();
                        showSelectedProduct(newValue);
                    }
                }
        );
    }

    private void showSelectedProduct(Product product) {
        this.product = product;
        productNameLabel.setText(this.product.getName());
        availableQtyLabel.setText(Utilities.thousandSeparator(this.product.getQty()));
        ppuLabel.setText(Utilities.thousandSeparator(this.product.getPPU()));
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
                Product localProd = new Product(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getInt(5)
                );
                if (localProd.getQty() != 0)
                    productList.addProduct(localProd);
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

    private void checkOrder() {
        if (orderList.getNumOrder()<=0) {
            checkoutButton.setDisable(true);
            clearOrderButton.setDisable(true);
        }
        else {
            checkoutButton.setDisable(false);
            clearOrderButton.setDisable(false);
        }
    }

    public static OrderList getOrderList() {
        return orderList;
    }

    @FXML private void handleAddOrderButton(ActionEvent event) {
        resetTagAndMessage();
        /*
            index 0 -> check empty input
            index 1 -> check non-numeric input
            * -> check valid quantity
            * -> check bid is equal or more than minimum
            * -> check valid bid (less than 1,000,000)
            * -> check valid input size
         */
        List<Boolean> check = Arrays.asList(true, true);
        int bid = 0, qty = 0;

        // check for empty input
        if (qtyTextField.getText().isBlank()) {
            check.set(0, false);
            qtyTag.setText(TAG);
        }
        if (bidTextField.getText().isBlank()) {
            check.set(0, false);
            bidTag.setText(TAG);
        }
        if (detailTextArea.getText().isBlank()) {
            check.set(0, false);
            detailTag.setText(TAG);
        }
        if (!check.get(0)){
            addOrderMessageLabel.setText("Input field must not be empty");
            return;
        }

        // check non-numeric input
        try {
            qty = Integer.parseInt(qtyTextField.getText());
        } catch (IllegalArgumentException e) {
            check.set(1, false);
            qtyTag.setText(TAG);
        }
        try {
            bid = Integer.parseInt(bidTextField.getText());
        } catch (IllegalArgumentException e) {
            check.set(1, false);
            bidTag.setText(TAG);
        }
        if (!check.get(1)) {
            addOrderMessageLabel.setText("Input field can only contain numeric values");
            return;
        }

        // check valid quantity
        if (qty > product.getQty()) {
            qtyTag.setText(TAG);
            addOrderMessageLabel.setText("Quantity must be less than available product");
            return;
        } else if (qty <= 0) {
            qtyTag.setText(TAG);
            addOrderMessageLabel.setText("Quantity lower than minimum (1)");
            return;
        } else if (orderList.getOrderByID(product.getPid()) != null) {
            int localQty = qty + orderList.getOrderByID(product.getPid()).getQty();
            if (localQty > product.getQty()) {
                qtyTag.setText(TAG);
                String qtyStr = Utilities.thousandSeparator(localQty);
                addOrderMessageLabel.setText("Total quantity (" + qtyStr + ") exceed available product");
                return;
            }
        }

        // check bid is equal or more than minimum
        int minimum = qty * this.product.getPPU();
        if (bid < minimum) {
            bidTag.setText(TAG);
            String minStr = Utilities.thousandSeparator(minimum);
            addOrderMessageLabel.setText("Your bid is lower than minimum bid (" + minStr + ")");
            return;
        }

        // check valid bid (less than 1,000,000)
        if (bid > 1000000) {
            String maxStr = Utilities.thousandSeparator(1000000);
            bidTag.setText(TAG);
            addOrderMessageLabel.setText("Bid limit exceeded (" + maxStr + ")");
            return;
        }

        // check valid input size
        String detail = detailTextArea.getText();
        if (detail.length() > 200) {
            detailTag.setText(TAG);
            addOrderMessageLabel.setText("Characters input limit exceeded (200)");
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
        orderList.clearOrder();
        Utilities.gotoPage("login");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        orderList.clearOrder();
        Utilities.gotoPage("manage_order");
    }

}
