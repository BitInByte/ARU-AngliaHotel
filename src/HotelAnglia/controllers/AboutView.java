package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutView {

//    Declaring UI elements
    @FXML
    private Label aboutl;

    @FXML
    public void initialize() {
//        Set about message
        String aboutMessage = "This project was made in case of study for the module OOP for Anglia Ruskin University Cambridge.\n";
        aboutMessage += "Module lecturer: Mahdi Maktab Dar\n\n";
        aboutMessage += "Technologies used: \n";
        aboutMessage += "\t-Java\n";
        aboutMessage += "\t-JavaFX\n";
        aboutMessage += "\t-JDBC\n";
        aboutMessage += "\t-PostgreSQL\n";

//        Assign message to the label
        this.aboutl.setText(aboutMessage);


    }
}
