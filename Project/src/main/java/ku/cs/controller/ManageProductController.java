package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ku.cs.model.Product;
import ku.cs.model.ProductList;
import ku.cs.service.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class ManageProductController {

    // Labels
    @FXML private Label detailMessageLabel;
    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label pidLabel;
    @FXML private Label ppuLabel;
    @FXML private Label qtyLabel;
    // Textfields
    @FXML private TextField addTextField;
    @FXML private TextField ppuTextField;
    // Buttons
    @FXML private Button addButton;
    @FXML private Button changePPUButton;
    // List of products
    @FXML private ListView<Product> productListView;

    private Product product;
    private ProductList productList;

    @FXML private void initialize() {
        disableButtons();
        showProductList();
        clearSelectedProduct();
        handleSelectedListView();
    }

    private void disableButtons() {
        addButton.setDisable(true);
        changePPUButton.setDisable(true);
    }

    private void showProductList() {
        productList = new ProductList();

        try {
            String sqlText = "select * FROM product";
            PreparedStatement pst = connection.prepareStatement(sqlText);
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

    private void handleSelectedListView() {
        productListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                        System.out.println("old value is " + oldValue);
                        System.out.println(newValue + " is selected");
                        showSelectedProduct(newValue);
                    }
                });
    }
    private void showSelectedProduct(Product product) {
        this.product = product;
        pidLabel.setText(String.valueOf(product.getPid()));
        nameLabel.setText(product.getName());
        typeLabel.setText(product.getType());
        qtyLabel.setText(Utilities.thousandSeparator(product.getQty()));
        ppuLabel.setText(Utilities.thousandSeparator(product.getPPU()));
        addButton.setDisable(false);
        changePPUButton.setDisable(false);
    }

    private void clearSelectedProduct() {
        this.product = null;
        pidLabel.setText("");
        nameLabel.setText("");
        typeLabel.setText("");
        qtyLabel.setText("");
        ppuLabel.setText("");
        addButton.setDisable(true);
        changePPUButton.setDisable(true);
    }
    @FXML private void handleAddButton(ActionEvent event) {
        // reset message
        detailMessageLabel.setText("");

        /*
            0 -> check empty input
            1 -> check non-numeric input
            2 -> check valid input range
            3 -> check result value doesn't exceed DB size
        */

        String str = addTextField.getText();
        int qty = 0;
        // check empty input
        if (str.equals("")) {
            detailMessageLabel.setText("Input field must not be empty");
            return;
        }

        // check non-numeric input
        try {
            qty = Integer.parseInt(str);
        } catch (IllegalArgumentException e) {
            detailMessageLabel.setText("Input field can only contain numeric values");
            return;
        }

        // check valid input range
        if (qty < 1 || qty > 99999) {
            detailMessageLabel.setText("Value range out of bound");
            return;
        }

        // check result value doesn't exceed DB size
        if (product.getQty() + qty > 99999) {
            detailMessageLabel.setText("Added quantity will exceed limit (99,999)");
            return;
        }

        try {
            product.addQty(qty);

            String sqlText = "UPDATE product SET P_Qty = ? WHERE P_ID = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, product.getQty());
            pst.setInt(2, product.getPid());
            pst.executeUpdate();

            pst.close();

            addTextField.setText("");
            productListView.refresh();
            showSelectedProduct(product);
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleChangePPUButton(ActionEvent event) {
        // reset message
        detailMessageLabel.setText("");

        /*
            0 -> check empty input
            1 -> check non-numeric input
            2 -> check valid input range
        */

        String str = ppuTextField.getText();
        int ppu = 0;

        // check empty input
        if (str.equals("")) {
            detailMessageLabel.setText("Input field must not be empty");
            return;
        }

        // check non-numeric input
        try {
            ppu = Integer.parseInt(str);
        } catch (IllegalArgumentException e) {
            detailMessageLabel.setText("Input field can only contain numeric values");
            return;
        }

        // check valid input range
        if (ppu < 1 || ppu > 99999) {
            detailMessageLabel.setText("Value range out of bound");
            return;
        }

        try {

            product.setPPU(ppu);

            String sqlText = "UPDATE product SET P_PPU = ? WHERE P_ID = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, product.getPPU());
            pst.setInt(2, product.getPid());
            pst.executeUpdate();

            pst.close();

            ppuTextField.setText("");
            productListView.refresh();
            showSelectedProduct(product);
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleNewProductButton(ActionEvent event) {
        Utilities.gotoPage("new_product");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        Utilities.gotoPage("menu_manager");
    }

}
