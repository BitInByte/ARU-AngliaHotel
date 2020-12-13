package HotelAnglia.controllers;

import HotelAnglia.models.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class ManagementLogin {

    @FXML
    private Button submitbtn;

    @FXML
    private Button backbtn;

    @FXML
    private Label errorlb;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void initialize() {
//        errorlb.setVisible(false);
//        this.backbtn.setDisable(true);
        this.submitbtn.setDisable(true);

        this.username.setOnKeyReleased(keyEvent -> this.validateText());
        this.password.setOnKeyReleased(keyEvent -> this.validateText());
    }

    public void submitHandler() {

        UI UI = new UI();

        Manager managerAccount = new Manager(this.username.getText(), this.password.getText());
        ResultSet result = managerAccount.login();

//        Connect.resultPrinter(result);


//        String query = "SELECT account_id, username FROM management_user WHERE username = '" + this.username.getText() + "' AND password = crypt('" + this.password.getText() + "', password);";
//        System.out.println(query);
//        ResultSet result = Connect.sqlExecute(query);
        try {

            if(result.next()) {
//                Store the session details
                Manager.setStoredAccountId(result.getInt("account_id"));
                Manager.setStoredUsername(result.getString("username"));
                System.out.println("Entered");
                UI.closeUIElement(this.submitbtn);
                UI.createUIElement("Management View", "managementView");
            } else {
                this.errorlb.setText("Wrong password or email");
                System.out.println("Wrong password or email");
                Connect.resultPrinter(result);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(backbtn);
    }

    private void validateText() {
        if(this.username.getText().trim().length() > 0 && this.password.getText().trim().length() > 0) {
            this.submitbtn.setDisable(false);
        } else {
            this.submitbtn.setDisable(true);
        }
    }
}
