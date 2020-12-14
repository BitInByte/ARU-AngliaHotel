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

    private Double roomPrice;

    @FXML
    private ComboBox roomTypecb;

    @FXML
    private TextField roompricetf;

    @FXML
    private Button submitbt;

    public void initialize() {

        submitbt.setDisable(true);

//        Get the room types
        ArrayList<String> roomTypes = Room.getRoomTypes();
//        Display it on the combobox to be displayed
        roomTypecb.getItems().addAll(roomTypes);

        roomTypecb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String roomType = roomTypecb.getSelectionModel().getSelectedItem().toString();
//                System.out.println(roomType);
                try {
                   roomPrice = Room.getRoomPriceByType(roomType);
                   roompricetf.setText(roomPrice.toString());
                    checkDisableButton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    Submit new price
    public void changeRoomPrice() {
//        Update price on database
        Room.updateRoomPrice(Double.parseDouble(roompricetf.getText()), roomTypecb.getSelectionModel().getSelectedItem().toString());

//        Close the element
        UI ui = new UI();
        ui.closeUIElement(submitbt);
    }

//    Check if the digited value is double to enable the button
    public void checkDisableButton() {

        if(roompricetf.getText().matches("\\d*\\.?\\d+")){
            System.out.println("true");
            submitbt.setDisable(false);
        } else {
            System.out.println(roompricetf);
            System.out.println("false");
            submitbt.setDisable(true);
        }
    }
}
