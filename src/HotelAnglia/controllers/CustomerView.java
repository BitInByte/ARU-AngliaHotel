package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerView {

//    Declare UI elements
    @FXML
    private Button newbtn;

    @FXML
    private Button managebtn;

//    Push a manage booking page
    public void pushManageBookingPage() {
        UI UI = new UI();
        UI.closeUIElement(managebtn);
        UI.createUIElement("Manage Booking", "customerManageBookingView");
    }

//    Push a new booking page
    public void pushNewBookingPage() {
        UI UI = new UI();
        UI.closeUIElement(newbtn);
        UI.createUIElement("New Booking", "newBookingView");

    }
}
