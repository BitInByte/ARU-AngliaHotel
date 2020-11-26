package HotelAnglia.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class NewBookingView {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//    private Boolean roomTypeValidator;
//    private Boolean dateValidator;

    @FXML
    private Button submitbtn;

    @FXML
    private Button backbtn;

    @FXML
    private DatePicker datedp;

    @FXML
    private Label errorlb;

    @FXML
    public void initialize() {

//        Set the button to disable at the window initialize
        submitbtn.setDisable(true);

//        this.roomTypeValidator = false;
//        this.dateValidator = false;

//        Add an event listener to check if tha date picked is in the future
        datedp.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
//                System.out.println(datedp.getValue().toString());

//                Get the date now
                LocalDate now = LocalDate.now();
                try {
//                    Parse date picker date and the date now
                Date datePicker = dateFormat.parse(datedp.getValue().toString());
                Date dateToday = dateFormat.parse(now.toString());
                    System.out.println(datePicker);
                    System.out.println(dateToday);
//                    If the date is in the past, then prompt an error on the error label
                    if(dateToday.getTime() > datePicker.getTime()) {
                        System.out.println("Cannot perform this action!");
                        errorlb.setText("Invalid Date. Please choose a date in the future!");
                    } else {
//                        If the date is in the future, then clear the error and enable the submit button
                        System.out.println("Valid date");
                        errorlb.setText("");
                        submitbtn.setDisable(false);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }


            }
        });


    }

    public void submitHandler() {
        UI UI = new UI();
        UI.closeUIElement(submitbtn);
        UI.createUIElement("Application Form", "applicationFormView");
    }

//    Close page deleting the view
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(backbtn);
    }
}
