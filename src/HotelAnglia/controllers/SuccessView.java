package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SuccessView {

//    Declare UI elements
    @FXML
    private Label errorl;

    @FXML
    private Button closepagebt;

//    Fetch data from the previous controller
    public void initData(String error) {
        this.errorl.setText(error);
    }

//    Close page after close button push
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.closepagebt);
    }
}
