package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

//    Declaring UI Elements
    @FXML
    private Button customerBtn;

    @FXML
    private Button managementBtn;


//    Push a customer view
    public void pushCustomerWindow() {
        UI ui = new UI();
        ui.createUIElement("Customer View", "customerView");
    }

//    push a management view
    public void pushManagementWindow() {
        UI ui = new UI();
        ui.createUIElement("Management Login", "managementLogin");
    }
}
