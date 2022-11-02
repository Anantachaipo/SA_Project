package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    @FXML private TextField bidTextField;
    @FXML private TextField productTextField;
    @FXML private TextField qtyTextField;
    @FXML private TextArea detailTextArea;
    @FXML private Label nameLabel;
    @FXML private Label placeOrderMessageLabel;
    @FXML private Label productTag;
    @FXML private Label qtyTag;
    @FXML private Label bidTag;
    @FXML private Label detailTag;
    @FXML private ListView<Product> productListView;

    ProductList productList;
    @FXML private void initialize() {
        nameLabel.setText(LoginController.user.getName());
        showProductList();
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
        boolean validBid = false;
        boolean validQty = false;

        // check for empty input
        if (productTextField.getText().isBlank()) {
            validInput = false;
            productTag.setText(TAG);
        }
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

        String product = productTextField.getText();
        int qty = Integer.parseInt(qtyTextField.getText());
        int bid = Integer.parseInt(bidTextField.getText());
        String detail = detailTextArea.getText();

        // TODO: เช็ค product qty ว่าถูกต้อง
        // check for invalid input
        Product checkProduct = productList.getProductByName(product);
        if (checkProduct == null) {
            productTag.setText(TAG);
            placeOrderMessageLabel.setText("Invalid order");
            return;
        }

        validBid = bid > 0 && bid > checkProduct.getQty() * checkProduct.getPPU();
        validQty = qty > 0 && qty < checkProduct.getQty();

        if (!validBid) {
            bidTag.setText(TAG);
            placeOrderMessageLabel.setText("Invalid order");
            return;
        }

        if (!validQty) {
            qtyTag.setText(TAG);
            placeOrderMessageLabel.setText("Invalid quantity");
            return;
        }

        try {
            String sqlText = "insert into order (C_ID, P_ID, qty, bid, detail, status) " +
                    "values (?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, user.getId());
            pst.setInt(2, checkProduct.getPid());
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
        productTag.setText("");
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
