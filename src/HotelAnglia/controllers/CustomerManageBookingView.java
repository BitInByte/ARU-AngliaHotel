package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerManageBookingView {

    @FXML
    private Button changebookingbtn;

    @FXML
    private Button addservicebtn;

    @FXML
    private Button backbtn;

    @FXML
    private TextField bookingidtf;


    @FXML
    public void initialize() {

        this.changebookingbtn.setDisable(true);
//        this.addservicebtn.setDisable(true);
    }

    public void fieldValidate() {
        if(!bookingidtf.getText().trim().isEmpty()) {
            this.changebookingbtn.setDisable(false);
//            this.addservicebtn.setDisable(false);
        } else {
            this.changebookingbtn.setDisable(true);
//            this.addservicebtn.setDisable(true);
        }
    }


    public void submitHandler() throws SQLException, IOException {
        int bookingID = Integer.parseInt(bookingidtf.getText());
        System.out.println(bookingID);
        Booking selectedBooking = new Booking();
        selectedBooking.getBookingById(bookingID);
        if(selectedBooking.getBookingId() > 0 && !selectedBooking.getStatus().equals("Checked-In") && !selectedBooking.getStatus().equals("Closed")) {
            System.out.println("Found a booking");
            System.out.println(selectedBooking.getBookingId());
//            Check if the booking is canceled
            if(selectedBooking.getStatus().equals("Canceled")) {
//                Show an error if booking has been changed
                UI UI = new UI();
                UI.showErrorView("This booking as been canceled and cannot be changed!");
            } else {
//                Open change window
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/HotelAnglia/views/selectedCustomerBookingView.fxml"));
                Parent pushingWindow = loader.load();
                Scene pushingWindowScene = new Scene(pushingWindow);

                SelectedCustomerBookingView controller = loader.getController();
                controller.initData(selectedBooking);

                Stage stage = new Stage();
                stage.setTitle("Booking " + selectedBooking.getBookingId());
                stage.setScene(pushingWindowScene);
                stage.setResizable(false);
                stage.show();
            }

            UI UI = new UI();
            UI.closeUIElement(this.changebookingbtn);
        } else {
            System.out.println("Booking not found, show an error");
            UI UI = new UI();
            UI.showErrorView("There is no booking with that ID or this ID already checked-in or it's closed!");
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/HotelAnglia/views/errorView.fxml"));
//            Parent pushingWindow = loader.load();
//            Scene pushingWindowScene = new Scene(pushingWindow);
//
//            ErrorView controller = loader.getController();
//            controller.initData("There is no booking with that ID!");
//
//            Stage stage = new Stage();
//            stage.setTitle("Error");
//            stage.setScene(pushingWindowScene);
//            stage.setResizable(false);
//            stage.show();
        }
    }

    public void addServiceHandler() throws SQLException, IOException {
        int bookingID = Integer.parseInt(bookingidtf.getText());
        System.out.println(bookingID);
        Booking selectedBooking = new Booking();
        selectedBooking.getBookingById(bookingID);
        if (selectedBooking.getStatus().equals("Checked-In")) {
            System.out.println("Checked In");
            //                Open change window
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

        } else {
            System.out.println("Not Checked In");
            UI UI = new UI();
            UI.showErrorView("You can only order services if you already checked in!");
        }
    }

    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.backbtn);
    }
}
