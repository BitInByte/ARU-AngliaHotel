package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Room;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class CheckInView {

//    Declare UI elements
    @FXML
    private TableColumn<Booking, Number> bookingid;

    @FXML
    private TableColumn<Booking, String> customerfullname;

    @FXML
    private TableColumn<Booking, String> customeremail;

    @FXML
    private TableColumn<Booking, String> roomtype;

    @FXML
    private TableView<Booking> bookingstv;

    @FXML
    private ComboBox availableRoomscb;

    @FXML
    private Button cancelbtn;

//    Perform actions when element initialization
    @FXML
    public void initialize() throws SQLException {
        this.listTodaysBookings();
    }

//    Get all room numbers
    public void getRoomNumbers() throws SQLException {
//        Create a new Booking instance by the selected booking from the table view
        Booking selectedBooking = this.bookingstv.getSelectionModel().getSelectedItem();
//        Get the room type from the selectedBooking instance
        String roomType = selectedBooking.getRoomType();
//        Call listRooms method with the selected roomType
        this.listRooms(roomType);
    }

//    Submit Check In Handler to perform actions after a submit button push
    public void submitCheckInHandler() throws SQLException {
//        Got the selected booking from the table view
        Booking selectedBooking = bookingstv.getSelectionModel().getSelectedItem();
//        Got the selected room from the combo box
        Room selectedRoom = Room.getRoomByRoomNumber(availableRoomscb.getValue().toString());
//        selectedBooking.setRoom(selectedRoom);
//        Proceed to the booking check in
        selectedBooking.checkInById(selectedRoom);
//        Update the table view
        this.listTodaysBookings();
//        Emptying combo box
        this.availableRoomscb.getItems().clear();
    }

//    Close the page after a close button push
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.cancelbtn);
    }

//    List all rooms
    private void listRooms(String type) throws SQLException {
//        Create a new array list to store all room types
        ArrayList<String> roomNumbers = Room.getRoomNumberByType(type);
//        Attribute the room types to the combo box
        availableRoomscb.getItems().addAll(roomNumbers);
    }

//    List todays bokings method
    private void listTodaysBookings() throws SQLException {
//        Create a new Booking empty instance
        Booking booking = new Booking();
//        Retrieve all todays booking from database
        ObservableList<Booking> todaysBookingList = booking.listAllTodaysBookings();

//        If there is todays booking
        if(!todaysBookingList.isEmpty()) {
            //        Populate data into the table view with lambda expression to be able to get information from related booking tables
            this.bookingid.setCellValueFactory(bookingObject -> new SimpleIntegerProperty(bookingObject.getValue().getBookingId()));
            this.customerfullname.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getFullName()));
            this.customeremail.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getEmail()));
            this.roomtype.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoomType()));
            this.bookingstv.setItems(todaysBookingList);
        } else {
//            Clear items on table view
            this.bookingstv.getItems().clear();
//            Show a message that there is no bookings today
            this.bookingstv.setPlaceholder(new Label("No Bookings Today"));
        }
    }
}
