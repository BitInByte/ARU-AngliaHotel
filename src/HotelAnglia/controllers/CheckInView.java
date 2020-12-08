package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Room;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;

public class CheckInView {

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
    public void initialize() throws SQLException {
        this.listTodaysBookings();
    }

    public void getRoomNumbers() throws SQLException {
        Booking selectedBooking = this.bookingstv.getSelectionModel().getSelectedItem();
        String roomType = selectedBooking.getRoomType();
        this.listRooms(roomType);
    }

    public void submitCheckInHandler() throws SQLException {
//        Got the selected booking from the table view
        Booking selectedBooking = bookingstv.getSelectionModel().getSelectedItem();
//        Got the selected room from the combo box
        Room selectedRoom = Room.getRoomByRoomNumber(availableRoomscb.getValue().toString());
//        selectedBooking.setRoom(selectedRoom);
//        Proceed to the booking check in
        selectedBooking.checkIn(selectedRoom);
//        Update the table view
        this.listTodaysBookings();
//        Emptying combo box
        this.availableRoomscb.getItems().clear();
    }

    private void listRooms(String type) throws SQLException {
        ArrayList<String> roomNumbers = Room.getRoomNumberByType(type);
        availableRoomscb.getItems().addAll(roomNumbers);
    }

    private void listTodaysBookings() throws SQLException {
        Booking booking = new Booking();
        ObservableList<Booking> todaysBookingList = booking.listAllTodaysBookings();
        System.out.println("Showing info");
//        System.out.println(todaysBookingList == null);
        System.out.println(todaysBookingList.isEmpty());

        if(!todaysBookingList.isEmpty()) {
            System.out.println("Not Empty");
            //        Populate data into the table view with lambda expression
            bookingid.setCellValueFactory(bookingObject -> new SimpleIntegerProperty(bookingObject.getValue().getBookingId()));
            customerfullname.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getFullName()));
            customeremail.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getEmail()));
            roomtype.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoomType()));
            bookingstv.setItems(todaysBookingList);
        } else {
            System.out.println("Empty");
            bookingstv.getItems().clear();
            bookingstv.setPlaceholder(new Label("No Bookings Today"));

        }


    }


}
