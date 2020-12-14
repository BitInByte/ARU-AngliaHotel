package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class SelectedCustomerBookingView {

//    Declare fields
    private Booking selectedBooking;
    private boolean validateCustomerName;
    private boolean validateCustomerEmail;
    private boolean validateReservationDate;

//    Create a new date formatter
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//    Declare UI elements
    @FXML
    private Label bookingidl;

    @FXML
    private TextField customernametf;

    @FXML
    private TextField customeremailtf;

    @FXML
    private Label roomtypel;

    @FXML
    private Label reservationstatusl;

    @FXML
    private DatePicker reservationdatedp;


    @FXML
    private Button submitchangesbtn;

    @FXML
    private Button closebtn;

//    Submit changes handler
    public void submitChangesHandler() throws ParseException, IOException {

//        Create a new UI empty instance
        UI UI = new UI();

//       If date is different then perform date change
        if(!this.validateReservationDate) {
//            Validate if date is in the future
//            Validate date
            LocalDate now = LocalDate.now();
            Date datePicker = dateFormat.parse(this.reservationdatedp.getValue().toString());
            Date dateToday = dateFormat.parse(now.toString());
//                    If the date is in the past, then prompt an error on the error label
            if(dateToday.getTime() > datePicker.getTime()) {
//                Show error modal with an message
                UI.showErrorView("You cannot change a date to a past date!");
//                Break this function here
                return;
            } else {
//                If the date is in the future, then clear the error and enable the submit button
//                Set a new reservation date
                this.selectedBooking.setReservationDate(this.reservationdatedp.getValue());
//                Query database with the new reservation date
                this.selectedBooking.updateReservationDateById();
            }

        }

//        If the customer name failed validation
        if(!this.validateCustomerName) {
//            Set new customer name
            this.selectedBooking.getCustomer().setFullName(this.customernametf.getText());
//            Query database with the new customer name
            this.selectedBooking.getCustomer().updateCustomerName();
        }

//        If the customer email failed validation
        if(!this.validateCustomerEmail) {
//            Set new customer email
            this.selectedBooking.getCustomer().setEmail(this.customeremailtf.getText());
//            Query database with the new customer email
            this.selectedBooking.getCustomer().updateCustomerEmail();
        }

//        Show Success message
        UI.showSuccessView("Your booking as successfully been changed!");
//        Close Page
        UI.closeUIElement(this.submitchangesbtn);

    }

//    Cancel booking handler method
    public void cancelBookingHandler() throws IOException {
//        Cancel Reservation
        this.selectedBooking.cancelReservationById();
//        Create a new UI empty instance
        UI UI = new UI();
//        Show Success message
        UI.showSuccessView("Your booking as successfully been canceled!");
//        Close Page
        UI.closeUIElement(this.submitchangesbtn);
    }

//    Close page handler after a close button push
    public void closePageHandler() {
        UI UI = new UI();
        UI.closeUIElement(this.closebtn);
    }

//    Fetch data from previous controller
    public void initData(Booking selectedBooking) {
//        Fetch data recovered from last page selection
        this.selectedBooking = selectedBooking;
//        Insert data inside of the labels
        this.bookingidl.setText("Booking " + this.selectedBooking.getBookingId());
        this.customernametf.setText(this.selectedBooking.getCustomer().getFullName());
        this.customeremailtf.setText(this.selectedBooking.getCustomer().getEmail());
        this.roomtypel.setText(this.selectedBooking.getRoomType());
        this.reservationstatusl.setText(this.selectedBooking.getStatus());
        this.reservationdatedp.setValue(this.selectedBooking.getReservationDate());
//        Disable buttons
        this.submitchangesbtn.setDisable(true);

//        Set Event Listeners
        this.customernametf.setOnKeyReleased(event -> this.validateData());
//        This lambda expression is the same than this:
//        this.customernametf.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                validateData();
//            }
//        });
        this.customeremailtf.setOnKeyReleased(event -> this.validateData());
        this.reservationdatedp.setOnAction(event -> this.validateData());
    }

//    Data validation method
    private void validateData() {
//        Validate if the customer name on the text field is the same than the selected booking customer name
        this.validateCustomerName = this.customernametf.getText().equals(this.selectedBooking.getCustomer().getFullName());

//        Validate if the customer email on the text field is the same than the selected booking customer email
        this.validateCustomerEmail = this.customeremailtf.getText().equals(this.selectedBooking.getCustomer().getEmail());

//        Validate id the reservation date on the reservation date picker is the same than the selected booking reservation date
        this.validateReservationDate = this.reservationdatedp.getValue().equals(this.selectedBooking.getReservationDate());

//        If all validation success
        if(!this.validateCustomerName || !this.validateCustomerEmail || !this.validateReservationDate) {
//            Enable submit button
            this.submitchangesbtn.setDisable(false);
        } else {
//            If fail, then disable submit button
            this.submitchangesbtn.setDisable(true);
        }
    }
}
