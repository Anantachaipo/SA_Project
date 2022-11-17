package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.Router;
import ku.cs.model.Contract;
import ku.cs.service.PageChanger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ku.cs.controller.LoginController.connection;
import static ku.cs.controller.LoginController.user;

public class ViewContractController {

    @FXML private Label nameLabel;
    @FXML private Label conIdLabel;
    @FXML private Label conLenLabel;
    @FXML private Label conDepositLabel;
    @FXML private Label conStatusLabel;

    private Contract contract;
    @FXML private void initialize() {
        nameLabel.setText(user.getName());
        try {
            String sqlText = "SELECT * FROM `contract` WHERE `C_ID` = ? AND  `Con_Status` = ?";
            PreparedStatement pst = connection.prepareStatement(sqlText);
            pst.setInt(1, user.getId());
            pst.setString(2, "V");
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                contract = new Contract(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getString(4),
                        result.getInt(5)
                );
                fillText(contract);
            } else {
                fillText("-");
            }

            pst.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ใช้ SQL ไม่ได้");
        }
    }

    private void fillText(String s) {
        conIdLabel.setText(s);
        conLenLabel.setText(s);
        conDepositLabel.setText(s);
        conStatusLabel.setText(s);
    }
    private void fillText(Contract contract) {
        conIdLabel.setText(String.valueOf(contract.getCon_Id()));
        conLenLabel.setText(String.valueOf(contract.getCon_Length()));
        conDepositLabel.setText(String.valueOf(contract.getCon_Deposit()));
        conStatusLabel.setText(Contract.showStatus(contract.getCon_Status()));
    }

    @FXML private void ManageLogoutButton(ActionEvent event) {
        PageChanger.gotoPage("login");
    }

    @FXML private void handleBackButton(ActionEvent event) {
        PageChanger.gotoPage("menu");
    }

}
