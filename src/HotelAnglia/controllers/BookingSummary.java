package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BookingSummary {

//    Declare UI elements
    @FXML
    private Label bookingidl;

    @FXML
    private Label roomTypel;

    @FXML
    private Label reservationDatel;

    @FXML
    private Label fullNamel;

    @FXML
    private Label emaill;

    @FXML
    private Label paymentMethodl;

    @FXML
    private Button closepagebt;

//    Close page after close button push
    public void closePage() {
        UI ui = new UI();
        ui.closeUIElement(closepagebt);
    }

//    Init data fetched from the last window
    public void initData(int roomID, String roomType, String reservationDate, String fullName, String email, String paymentMethod) {
        this.bookingidl.setText(Integer.toString(roomID));
        this.roomTypel.setText(roomType);
        this.reservationDatel.setText(reservationDate);
        this.fullNamel.setText(fullName);
        this.emaill.setText(email);
        this.paymentMethodl.setText(paymentMethod);
    }
}
