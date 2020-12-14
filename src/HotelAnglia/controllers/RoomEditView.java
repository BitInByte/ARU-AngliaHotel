package HotelAnglia.controllers;

import HotelAnglia.models.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;

public class RoomEditView {

//    Declare fields
    private Room room;

//    Declare UI elements
    @FXML
    private Label roomidel;

    @FXML
    private Label roomiddl;

    @FXML
    private Label roomavailabilityl;

    @FXML
    private Label errorl;

    @FXML
    private Label roomidl;

    @FXML
    private ComboBox<String> roomavailabilitycb;

    @FXML
    private Button submitbt;

//    Change room availability handler
    public void changeRoomAvailabilityHandler() {
//        If there is reserved rooms populate then, update it into the database regarding the combo box value
//        If not, then just close the elment
        if(!this.room.getAvailability().equals("Reserved")) {
            this.room.updateRoomAvailabilityById(this.roomavailabilitycb.getValue());
        }
            UI ui = new UI();
            ui.closeUIElement(this.submitbt);
    }

//    Fetch data from the previous controller
    public void initData(Room room) {
//        Store the room instance into the room field
        this.room = room;
//        Check if the room is reserved and change elements accordingly to it
        if(this.room.getAvailability().equals("Reserved")) {
            this.roomiddl.setVisible(false);
            this.roomavailabilitycb.setVisible(false);
            this.roomidl.setVisible(false);
            this.roomavailabilityl.setVisible(false);
            this.errorl.setVisible(true);
            this.submitbt.setText("Go Back");
        } else {
            this.roomiddl.setText(Integer.toString(room.getRoom_id()));
            this.roomavailabilitycb.setValue(room.getAvailability());
            this.errorl.setVisible(false);
        }
    }

//    Set a new room
    public void setRoomDate(Room room) { this.room = room; }
}
