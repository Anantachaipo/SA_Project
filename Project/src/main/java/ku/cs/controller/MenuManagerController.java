package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.Router;

import java.io.IOException;

public class MenuManagerController {

    @FXML private void handleReceiptButton(ActionEvent event) {
        try {
            Router.goTo("manager_receipt");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า manager_receipt จาก menu_manager ไม่ได้");
        }
    }

    @FXML private void handleManageContractButton(ActionEvent event) {
        try {
            Router.goTo("manage_contract");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า manage_contract จาก menu_manager ไม่ได้");
        }
    }

    @FXML private void handleManageOrderButton(ActionEvent event) {
        try {
            Router.goTo("manage_manager_order");
        } catch (IOException e) {
            System.err.println("ไปหน้า manage_manager_order จาก menu_manager ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void handleManageProductButton(ActionEvent event) {
        try {
            Router.goTo("manage_product");
        } catch (IOException e) {
            System.err.println("ไปหน้า manage_product จาก menu_manager ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML private void ManageLogoutButton(ActionEvent event) {
        try {
            Router.goTo("login");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า login จาก menu_manager ไม่ได้");
        }
    }
}
