package HotelAnglia.controllers;

import HotelAnglia.models.Room;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ManageRoomPriceView {

//    Declare fields
    private Double roomPrice;

//    Declare UI elements
    @FXML
    private ComboBox roomTypecb;

    @FXML
    private TextField roompricetf;

    @FXML
    private Button submitbt;

//    Perform some actions at element initialization
    public void initialize() {

//        set the submit button disable
        this.submitbt.setDisable(true);

//        Get the room types
        ArrayList<String> roomTypes = Room.getRoomTypes();
//        Display it on the combobox to be displayed
        this.roomTypecb.getItems().addAll(roomTypes);

//        Add an event listener to the room type combo box
        this.roomTypecb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
//                Get the selected room type
                String roomType = roomTypecb.getSelectionModel().getSelectedItem().toString();
                try {
//                    Attribute the room price to the roomPrice field
                   roomPrice = Room.getRoomPriceByType(roomType);
//                   Displays the room price at the text field
                   roompricetf.setText(roomPrice.toString());
//                   Validate if the button can be enabled
                    checkDisableButton();
                } catch (Exception e) {
//                    Catch possible error and log it to the console
                    e.printStackTrace();
                }
            }
        });
    }

//    Submit new price
    public void changeRoomPrice() {
//        Update price on database
        Room.updateRoomPrice(Double.parseDouble(this.roompricetf.getText()), this.roomTypecb.getSelectionModel().getSelectedItem().toString());

//        Close the element
        UI ui = new UI();
        ui.closeUIElement(this.submitbt);
    }

//    Check if the digited value is double to enable the button
    public void checkDisableButton() {

//        Check if the room price text field value matches the regex expression
        if(this.roompricetf.getText().matches("\\d*\\.?\\d+")){
//            Enable the submit button
            this.submitbt.setDisable(false);
        } else {
//            Disable the submit button
            this.submitbt.setDisable(true);
        }
    }
}
