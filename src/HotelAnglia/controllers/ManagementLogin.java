package HotelAnglia.controllers;

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
//        this.submitbtn.setDisable(true);
    }

    public void submitHandler() {

        UI UI = new UI();


        String query = "SELECT * FROM account WHERE username = '" + this.username.getText() + "' AND password = '" + this.password.getText() + "';";
        ResultSet result = Connect.sqlExecute(query);
        try {

        if(result.next()) {
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
}
