package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import ku.cs.Router;
import ku.cs.model.Product;
import ku.cs.model.ProductList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

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
    @FXML private Label placeOrderMessageLabel;
    // List of products
    @FXML private ListView<Product> productListView;

    private ProductList productList;
    private Product product;
    @FXML private void initialize() {
        nameLabel.setText(LoginController.user.getName());
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
    }

    private void clearSelectedProduct() {
        productNameLabel.setText("-");
        availableQtyLabel.setText("-");
        ppuLabel.setText("-");
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

    @FXML void handlePlaceOrderButton(ActionEvent event) {
        resetTagAndMessage();

        boolean validInput = true;
        boolean validBid;
        boolean validQty;

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
            placeOrderMessageLabel.setText("Invalid order");
            return;
        }

        int qty = Integer.parseInt(qtyTextField.getText());
        int bid = Integer.parseInt(bidTextField.getText());
        String detail = detailTextArea.getText();

        // check for invalid input

        validBid = bid > 0 && bid >= this.product.getQty() * this.product.getPPU();
        validQty = qty > 0 && qty <= this.product.getQty();

        if (!validQty) {
            qtyTag.setText(TAG);
            placeOrderMessageLabel.setText("Invalid quantity");
            return;
        }
        if (!validBid) {
            bidTag.setText(TAG);
            placeOrderMessageLabel.setText("Invalid bid");
            return;
        }

        try {
            String sqlText = "INSERT INTO `order`(C_ID, P_ID, qty, bid, detail, status) " +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, user.getId());
            pst.setInt(2, this.product.getPid());
            pst.setInt(3, qty);
            pst.setInt(4, bid);
            pst.setString(5, detail);
            pst.setString(6, "P");
            pst.executeUpdate();

            pst.close();

            Router.goTo("menu");
        } catch (IOException e) {
            System.err.println("ไปหน้า menu จาก new_order ไม่ได้");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            placeOrderMessageLabel.setText("Invalid Order");
            e.printStackTrace();
        }
    }

    private void resetTagAndMessage() {
        bidTag.setText("");
        detailTag.setText("");
        qtyTag.setText("");
        placeOrderMessageLabel.setText("");
    }

    @FXML void ManageLogoutButton(ActionEvent event) {
        try {
            Router.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปหน้า login จาก new_order ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("manage_order");
        } catch (IOException e) {
            System.err.println("ไปหน้า manage_order จาก new_order ไม่ได้");
            e.printStackTrace();
        }
    }

}
