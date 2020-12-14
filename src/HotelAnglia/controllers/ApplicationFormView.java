package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Customer;
import HotelAnglia.models.Payment;
import HotelAnglia.models.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationFormView {

//    Regex expression to validate emails
    private static final String regex = "^(.+)@(.+)$";

//    Declaring fields
    private boolean emailValidate;
    private boolean nameValidate;
    private LocalDate reservationDate;
    private String roomType;

//    Declaring UI elements
    @FXML
    private Button submitbtn;

    @FXML
    private Button backbtn;

    @FXML
    private TextField nametf;

    @FXML
    private TextField emailtf;

    @FXML
    private Label roomtypel;

    @FXML
    private Label reservationdatel;

    @FXML
    private ComboBox paymentcb;

//  Validator which validates whether name field is empty or not
    public void nameValidator() {
        if(!nametf.getText().trim().isEmpty()) {
            this.nameValidate = true;
        } else {
            this.nameValidate = false;
        }

//        Validate the nameValidate and the emailValidate to enable submit button
        if(this.nameValidate && this.emailValidate) {
            this.submitbtn.setDisable(false);
        } else {
            this.submitbtn.setDisable(true);
        }
    }

//    Validator which validates if the value inside of the label email is an email or not
    public void checkEmailValidator() {
//        Initialize the regex expression
        Pattern pattern = Pattern.compile(regex);

//        Checks if the email matches the regex expression
        Matcher matcher = pattern.matcher(emailtf.getText());
        this.emailValidate = matcher.matches();

//        Validate the nameValidate and the emailValidate to enable submit button
        if(this.nameValidate && this.emailValidate) {
            this.submitbtn.setDisable(false);
        } else {
            this.submitbtn.setDisable(true);
        }
    }

//    Submit handler to handle submit button push
    public void submitHandler() throws SQLException, IOException {
//        Get an available room id
        Room availableRoom = Room.getAvailableRoom(this.roomType);
//        Create a new payment
        Payment newPayment = new Payment(paymentcb.getSelectionModel().getSelectedItem().toString(), 0.0);
        newPayment.createNewPayment();
//        Create a new customer
        Customer newCustomer = new Customer(this.nametf.getText(), this.emailtf.getText());
        newCustomer.createNewCustomer();
//        Create new booking
        Booking newBooking = new Booking(this.reservationDate, newCustomer, newPayment, this.roomType);
        newBooking.createNewBooking();
//        Reserve Room
//        availableRoom.reserveRoom();

//        Open Booking Summary Page
//        Load new window and controller
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HotelAnglia/views/bookingSummary.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

//        Push new data to the next controller
        BookingSummary controller = loader.getController();
//        controller.initData("It works!");
        controller.initData(newBooking.getBookingId(), availableRoom.getType(), newBooking.getReservationDate().toString(), newCustomer.getFullName(), newCustomer.getEmail(), newPayment.getPaymentMethod());

//        Open new window
        Stage stage = new Stage();
        stage.setTitle("Booking Summary");
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();

//        Close current window
        UI UI = new UI();
        UI.closeUIElement(this.submitbtn);
    }

//    Close page after close button push
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.backbtn);
    }

//    Fetch data from the newBookView
    public void initData(LocalDate reservationDate, String roomType) {
        this.reservationDate = reservationDate;
        this.roomType = roomType;

        this.nameValidate = false;
        this.emailValidate = false;
        this.submitbtn.setDisable(true);
        this.roomtypel.setText(this.roomType);
        this.reservationdatel.setText(this.reservationDate.toString());
    }
}
