package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ManagementView {

    @FXML
    private GridPane rootPane;

    public void pushManageBookingsView() throws IOException {
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/HotelAnglia/views/manageBookingView.fxml"));
//        rootPane.getChildren().setAll(pane);
        UI UI = new UI();
        UI.changePane(rootPane, "manageBookingView");
    }

    public void pushManageRoomsView() {

    }

    public void pushManageServicesView() {

    }

    public void pushCheckInView() {

    }

    public void pushCheckOutView() {

    }

    public void pushRepostView() {

    }

    public void pushAdministrativeView() {

    }

    public void pushAboutView() {

    }
}
