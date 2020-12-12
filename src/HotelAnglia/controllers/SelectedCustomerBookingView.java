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

    private Booking selectedBooking;

    private boolean validateCustomerName;

    private boolean validateCustomerEmail;

    private boolean validateReservationDate;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    public void changeBookingHandler() {

    }

    public void submitChangesHandler() throws ParseException, IOException {

        UI UI = new UI();

        //       If date is different then perform date change
        if(!this.validateReservationDate) {
//            Validate if date is in the future
            System.out.println("Date changed");
//            Validate date
            LocalDate now = LocalDate.now();
            Date datePicker = dateFormat.parse(this.reservationdatedp.getValue().toString());
            Date dateToday = dateFormat.parse(now.toString());
            System.out.println(datePicker);
            System.out.println(dateToday);
//                    If the date is in the past, then prompt an error on the error label
            if(dateToday.getTime() > datePicker.getTime()) {
                System.out.println("Cannot perform this action!");
//                UI UI = new UI();
                UI.showErrorView("You cannot change a date to a past date!");
//                Break this function here
                return;
//                errorlb.setText("Invalid Date. Please choose a date in the future!");
            } else {
//                        If the date is in the future, then clear the error and enable the submit button
                System.out.println("Valid date");
//                Set a new reservation date
                this.selectedBooking.setReservationDate(this.reservationdatedp.getValue());
//                Query database with the new reservation date
                this.selectedBooking.updateReservationDateById();
                System.out.println("Date Changed");
//                errorlb.setText("");
//                submitbtn.setDisable(false);
            }

        }

        if(!this.validateCustomerName) {
            System.out.println("Name Changed");
//            Set new customer name
            this.selectedBooking.getCustomer().setFullName(this.customernametf.getText());
//            Query database with the new customer name
            this.selectedBooking.getCustomer().updateCustomerName();
            System.out.println("Name Changed");
//            this.customernametf.setText(this.selectedBooking.getCustomer().getFullName());
        }

        if(!this.validateCustomerEmail) {
            System.out.println("Email changed");
//            Set new customer email
            this.selectedBooking.getCustomer().setEmail(this.customeremailtf.getText());
//            Query database with the new customer email
            this.selectedBooking.getCustomer().updateCustomerEmail();
            System.out.println("Email Changed");
        }



//        Show Success message
        UI.showSuccessView("Your booking as successfully been changed!");
//        Close Page
        UI.closeUIElement(this.submitchangesbtn);

    }

    public void cancelBookingHandler() throws IOException {
//        Cancel Reservation
        this.selectedBooking.cancelReservationById();
        UI UI = new UI();
//        Show Success message
        UI.showSuccessView("Your booking as successfully been canceled!");
//        Close Page
        UI.closeUIElement(this.submitchangesbtn);
    }

    public void closePageHandler() {
        UI UI = new UI();
        UI.closeUIElement(this.closebtn);
    }

    public void initData(Booking selectedBooking) {
//        Fetch data recovered from last page selection
        this.selectedBooking = selectedBooking;
//        Insert data inside of the labels
        this.bookingidl.setText("Booking " + this.selectedBooking.getBookingId());
        this.customernametf.setText(this.selectedBooking.getCustomer().getFullName());
        this.customeremailtf.setText(this.selectedBooking.getCustomer().getEmail());
        this.roomtypel.setText(this.selectedBooking.getRoomType());
        this.reservationstatusl.setText(this.selectedBooking.getStatus());
//        this.reservationdatel.setText(this.selectedBooking.getReservationDate().toString());
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

    private void validateData() {
        System.out.println("Validation in progress");
        System.out.println(this.customernametf.getText().equals(this.selectedBooking.getCustomer().getFullName()));
//        boolean validateCustomerName = this.customernametf.getText().equals(this.selectedBooking.getCustomer().getFullName());
        this.validateCustomerName = this.customernametf.getText().equals(this.selectedBooking.getCustomer().getFullName());

//        boolean validateCustomerEmail = this.customeremailtf.getText().equals(this.selectedBooking.getCustomer().getEmail());
        this.validateCustomerEmail = this.customeremailtf.getText().equals(this.selectedBooking.getCustomer().getEmail());

//        boolean validateReservationDate = this.reservationdatedp.getValue().equals(this.selectedBooking.getReservationDate());
        this.validateReservationDate = this.reservationdatedp.getValue().equals(this.selectedBooking.getReservationDate());

        System.out.println("test validation");
        System.out.println(this.validateCustomerName);
        System.out.println(this.validateCustomerEmail);
        System.out.println(this.validateReservationDate);
//        if(!this.customernametf.getText().equals(this.selectedBooking.getCustomer().getFullName()) || !this.customeremailtf.getText().equals(this.selectedBooking.getCustomer().getEmail())) {
        if(!this.validateCustomerName || !this.validateCustomerEmail || !this.validateReservationDate) {
            this.submitchangesbtn.setDisable(false);
            System.out.println("Not Equal");
        } else {
            this.submitchangesbtn.setDisable(true);
            System.out.println("Equal");
        }
    }
}
