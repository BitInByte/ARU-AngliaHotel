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

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    @FXML
    public void initialize() throws SQLException {
        this.listCheckedInBookings();
        this.submitbtn.setDisable(true);
    }

    public void checkOutHandler() throws IOException, ParseException, SQLException {
//        long daysBetween = Duration.between(this.bookingstv.getSelectionModel().getSelectedItem().getBookingDate(), LocalDate.now()).toDays();
//        https://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-java
        long daysBetween = ChronoUnit.DAYS.between(this.bookingstv.getSelectionModel().getSelectedItem().getBookingDate(), LocalDate.now());
        System.out.println(daysBetween);

        if(daysBetween < 1) {
            UI UI = new UI();
            UI.showErrorView("The Customer stayed less than 1 day!");
        } else {
            System.out.println("More than 1 day");
//            System.out.println(LocalDate.now());
//            Get selected item from the table view
            Booking selectedBooking = this.bookingstv.getSelectionModel().getSelectedItem();
//            System.out.println(this.bookingstv.getSelectionModel().getSelectedItem().getReservationDate());
//            Set checkout date
            selectedBooking.setCheckoutDate(LocalDate.now());
//            Query database to update the checkout date
            selectedBooking.updateCheckOutDateById();
//            System.out.println("Days");
//            Date datePicker = dateFormat.parse(this.reservationdatedp.getValue().toString());
//            Get the days that the customer got inside of the hotel
//            Get days stayed by the customer
            Date checkIn = dateFormat.parse(selectedBooking.getReservationDate().toString());
            Date checkOut = dateFormat.parse(selectedBooking.getCheckoutDate().toString());
            long daysStayed = ((checkOut.getTime() - checkIn.getTime()) / (1000*60*60*24));
//            LocalDate checkin = LocalDate.parse(selectedBooking.getReservationDate(), dateFormat);
//            System.out.println(selectedBooking.getReservationDate());
//            System.out.println(selectedBooking.getCheckoutDate());
//            System.out.println(Duration.between(selectedBooking.getCheckoutDate(), selectedBooking.getReservationDate()).toDays());
//            System.out.println(daysStayed);
//            System.out.println(selectedBooking.getRoom().getPrice());
//            System.out.println("PRICE");
//            Load all services on booking class
            selectedBooking.setServices(Service.getAllServices(selectedBooking.getBookingId()));
//            System.out.println("SERVICES");
//            for (Service service : selectedBooking.getServices()) {
//                System.out.println(service.getType());
//            }
//            Get the price
            double price = daysStayed * selectedBooking.getRoom().getPrice();
//            Get Services total price
            double servicesTotalPrice = Service.getTotalPriceByBookingID(selectedBooking.getBookingId());
//            System.out.println(servicesTotalPrice);
//            Sum price with servicesTotalPrice to get the customer bill
            price += servicesTotalPrice;
//            System.out.println(price);
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

    public void buttonEnableHandler() {
        this.submitbtn.setDisable(false);
    }

    public void closePageHandler() {
        UI UI = new UI();
        UI.closeUIElement(cancelbtn);
    }

    private void listCheckedInBookings() throws SQLException {
        Booking booking = new Booking();
        ObservableList<Booking> checkedInBookingList = booking.listAllCheckedInBookings();
        System.out.println("Showing info");
        System.out.println(checkedInBookingList.isEmpty());

        if(!checkedInBookingList.isEmpty()) {
            this.bookingid.setCellValueFactory(bookingObject -> new SimpleIntegerProperty(bookingObject.getValue().getBookingId()));
            this.customerfullname.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getFullName()));
            this.customeremail.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getEmail()));
            this.checkinDate.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getReservationDate().toString()));
            this.roomtype.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoom().getType()));
            this.roomnumber.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoom().getRoom_number()));
            this.bookingstv.setItems(checkedInBookingList);
        } else {
            System.out.println("Empty");
            bookingstv.getItems().clear();
            bookingstv.setPlaceholder(new Label("No more bookings to Check out today"));
        }
    }
}
