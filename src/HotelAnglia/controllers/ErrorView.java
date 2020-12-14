package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ErrorView {

//    Declare UI elements
    @FXML
    private Label errorl;

    @FXML
    private Button closepagebt;

//    Get the error message and display it on the error label
    public void initData(String error) {
        this.errorl.setText(error);
    }

//    Close this elements after a close button push
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.closepagebt);
    }
}
