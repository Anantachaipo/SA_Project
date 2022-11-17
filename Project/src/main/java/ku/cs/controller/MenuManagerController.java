package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.Router;
import ku.cs.service.PageChanger;

import java.io.IOException;

public class MenuManagerController {

    @FXML private void handleReceiptButton(ActionEvent event) {
        PageChanger.gotoPage("manager_receipt");
    }

    @FXML private void handleManageContractButton(ActionEvent event) {
        PageChanger.gotoPage("manage_contract");
    }

    @FXML private void handleManageOrderButton(ActionEvent event) {
        PageChanger.gotoPage("manage_manager_order");
    }

    @FXML private void handleManageProductButton(ActionEvent event) {
        PageChanger.gotoPage("manage_product");
    }

    @FXML private void ManageLogoutButton(ActionEvent event) {
        PageChanger.gotoPage("login");
    }
}
