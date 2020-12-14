package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ManagementView {

//    Declare UI elements
    @FXML
    private GridPane rootPane;

//    The following methods are throwing errors when a possible error may occur and us logging it in the console

//    Push manager booking view method
    public void pushManageBookingsView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "manageBookingView");
    }

//    Push manager rooms view methods
    public void pushManageRoomsView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "manageRoomView");
    }

//    Push manager rooms price view method
    public void pushManageRoomsPriceView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "manageRoomPriceView");
    }

//    push manager services view method
    public void pushManageServicesView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "orderServiceView");
    }

//    push check in view method
    public void pushCheckInView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "checkInView");
    }

//    push check out view method
    public void pushCheckOutView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "checkOutView");
    }

//    push revenue view method
    public void pushRevenueView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "revenueView");
    }

//    push administrative view method
    public void pushAdministrativeView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "changePasswordView");
    }

//    push about view method
    public void pushAboutView() throws IOException {
        UI UI = new UI();
        UI.changePane(this.rootPane, "aboutView");
    }

//    push logout method
    public void logoutHandler() {
        UI UI = new UI();
        UI.closeUIElement(this.rootPane);
    }
}
