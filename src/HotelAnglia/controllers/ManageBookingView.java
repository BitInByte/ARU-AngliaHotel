package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Customer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.SimpleTimeZone;

public class ManageBookingView {

    private Booking selectedBooking;

    @FXML
    private TableColumn<Booking, Integer> bookingId;

    @FXML
    private TableColumn<Booking, LocalDate> reservationId;

    @FXML
    private TableColumn<Booking, String> bookingStatus;

    @FXML
    private TableColumn<Booking, LocalDate> bookingDate;

    @FXML
    private TableColumn<Booking, String> customerName;

    @FXML
    private TableColumn<Booking, Number> bookingTotalPrice;

    @FXML
    private TableColumn<Booking, String> customerEmail;

    @FXML
    private TableColumn<Booking, String> paymentMethod;

    @FXML
    private TableColumn<Booking, String> paymentStatus;

    @FXML
    private TableColumn<Booking, String> paymentDate;

    @FXML
    private TableColumn<Booking, Number> roomId;

    @FXML
    private TableColumn<Booking, String> roomType;

    @FXML
    private Button approvebtn;

    @FXML
    private Button modifybtn;

    @FXML
    private Button managebtn;

    @FXML
    private Button deletebtn;

    @FXML
    private TableView<Booking> bookingstv;

    @FXML
    public void initialize() throws SQLException {
//        List bookings
        this.listBookings();
//        Disable all buttons at initialization
        this.swapButtons(false);
    }

    public void bookingManageHandler() {
//        Got the object selected
        this.selectedBooking = bookingstv.getSelectionModel().getSelectedItem();
//        Check if it got a selected booking
        if(this.selectedBooking != null) {
          this.swapButtons(true);
        } else {
            this.swapButtons(false);
        }
    }

    public void approveBookingHandler() throws SQLException {
        System.out.println(this.selectedBooking.getBookingId());
//        Approve the book in the database
        this.selectedBooking.approveBooking();
//        Update the tableView to the new values
        this.listBookings();
    }

    private void listBookings() throws SQLException {
//        Create a new booking object
        Booking booking = new Booking();
//        Get observableList from the booking class listAllBookings object
        ObservableList<Booking> bookingList = booking.listAllBookings();
        System.out.println("Showing info");
        System.out.println(bookingList);
//        Create values to fill the tableview columns
        this.bookingId.setCellValueFactory(new PropertyValueFactory("bookingId"));
        this.reservationId.setCellValueFactory(new PropertyValueFactory("reservationDate"));
//        reservationId.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getReservationDate().toString()));
        this.bookingStatus.setCellValueFactory(new PropertyValueFactory("status"));
        this.bookingDate.setCellValueFactory(new PropertyValueFactory("bookingDate"));
//        Lambda Expressions to access the Related Objects inside of the Booking object. With that we can access the customer, the payment and the room
        this.customerName.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getFullName()));
        this.bookingTotalPrice.setCellValueFactory(bookingObject -> new SimpleDoubleProperty(bookingObject.getValue().getPayment().getTotalPrice()));
        this.customerEmail.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getEmail()));
        this.paymentMethod.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getPayment().getPaymentMethod()));
        this.paymentStatus.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getPayment().getIsPaid()));
        this.paymentDate.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getPayment().getPaymentDate()));
//        paymentDate.setCellValueFactory(bookingObject -> new Simpl;

//        paymentDate.setCellValueFactory(column -> {
//           return new TableCell<Booking, Date>() {
//             SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//
//             @Override
//               protected void updateItem(Date item, boolean empty) {
//                 super.updateItem(item, empty);
//                 if(!empty) {
//                     setText(format.format(item));
//                 } else {
//                     setText("");
//                     setGraphic(null);
//                 }
//             }
//           };
//        });
//        roomId.setCellValueFactory(bookingObject -> new SimpleIntegerProperty(bookingObject.getValue().getRoom().getRoom_id()));
        this.roomType.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoomType()));

//        bookingId.setCellValueFactory(bookingObject -> new SimpleIntegerProperty(bookingObject.getValue().getBookingId()));
//        customerName.setCellValueFactory(new PropertyValueFactory<Booking, String>("fullName"));

        this.bookingstv.setItems(bookingList);
    }

    private void swapButtons(boolean isVisible ) {
//        Swap buttons to get them enable or disable
        if (isVisible) {
            this.approvebtn.setDisable(false);
            this.modifybtn.setDisable(false);
            this.managebtn.setDisable(false);
            this.deletebtn.setDisable(false);
        } else {
            this.approvebtn.setDisable(true);
            this.modifybtn.setDisable(true);
            this.managebtn.setDisable(true);
            this.deletebtn.setDisable(true);
        }
    }
}
