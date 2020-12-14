package HotelAnglia.controllers;

import HotelAnglia.models.Room;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;




public class ManageRoomView {

//    Declare fields
    private Room selectedRoom;

//    Declare UI elements
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

//    Perform some data at element initialization
    @FXML
    public void initialize() {
        //        Get the Room Types
        ArrayList<String> roomTypes = Room.getRoomTypes();
//        Add room types to the combobox
        this.roomTypecb.getItems().addAll(roomTypes);
//        roomTypecb.setValue(roomTypes.get(0));
    }

//    Fetch rooms method
    public void fetchRooms() throws SQLException {
//        Call list rooms method
        this.listRooms(roomTypecb.getValue().toString());
    }

//    Change values handler
    public void changeValuesHandler() throws IOException {
//        Create a new instance of Room and get the selection model from the rooms table view selection
        Room room = (Room) this.roomstv.getSelectionModel().getSelectedItem();
//        Store the selected room
        this.selectedRoom = room;
//        Open a new controller and inject data in it
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HotelAnglia/views/roomEditView.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

//        Access the controller and call a method
        RoomEditView controller = loader.getController();
        controller.initData((Room)roomstv.getSelectionModel().getSelectedItem());


        Stage stage = new Stage();
        stage.setTitle("Edit room");
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();

    }

//    Populate data in the rooms table view
    private void listRooms(String roomType) throws SQLException {
        ObservableList<Room> roomList = Room.getRoomObservableList(roomType);
        this.roomID.setCellValueFactory(new PropertyValueFactory<Room,Integer>("room_id"));
        this.type.setCellValueFactory(new PropertyValueFactory<Room,String>("type"));
        this.availability.setCellValueFactory(new PropertyValueFactory<Room,String>("availability"));
        this.price.setCellValueFactory(new PropertyValueFactory<Room,Double>("price"));
        this.roomstv.setItems(roomList);
    }

}
