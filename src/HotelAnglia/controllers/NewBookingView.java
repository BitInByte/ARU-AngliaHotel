package HotelAnglia.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import HotelAnglia.models.Room;
import javafx.stage.Stage;

public class NewBookingView {

//    Create a new date formatter
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//    Declare UI elements
    @FXML
    private Button submitbtn;

    @FXML
    private Button backbtn;

    @FXML
    private DatePicker datedp;

    @FXML
    private Label errorlb;

    @FXML
    private ComboBox<String> roomTypecb;

//    Perform actions at elements initialization
    @FXML
    public void initialize() {

//        Set the button to disable at the window initialize
        this.submitbtn.setDisable(true);


//        Add an event listener to check if tha date picked is in the future
        this.datedp.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {

//                Get the date now
                LocalDate now = LocalDate.now();
                try {
//                    Parse date picker date and the date now
                Date datePicker = dateFormat.parse(datedp.getValue().toString());
                Date dateToday = dateFormat.parse(now.toString());
//                    If the date is in the past, then prompt an error on the error label
                    if(dateToday.getTime() > datePicker.getTime()) {
                        errorlb.setText("Invalid Date. Please choose a date in the future!");
                    } else {
//                        If the date is in the future, then clear the error and enable the submit button
                        errorlb.setText("");
                        submitbtn.setDisable(false);
                    }
                } catch (Exception e) {
//                    Catch possible errors and log it to the console
                    e.printStackTrace();
                }


            }
        });

//        Get the Room Types
        ArrayList<String> roomTypes = Room.getRoomTypes();
//        Add room types to the combobox
        this.roomTypecb.getItems().addAll(roomTypes);
        this.roomTypecb.setValue(roomTypes.get(0));
    }

//    Submit handler method to be pushed after a submit button push
    public void submitHandler() throws IOException {

//        Load new window and controller
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HotelAnglia/views/applicationFormView.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

//        Push new data to the next controller
        ApplicationFormView controller = loader.getController();
        controller.initData(this.datedp.getValue(), this.roomTypecb.getSelectionModel().getSelectedItem());

//        Open new window
        Stage stage = new Stage();
        stage.setTitle("Room Application Form");
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();

//        Close current window
        UI UI = new UI();
        UI.closeUIElement(this.submitbtn);
    }

//    Close page deleting the view
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.backbtn);
    }
}
