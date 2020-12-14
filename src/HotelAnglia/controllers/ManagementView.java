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
        UI.changePane(this.rootPane, "manageBookingView");
    }

    public void pushManageRoomsView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "manageRoomView");
    }

    public void pushManageRoomsPriceView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "manageRoomPriceView");
    }

    public void pushManageServicesView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "orderServiceView");
    }

    public void pushCheckInView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "checkInView");
    }

    public void pushCheckOutView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "checkOutView");
    }

    public void pushRevenueView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "revenueView");
    }

    public void pushAdministrativeView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "changePasswordView");
    }

    public void pushAboutView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "aboutView");
    }

    public void logoutHandler() {
        UI UI = new UI();
        UI.closeUIElement(this.rootPane);
    }
}
