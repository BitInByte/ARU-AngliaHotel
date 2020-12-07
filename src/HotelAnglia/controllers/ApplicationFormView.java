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

    private boolean emailValidate;

    private boolean nameValidate;

    private LocalDate reservationDate;

    private String roomType;

    @FXML
    private Button submitbtn;

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


//    @FXML
//    public void initialize() {
//        this.nameValidate = false;
//        this.emailValidate = false;
//        submitbtn.setDisable(true);
//        roomtypel.setText(this.roomType);
//        reservationdatel.setText(this.reservationDate.toString());
//    }

    public void nameValidator() {
        if(!nametf.getText().trim().isEmpty()) {
            this.nameValidate = true;
        } else {
            this.nameValidate = false;
        }

        System.out.println(this.nameValidate);

        if(this.nameValidate && this.emailValidate) {
            submitbtn.setDisable(false);
        } else {
            submitbtn.setDisable(true);
        }
    }

    public void checkEmailValidator() {
//        Initialize the regex expression
        Pattern pattern = Pattern.compile(regex);

//        Checks if the email matches the regex expression
        Matcher matcher = pattern.matcher(emailtf.getText());

//        System.out.println(matcher.matches() ? "Matches" : "Doesnt matches");

//        this.emailValidate = matcher.matches() ? true : false;
        this.emailValidate = matcher.matches();

        System.out.println(emailValidate);

        if(this.nameValidate && this.emailValidate) {
            submitbtn.setDisable(false);
        } else {
            submitbtn.setDisable(true);
        }

    }

    public void submitHandler() throws SQLException, IOException {
        //        Get an available room id
        System.out.println("RoomId");
        Room availableRoom = Room.getAvailableRoom(this.roomType);
        System.out.println(availableRoom.getRoom_id());
        System.out.println(availableRoom.getType());
        System.out.println(availableRoom.getAvailability());
//        Create a new payment
        System.out.println("Payment");
        Payment newPayment = new Payment(paymentcb.getSelectionModel().getSelectedItem().toString(), availableRoom.getPrice());
        newPayment.createNewPayment();
//        Create a new customer
        System.out.println("Customer");
        Customer newCustomer = new Customer(this.nametf.getText(), this.emailtf.getText());
        newCustomer.createNewCustomer();
//        Create new booking
        Booking newBooking = new Booking(this.reservationDate, availableRoom.getRoom_id(), newCustomer.getCustomerId(), newPayment.getPaymentId());
        newBooking.createNewBooking();
//        System.out.println(newPayment.getPaymentId());
//        Reserve Room
        availableRoom.reserveRoom();

//        Open Booking Summary Page
        //        Load new window and controller
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HotelAnglia/views/bookingSummary.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

//        Push new data to the next controller
        BookingSummary controller = loader.getController();
//        controller.initData("It works!");
        controller.initData(availableRoom.getType(), newBooking.getReservationDate().toString(), newCustomer.getFullName(), newCustomer.getEmail(), newPayment.getPaymentMethod());

//        Open new window
        Stage stage = new Stage();
        stage.setTitle("Booking Summary");
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();

//        Close current window
        UI UI = new UI();
        UI.closeUIElement(submitbtn);
    }

    public void closePage() {

    }

//    Fetch data from the newBookView
    public void initData(LocalDate reservationDate, String roomType) {
        this.reservationDate = reservationDate;
        this.roomType = roomType;

        this.nameValidate = false;
        this.emailValidate = false;
        submitbtn.setDisable(true);
        roomtypel.setText(this.roomType);
        reservationdatel.setText(this.reservationDate.toString());
    }
}
