package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CustomerManageBookingView {

    @FXML
    private Button submitbtn;

    @FXML
    private Button backbtn;

    @FXML
    private TextField bookingidtf;


    @FXML
    public void initialize() {
        submitbtn.setDisable(true);
    }

    public void fieldValidate() {
        if(!bookingidtf.getText().trim().isEmpty()) {
            submitbtn.setDisable(false);
        } else {
            submitbtn.setDisable(true);
        }
    }


    public void submitHandler() {

    }

    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(backbtn);
    }
}
