package HotelAnglia.controllers;

import HotelAnglia.models.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordView {

    @FXML
    private PasswordField oldpasswordpf;

    @FXML
    private PasswordField newpasswordpf;

    @FXML
    private PasswordField repeatedpasswordpf;

    @FXML
    private Label errorlb;

    @FXML
    private Button submitbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    public void initialize() {
//        Disable submit button
        this.submitbtn.setDisable(true);
//        Create event listen to watch any key press
        this.oldpasswordpf.setOnKeyReleased(keyEvent -> this.validateText());
        this.newpasswordpf.setOnKeyReleased(keyEvent -> this.validateText());
        this.repeatedpasswordpf.setOnKeyReleased(keyEvent -> this.validateText());
    }


    public void submitChangePasswordHandler() throws SQLException {
//        Verify password
        Manager changeManagerPassword = new Manager(Manager.getStoredUsername(), this.oldpasswordpf.getText());
        ResultSet result = changeManagerPassword.login();
        if (result.next()) {
            System.out.println("Success");
//            Verify if both are password match
            if(this.newpasswordpf.getText().equals(this.repeatedpasswordpf.getText())) {
                System.out.println("Match");
//                Set Values on the changeManagerPassword object
                changeManagerPassword.setAccountId(Manager.getStoredAccountId());
                changeManagerPassword.setUsername(Manager.getStoredUsername());
                changeManagerPassword.setPassword(this.newpasswordpf.getText());
//                Query database to change password
                changeManagerPassword.changePassword();
                this.errorlb.setText("Success!");
            } else {
                System.out.println("Doesnt Match");
//                If both password doesnt match, then show an error
                this.errorlb.setText("New password and repeat password doesn't match!");
            }
        } else {
            System.out.println("Fail");
            this.errorlb.setText("Wrong password, please insert your correct old password!");
        }
    }

//    Close Page
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.cancelbtn);

    }

    private void validateText() {
//        Enable button if all password fields have text
        if(this.oldpasswordpf.getText().trim().length() > 0 && this.newpasswordpf.getText().trim().length() > 0 && this.repeatedpasswordpf.getText().trim().length() > 0) {
            this.submitbtn.setDisable(false);
        } else {
            this.submitbtn.setDisable(true);
        }
    }
}
