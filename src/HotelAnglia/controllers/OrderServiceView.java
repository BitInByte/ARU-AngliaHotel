package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
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

public class OrderServiceView {


    @FXML
    private TableColumn<Booking, String> roomnumber;

    @FXML
    private TableColumn<Booking, String> bookingid;

    @FXML
    private TableColumn<Booking, String> customerfullname;

    @FXML
    private TableColumn<Booking, String> roomtype;

    @FXML
    private TableView<Booking> bookingstv;

    @FXML
    private Button cancelbtn;


    @FXML
    public void initialize() throws SQLException {
//        List bookings
        this.listBookings();
    }

    public void addServiceHandler() throws IOException, SQLException {

//        Retrieve selected booking object
        Booking selectedBooking = this.bookingstv.getSelectionModel().getSelectedItem();

//                Open add new service window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HotelAnglia/views/addNewServiceView.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

        AddNewServiceView controller = loader.getController();
        controller.initData(selectedBooking);

        Stage stage = new Stage();
        stage.setTitle("Booking " + selectedBooking.getBookingId());
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();
    }

    public void closePageHandler() {
        UI UI = new UI();
        UI.closeUIElement(this.cancelbtn);
    }

    private void listBookings() throws SQLException {
        Booking booking = new Booking();
        ObservableList<Booking> checkedInBookingList = booking.listAllCheckedInBookings();
        if(!checkedInBookingList.isEmpty()) {
            this.roomnumber.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoom().getRoom_number()));
            this.bookingid.setCellValueFactory(bookingObject -> new SimpleStringProperty(Integer.toString(bookingObject.getValue().getBookingId())));
            this.customerfullname.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getCustomer().getFullName()));
            this.roomtype.setCellValueFactory(bookingObject -> new SimpleStringProperty(bookingObject.getValue().getRoom().getType()));
            this.bookingstv.setItems(checkedInBookingList);
        } else {
            System.out.println("Empty");
            this.bookingstv.getItems().clear();
            this.bookingstv.setPlaceholder(new Label("No more bookings to Check out today"));
        }
    }
}
