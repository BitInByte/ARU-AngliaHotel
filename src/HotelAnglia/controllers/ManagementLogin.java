package HotelAnglia.controllers;

import HotelAnglia.models.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class ManagementLogin {

//    Declare UI elements
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

//    Perform some actions at element initialization
    @FXML
    public void initialize() {
//        Disable buttons
        this.submitbtn.setDisable(true);

//        Set on key released event listener
        this.username.setOnKeyReleased(keyEvent -> this.validateText());
        this.password.setOnKeyReleased(keyEvent -> this.validateText());
    }

//    Submit handler when the submit button got pushed
    public void submitHandler() {

//        Create new UI empty instance
        UI UI = new UI();

//        Create a new Manager instance with data populated
        Manager managerAccount = new Manager(this.username.getText(), this.password.getText());
//        Create a new result set with the login query results
        ResultSet result = managerAccount.login();
        try {
//            If result have data then loop through it
            if(result.next()) {
//                Store the session details
                Manager.setStoredAccountId(result.getInt("account_id"));
                Manager.setStoredUsername(result.getString("username"));
//                Close this elements
                UI.closeUIElement(this.submitbtn);
//                Create a new UI element
                UI.createUIElement("Management View", "managementView");
            } else {
//                Set a error message in the error label
                this.errorlb.setText("Wrong password or email");
            }
        } catch (Exception e) {
//            Catch possible errors and log it to the console
            e.printStackTrace();
        }
    }

//    Close page after a close button push
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(backbtn);
    }

//    Validate text method
    private void validateText() {
//        If username and password labels have data in it then enable the button, if not, then disable it
        if(this.username.getText().trim().length() > 0 && this.password.getText().trim().length() > 0) {
            this.submitbtn.setDisable(false);
        } else {
            this.submitbtn.setDisable(true);
        }
    }
}
