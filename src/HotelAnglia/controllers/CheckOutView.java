package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Room;
import HotelAnglia.models.Service;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CheckOutView {

//    Date formatter to format date type
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//    Declare UI elements
    @FXML
    private TableColumn<Booking, Number> bookingid;

    @FXML
    private TableColumn<Booking, String> customerfullname;

    @FXML
    private TableColumn<Booking, String> customeremail;

    @FXML
    private  TableColumn<Booking, String> checkinDate;

    @FXML
    private TableColumn<Booking, String> roomtype;

    @FXML
    private TableColumn<Booking, String> roomnumber;

    @FXML
    private TableView<Booking> bookingstv;

    @FXML
    private Button submitbtn;

    @FXML
    private Button cancelbtn;

//    Perform actions at element initialization
    @FXML
    public void initialize() throws SQLException {
//        Call listCheckedInBookings method
        this.listCheckedInBookings();
//        Set submit button to disable at element startup
        this.submitbtn.setDisable(true);
    }

//    Check out handler
    public void checkOutHandler() throws IOException, ParseException, SQLException {
//        Create a calculation to get the days between the selected booking date to the date now
        long daysBetween = ChronoUnit.DAYS.between(this.bookingstv.getSelectionModel().getSelectedItem().getBookingDate(), LocalDate.now());

//        If the check in was made in less than one day then the check out cannot proceed
        if(daysBetween < 1) {
//            Create an UI empty instance
            UI UI = new UI();
//            Create an error element with an error message
            UI.showErrorView("The Customer stayed less than 1 day!");
        } else {
//            The days are bigger than 1 then...
//            Get selected item from the table view
            Booking selectedBooking = this.bookingstv.getSelectionModel().getSelectedItem();
//            Set checkout date
            selectedBooking.setCheckoutDate(LocalDate.now());
//            Query database to update the checkout date
            selectedBooking.updateCheckOutDateById();
//            Get the days that the customer got inside of the hotel
//            Get days stayed by the customer
            Date checkIn = dateFormat.parse(selectedBooking.getReservationDate().toString());
            Date checkOut = dateFormat.parse(selectedBooking.getCheckoutDate().toString());
            long daysStayed = ((checkOut.getTime() - checkIn.getTime()) / (1000*60*60*24));
//            Load all services on booking class
            selectedBooking.setServices(Service.getAllServicesByBookingId(selectedBooking.getBookingId()));
//            Get the price
            double price = daysStayed * selectedBooking.getRoom().getPrice();
//            Get Services total price
            double servicesTotalPrice = Service.getTotalPriceByBookingID(selectedBooking.getBookingId());
//            Sum price with servicesTotalPrice to get the customer bill
            price += servicesTotalPrice;
//            Update the total payment price
            selectedBooking.getPayment().setTotalPrice(price);
//            Query database to update total price
            selectedBooking.getPayment().updatePaymentTotalPriceById();
//            Open Payment Window
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/HotelAnglia/views/bookingPaymentView.fxml"));
            Parent pushingWindow = loader.load();
            Scene pushingWindowScene = new Scene(pushingWindow);

            BookingPaymentView controller = loader.getController();
            controller.initData(selectedBooking);

            Stage stage = new Stage();
            stage.setTitle("Payment ");
            stage.setScene(pushingWindowScene);
            stage.setResizable(false);
            stage.show();
//            Close window
            UI UI = new UI();
            UI.closeUIElement(this.submitbtn);
        }
    }

//    Button enable handler to enable the button after some validation
    public void buttonEnableHandler() {
        this.submitbtn.setDisable(false);
    }

//    Close the page handler to close the page after a close button push
    public void closePageHandler() {
        UI UI = new UI();
        UI.closeUIElement(cancelbtn);
    }

//    List all checked in bookings method
    private void listCheckedInBookings() throws SQLException {
//        Create a new Booking empty instance
        Booking booking = new Booking();
//        Get all checked in bookings list
        ObservableList<Booking> checkedInBookingList = booking.listAllCheckedInBookings();

//        If there is checked in bookigs
        if(!checkedInBookingList.isEmpty()) {
//            Lambda expressions to populate data to the table view in order to be able to access booking related data
            this.bookingid.setCellValueFactory(bookingObject -> new SimpleIntegerProperty(bookingObject.getValue().getBookingId()));
            this.customerfullname.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getFullName()));
            this.customeremail.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getEmail()));
            this.checkinDate.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getReservationDate().toString()));
            this.roomtype.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoom().getType()));
            this.roomnumber.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoom().getRoom_number()));
//            Populate Booking instance
            this.bookingstv.setItems(checkedInBookingList);
        } else {
//            If there is no checkin bookings today
//            Clear booking table view
            this.bookingstv.getItems().clear();
//            Populate an error message on the bookings table view
            this.bookingstv.setPlaceholder(new Label("No more bookings to Check out today"));
        }
    }
}
