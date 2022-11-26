package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.service.Utilities;

public class MenuManagerController {

    @FXML private void handleReceiptButton(ActionEvent event) {
        Utilities.gotoPage("manager_receipt");
    }

    @FXML private void handleManageContractButton(ActionEvent event) {
        Utilities.gotoPage("manage_contract");
    }

    @FXML private void handleManageOrderButton(ActionEvent event) {
        Utilities.gotoPage("manage_manager_order");
    }

    @FXML private void handleManageProductButton(ActionEvent event) {
        Utilities.gotoPage("manage_product");
    }

    @FXML private void ManageLogoutButton(ActionEvent event) {
        Utilities.gotoPage("login");
    }
}
