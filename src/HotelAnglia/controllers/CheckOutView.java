package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Room;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CheckOutView {

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
    public void initialize() throws SQLException {
        this.listCheckedInBookings();
        this.submitbtn.setDisable(true);
    }

    public void checkOutHandler() throws IOException {
//        long daysBetween = Duration.between(this.bookingstv.getSelectionModel().getSelectedItem().getBookingDate(), LocalDate.now()).toDays();
        long daysBetween = ChronoUnit.DAYS.between(this.bookingstv.getSelectionModel().getSelectedItem().getBookingDate(), LocalDate.now());
        System.out.println(daysBetween);

        if(daysBetween < 1) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/HotelAnglia/views/errorView.fxml"));
            Parent pushingWindow = loader.load();
            Scene pushingWindowScene = new Scene(pushingWindow);

            ErrorView controller = loader.getController();
            controller.initData("The Customer stayed less than 1 day!");

            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(pushingWindowScene);
            stage.setResizable(false);
            stage.show();
        }

    }

    public void buttonEnableHandler() {
        this.submitbtn.setDisable(false);

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
