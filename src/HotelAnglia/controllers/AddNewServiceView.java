package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class AddNewServiceView {

    private Booking selectedBooking;

    @FXML
    private Label customerl;

    @FXML
    private ComboBox<String> servicecb;

    @FXML
    private Button submitbtn;

    @FXML
    private Button backbtn;

    public void initData(Booking selectedBooking) throws SQLException {
//        Disable submit button
        this.submitbtn.setDisable(true);
//        Store the selected booking provided by the last window
        this.selectedBooking = selectedBooking;
//        Set booking id text on the label
//        this.bookingidl.setText(Integer.toString(this.selectedBooking.getBookingId()));
        this.customerl.setText(selectedBooking.getCustomer().getFullName());
//        Add all service types to the combo box
        this.servicecb.getItems().addAll(Service.getServiceTypes());

//        Add an event listener to the service combo box
        servicecb.setOnAction(event -> this.submitbtn.setDisable(false));
//        Lambda expression same than this:
//        servicecb.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println(servicecb.getValue());
//            }
//        });
    }

    public void submitHandler() throws SQLException {
        System.out.println(this.servicecb.getValue());
//        Create new Service instance
        Service newService = new Service();
//        Fill newService instance with service type
        newService.setType(this.servicecb.getValue());
//        Fill newService instance with booking ID
        newService.setBookingId(this.selectedBooking.getBookingId());
//        Query database to get service price and id
        newService.getServiceByType();
//        Query database to add the service to the customer bill
        newService.setNewServiceByBookingId();
//        Close page
        UI UI = new UI();
        UI.closeUIElement(this.submitbtn);
    }

    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.backbtn);
    }
}
