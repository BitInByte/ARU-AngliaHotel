package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ManageBookingView {

    @FXML
    private TableColumn bookingId;

    @FXML
    private TableColumn customerName;

    @FXML
    private TableView bookingstv;

    @FXML
    public void initialize() throws SQLException {
        this.listBookings();
    }

    public void bookingManageHandler() {

    }

    private void listBookings() throws SQLException {
        Booking booking = new Booking();
        ObservableList<Booking> bookingList = booking.listAllBookings();
        System.out.println("Showing info");
        System.out.println(bookingList);
        bookingId.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("bookingId"));
        customerName.setCellValueFactory(new PropertyValueFactory<Booking, String>("fullName"));
        bookingstv.setItems(bookingList);
    }
}
