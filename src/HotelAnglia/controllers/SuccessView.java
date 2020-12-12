package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SuccessView {

    @FXML
    private Label errorl;

    @FXML
    private Button closepagebt;

    public void initData(String error) {
        this.errorl.setText(error);
    }

    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.closepagebt);
    }
}
