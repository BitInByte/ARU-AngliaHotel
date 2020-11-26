package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerView {

    @FXML
    private Button newbtn;

    @FXML
    private Button managebtn;

    public void pushManageBookingPage() {
        UI UI = new UI();
        UI.closeUIElement(managebtn);
        UI.createUIElement("Manage Booking", "customerManageBookingView");
    }

    public void pushNewBookingPage() {
        UI UI = new UI();
        UI.closeUIElement(newbtn);
        UI.createUIElement("New Booking", "newBookingView");

    }
}
