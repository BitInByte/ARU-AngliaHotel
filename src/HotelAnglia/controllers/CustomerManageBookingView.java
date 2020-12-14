package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerManageBookingView {

//    Declare UI elements
    @FXML
    private Button changebookingbtn;

    @FXML
    private Button addservicebtn;

    @FXML
    private Button backbtn;

    @FXML
    private TextField bookingidtf;

//    Perform some actions at element initialization
    @FXML
    public void initialize() {
//        Set the change booking button disable at initialization
        this.changebookingbtn.setDisable(true);
    }

//    Validate fields method
    public void fieldValidate() {
//        Check if the data on the booking id text field is empty
        if(!bookingidtf.getText().trim().isEmpty()) {
//            If empty disable the change booking button
            this.changebookingbtn.setDisable(false);
        } else {
//            If not empty, then enable the change booking button
            this.changebookingbtn.setDisable(true);
        }
    }

//    Submit button handler
    public void submitHandler() throws SQLException, IOException {
//        Create a new booking id variable from the booking id text field value
        int bookingID = Integer.parseInt(bookingidtf.getText());
//        Create a new Booking empty instance
        Booking selectedBooking = new Booking();
//        Get a booking by the id
        selectedBooking.getBookingById(bookingID);
//        If there is a booking and the status is either checked in or closed then perform actions
        if(selectedBooking.getBookingId() > 0 && !selectedBooking.getStatus().equals("Checked-In") && !selectedBooking.getStatus().equals("Closed")) {
//            Check if the booking is canceled
            if(selectedBooking.getStatus().equals("Canceled")) {
//                Show an error if booking has been changed
                UI UI = new UI();
                UI.showErrorView("This booking as been canceled and cannot be changed!");
            } else {
//                Open change window
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/HotelAnglia/views/selectedCustomerBookingView.fxml"));
                Parent pushingWindow = loader.load();
                Scene pushingWindowScene = new Scene(pushingWindow);

                SelectedCustomerBookingView controller = loader.getController();
                controller.initData(selectedBooking);

                Stage stage = new Stage();
                stage.setTitle("Booking " + selectedBooking.getBookingId());
                stage.setScene(pushingWindowScene);
                stage.setResizable(false);
                stage.show();
            }
//              Close this element
            UI UI = new UI();
            UI.closeUIElement(this.changebookingbtn);
        } else {
//            Create a new UI empty instance
            UI UI = new UI();
//            Open the error modal and show an error
            UI.showErrorView("There is no booking with that ID or this ID already checked-in or it's closed!");
        }
    }

//    Close page after close button push
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.backbtn);
    }
}
