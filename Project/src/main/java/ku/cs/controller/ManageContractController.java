package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ku.cs.Router;
import ku.cs.model.Contract;
import ku.cs.model.Customer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;

public class ManageContractController {

    @FXML private Label conIdLabel;
    @FXML private Label cIdLabel;
    @FXML private Label cNameLabel;
    @FXML private Label conLenLabel;
    @FXML private Label conDepositLabel;
    @FXML private Label conStatusLabel;
    @FXML private Button newContractButton;
    @FXML private Button terminateContractButton;

    private Contract contract;

    @FXML private void initialize() {
        showContract();
    }
    private void showContract() {
        // get current active contract
        try {
            String sqlText = "select * from `contract` where `Con_Status` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setString(1, "V");
            ResultSet result = pst.executeQuery();

            if (result.next()) {
                contract = new Contract(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getString(4),
                        result.getInt(5)
                );
                sqlText = "select C_Name from `customer` where `C_ID` = " + contract.getC_Id();
                pst = connection.prepareStatement(sqlText);
                result = pst.executeQuery();
                String name = "";
                if (result.next())
                    name = result.getString(1);

                pst.close();
                result.close();

                newContractButton.setDisable(true);
                terminateContractButton.setDisable(false);
                fillText(contract, name);
            } else {
                contract = null;
                fillText("-");
                newContractButton.setDisable(false);
                terminateContractButton.setDisable(true);
            }
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            fillText("-");
            e.printStackTrace();
        }
    }
    private void fillText(String s) {
        conIdLabel.setText(s);
        conLenLabel.setText(s);
        conDepositLabel.setText(s);
        conStatusLabel.setText(s);
        cIdLabel.setText(s);
        cNameLabel.setText(s);
    }
    private void fillText(Contract contract, String name) {
        conIdLabel.setText(String.valueOf(contract.getCon_Id()));
        conLenLabel.setText(String.valueOf(contract.getCon_Length()));
        conDepositLabel.setText(String.valueOf(contract.getCon_Deposit()));
        conStatusLabel.setText(Contract.showStatus(contract.getCon_Status()));
        cIdLabel.setText(String.valueOf(contract.getC_Id()));
        cNameLabel.setText(name);
    }

    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("menu_manager");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า menu_manager จาก manage_contract ไม่ได้");
        }
    }
    @FXML private void handleContractHistButton(ActionEvent event) {
        try {
            Router.goTo("contract_history");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า contract_history จาก manage_contract ไม่ได้");
        }
    }
    @FXML private void handleNewContractButton(ActionEvent event) {
        try {
            Router.goTo("new_contract");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า new_contract จาก manage_contract ไม่ได้");
        }
    }
    @FXML private void handleTerminateContractButton(ActionEvent event) {
        try {
            String sqlText = "update `contract` set `Con_Status` = " + "'T'" +
                    " where `Con_Status` = " + "'V'";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.executeUpdate();

            pst.close();
            showContract();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

}
