package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BookingSummary {

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

    public void closePage() {
        UI ui = new UI();
        ui.closeUIElement(closepagebt);
    }

    public void initData(String roomType, String reservationDate, String fullName, String email, String paymentMethod) {
        this.roomTypel.setText(roomType);
        this.reservationDatel.setText(reservationDate);
        this.fullNamel.setText(fullName);
        this.emaill.setText(email);
        this.paymentMethodl.setText(paymentMethod);
    }


}
