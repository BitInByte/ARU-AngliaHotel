package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

//    Declaring UI Elements
    @FXML
    private Button customerBtn;

    @FXML
    private Button managementBtn;

//    Declaring UI Class
//    private static UI UI;

//    Generation Event Handlers
    public void pushCustomerWindow() {
        UI ui = new UI();
//        System.out.println("Executing");
        ui.createUIElement("Customer View", "customerView");
    }

    public void pushManagementWindow() {
        UI ui = new UI();
        ui.createUIElement("Management Login", "managementLogin");
//        ui.closeUIElement();
    }
}
