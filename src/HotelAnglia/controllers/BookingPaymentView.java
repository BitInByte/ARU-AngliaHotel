package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class BookingPaymentView {

//    Declare fields
    private Booking checkedOutBooking;

//    Declare UI elements
    @FXML
    private Label customernamel;

    @FXML
    private Label totalpricel;

    @FXML
    private Button proceedbtn;

//    Proceed payment handler for when proceed button got pushed
    public void proceedPaymentHandler() throws IOException, ParseException {
//        Query Database isPaid to 1
        this.checkedOutBooking.getPayment().updatePaymentIsPaidById();

//        Query Database booking status closed
        checkedOutBooking.closeBookingByID();

//        Set Payment Date
        this.checkedOutBooking.getPayment().updatePaymentDateById();

//        Change room availability to available
        this.checkedOutBooking.getRoom().updateRoomAvailabilityById("Available");


//            Open Bill Window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HotelAnglia/views/customerBill.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

        CustomerBill controller = loader.getController();
        controller.initData(this.checkedOutBooking);

        Stage stage = new Stage();
        stage.setTitle("Customer Bill");
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();
//            Close window
        UI UI = new UI();
        UI.closeUIElement(this.proceedbtn);
    }

//    Init data fetched from the last window
    public void initData(Booking checkedOutBooking) {
//        Store the checked out booking retrieved from the last window
        this.checkedOutBooking = checkedOutBooking;
//        Set the customer name text on the label
        this.customernamel.setText(this.checkedOutBooking.getCustomer().getFullName());
//        Set the total price text on the label
        this.totalpricel.setText(Double.toString(this.checkedOutBooking.getPayment().getTotalPrice()) + "£");
    }
}
