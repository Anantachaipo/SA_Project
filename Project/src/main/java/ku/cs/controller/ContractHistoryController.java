package ku.cs.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.Router;
import ku.cs.model.Contract;
import ku.cs.model.ContractList;
import ku.cs.model.Order;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

public class ContractHistoryController {

    @FXML private Label conIdLabel;
    @FXML private Label cIdLabel;
    @FXML private Label conLenLabel;
    @FXML private Label depositLabel;
    @FXML private Label statusLabel;
    @FXML private ListView<Contract> contractListView;

    private Contract contract;

    @FXML private void initialize() {
        showContractList();
        clearSelectedContract();
        handleSelectedListView();
    }

    private void handleSelectedListView() {
        contractListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Contract>() {
                    @Override
                    public void changed(ObservableValue<? extends Contract> observableValue, Contract oldValue, Contract newValue) {
                        System.out.println(newValue + "is selected");
                        showSelectedContract(newValue);
                    }
                }
        );
    }
    private void showSelectedContract(Contract contract) {
        this.contract = contract;
        conIdLabel.setText(String.valueOf(contract.getCon_Id()));
        cIdLabel.setText(String.valueOf(contract.getC_Id()));
        conLenLabel.setText(String.valueOf(contract.getCon_Length()));
        depositLabel.setText(String.valueOf(contract.getCon_Deposit()));
        statusLabel.setText(Contract.showStatus(contract.getCon_Status()));
    }

    private void clearSelectedContract() {
        statusLabel.setText("-");
        depositLabel.setText("-");
        conLenLabel.setText("-");
        conIdLabel.setText("-");
        cIdLabel.setText("-");
    }

    private void showContractList() {
        ContractList contractList = new ContractList();
        try {
            String sqlText = "select * from `contract`";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                contractList.addContract(new Contract(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getString(4),
                        result.getInt(5)
                ));
            }

            result.close();
            pst.close();
        } catch (SQLException e) {
            System.err.println("ใช้ SQL ไม่ได้");
            e.printStackTrace();
        }
        contractListView.getItems().addAll(contractList.getContracts());
    }


    @FXML private void handleBackButton(ActionEvent event) {
        try {
            Router.goTo("manage_contract");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปหน้า manage_contract จาก contract_history ไม่ได้");
        }
    }

}
