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

    private Room room;

    @FXML
    private Label roomidel;

    @FXML
    private Label roomiddl;

    @FXML
    private Label roomavailabilityl;

    @FXML
    private ComboBox<String> roomavailabilitycb;

    @FXML
    private Button submitbt;


//    @FXML
//    public void initialize() {
//        System.out.println(room.getType());
//    }

//    public void initialize() throws IOException {
////        ManageRoomView manageRoomView = new ManageRoomView();
////        String roomId = Integer.toString(manageRoomView.getRoom().getRoom_id());
//
////        int roomId = manageRoomView.getRoom().getRoom_id();
////        System.out.println("Room id");
////        System.out.println(roomId);
////        System.out.println(this.room.getRoom_id());
////        roomtitlel.setText(roomId);
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HotelAnglia/views/manageRoomView.fxml"));
////        Parent root = loader.load();
////        ManageRoomView controller = loader.<ManageRoomView>getController();
////        System.out.println("ROOM: ");
////        System.out.println(this.room.getRoom_id());
//    }

//    public void initData(String data) {
//        this.roomtitlel.setText(data);
//        System.out.println(data);
//    }

    public void changeRoomAvailabilityHandler() {
        this.room.updateRoomAvailability(roomavailabilitycb.getValue());
        UI ui = new UI();
        ui.closeUIElement(submitbt);
//        System.out.println(roomavailabilitycb.getValue());
    }

    public void initData(Room room) {
        this.room = room;
        roomiddl.setText(room.getRoom_id());
        roomavailabilitycb.setValue(room.getAvailability());
    }

    public void setRoomDate(Room room) { this.room = room; }
}
