package HotelAnglia.controllers;

import HotelAnglia.models.Room;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageRoomView {

    private Room selectedRoom;

    @FXML
    private ComboBox roomTypecb;

    @FXML
    private TableColumn roomID;

    @FXML
    private TableColumn type;

    @FXML
    private TableColumn availability;

    @FXML
    private TableColumn price;

    @FXML
    private TableView roomstv;

    @FXML
    public void initialize() {
        //        Get the Room Types
        ArrayList<String> roomTypes = Room.getRoomTypes();

//        Add room types to the combobox
        roomTypecb.getItems().addAll(roomTypes);
//        roomTypecb.setValue(roomTypes.get(0));
    }


    public void fetchRooms() throws SQLException {
        System.out.println(roomTypecb.getValue());
        this.listRooms(roomTypecb.getValue().toString());
    }

    public void changeValuesHandler() {
        Room room = (Room) roomstv.getSelectionModel().getSelectedItem();
        System.out.println(room.getRoom_id());
        this.selectedRoom = room;

//        UI ui = new UI();
//        ui.createUIElement("Room " + this.selectedRoom.getRoom_id() + " Edit", "roomEditView");
//        System.out.println("Pushing new window: Room edit");
//        try {
////            System.out.println("Pushing new window: " + title);
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HotelAnglia/views/roomEditView.fxml"));
//            RoomEditView controller = new RoomEditView();
//            controller.setRoomDate(room);
//            fxmlLoader.setController(controller);
//            Parent pushingWindow = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setTitle("Edit room");
//            stage.setScene(new Scene(pushingWindow));
//            stage.setResizable(false);
//            stage.show();
//        } catch (Exception e) {
//            System.out.println(e);
//            e.printStackTrace();
//        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HotelAnglia/views/roomEditView.fxml"));
        try {
            Parent parent = loader.load();
            if(parent!=null) {
                RoomEditView controller = loader.getController();
                controller.setRoomDate(room);
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Room getRoom() { return this.selectedRoom; }

    private void listRooms(String roomType) throws SQLException {
        ObservableList<Room> roomList = Room.getRoomObservableList(roomType);
        roomID.setCellValueFactory(new PropertyValueFactory<Room,Integer>("room_id"));
        type.setCellValueFactory(new PropertyValueFactory<Room,String>("type"));
        availability.setCellValueFactory(new PropertyValueFactory<Room,String>("availability"));
        price.setCellValueFactory(new PropertyValueFactory<Room,Double>("price"));
        roomstv.setItems(roomList);
    }

}
