package ku.cs.controller;

import com.mysql.cj.util.StringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ku.cs.Router;
import ku.cs.model.Order;
import ku.cs.model.Product;
import ku.cs.model.ProductList;

import java.io.IOException;
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
        qtyLabel.setText(String.valueOf(product.getQty()));
        ppuLabel.setText(String.valueOf(product.getPPU()));
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
    @FXML private void handleNewProductButton(ActionEvent event) {
        try {
            Router.goTo("new_product");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า new_product จาก manage_product ไม่ได้");
        }
    }

    @FXML private void handleAddButton(ActionEvent event) {
        // reset message
        detailMessageLabel.setText("");

        String addQty = addTextField.getText();

        // Invalid input
        if (addQty.equals("") || Integer.parseInt(addQty) < 1) {
            detailMessageLabel.setText("Invalid Quantity");
            return;
        }
        try {
            int intAddQty = Integer.parseInt(addQty);
            product.addQty(intAddQty);

            String sqlText = "update product set P_Qty = ? where P_ID = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, product.getQty());
            pst.setInt(2, product.getPid());
            pst.executeUpdate();

            pst.close();

            addTextField.setText("");
            addTextField.setPromptText("add product to stock");
            productListView.refresh();
            showSelectedProduct(product);
        } catch (IllegalArgumentException e) {
            detailMessageLabel.setText("Invalid Quantity");
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleChangePPUButton(ActionEvent event) {
        // reset message
        detailMessageLabel.setText("");

        String newPPU = ppuTextField.getText();
        if (newPPU.equals("") || Integer.parseInt(newPPU) < 1) {
            detailMessageLabel.setText("Invalid Price Per Unit");
            return;
        }
        try {
            int intNewPPU = Integer.parseInt(newPPU);
            product.setPPU(intNewPPU);

            String sqlText = "update product set P_PPU = ? where P_ID = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, product.getPPU());
            pst.setInt(2, product.getPid());
            pst.executeUpdate();

            pst.close();

            ppuTextField.setText("");
            ppuTextField.setPromptText("change price per unit");
            productListView.refresh();
            showSelectedProduct(product);
        } catch (IllegalArgumentException e) {
            detailMessageLabel.setText("Invalid Price Per Unit");
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("menu_manager");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า menu_manager จาก manage_product ไม่ได้");
        }
    }

}
