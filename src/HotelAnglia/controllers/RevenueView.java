package HotelAnglia.controllers;

import HotelAnglia.models.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RevenueView {

//    Declare UI elements
    @FXML
    private ComboBox<String> revenueyearcb;

    @FXML
    private Label revenuesl;

//    Perform some actions at elements initialization
    @FXML
    public void initialize() throws SQLException {
//        Get revenue years
        ArrayList<String> revenueYearsArray = Payment.getRevenueYears();
//        Add revenue years to the combo box
        this.revenueyearcb.getItems().addAll(revenueYearsArray);

//        Add an event listener to the combo box selection
        this.revenueyearcb.setOnAction(actionEvent -> {
            try {
                this.listRevenues();
            } catch (Exception e) {
//                Catch possible errors and log it into the console
                e.printStackTrace();
            }
        });
    }

//    List revenues method
    private void listRevenues() throws SQLException {
//        Get a revenue from the server by year
        ResultSet result = Payment.getRevenueYearByYear(this.revenueyearcb.getValue());
//        Create a revenue string
        String revenues = "";

//        Declare the aux boolean month variable
        boolean jan = false;
        boolean feb = false;
        boolean mar = false;
        boolean apr = false;
        boolean may = false;
        boolean jun = false;
        boolean jul = false;
        boolean aug = false;
        boolean sep = false;
        boolean out = false;
        boolean nov = false;
        boolean dec = false;

//        Declare the aux String message to each month variable
        String janS = "";
        String febS = "";
        String marS = "";
        String aprS = "";
        String mayS = "";
        String junS = "";
        String julS = "";
        String augS = "";
        String sepS = "";
        String outS = "";
        String novS = "";
        String decS = "";

//        Loop through the server results
        while(result.next()) {

//            Algorithm to populate price when there is a price in that month or 0£ when there is no price in that month
            if(result.getDouble("Month") == 1) {
                janS = "Month: January = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                jan = true;
            } else if(!jan) {
                janS = "Month: January = Revenue: 0£\n";
                jan = true;
            }
            if(result.getDouble("Month") == 2) {
                febS = "Month: February = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                feb = true;
            } else if(!feb) {
                febS = "Month: February = Revenue: 0£\n";
                feb = true;
            }
            if(result.getDouble("Month") == 3) {
                marS = "Month: March = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                mar = true;
            } else if(!mar) {
                marS = "Month: March = Revenue: 0£\n";
                mar = true;
            }
            if(result.getDouble("Month") == 4) {
                aprS = "Month: April = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                apr = true;
            } else if(!apr) {
                aprS = "Month: April = Revenue: 0£\n";
                apr = true;
            }
            if(result.getDouble("Month") == 5) {
                mayS = "Month: May = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                may = true;
            } else if(!may) {
                mayS = "Month: May = Revenue: 0£\n";
                may = true;
            }
            if(result.getDouble("Month") == 6) {
                junS = "Month: June = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                jun = true;
            } else if(!jun) {
                junS = "Month: June = Revenue: 0£\n";
                jun = true;
            }
            if(result.getDouble("Month") == 7) {
                julS = "Month: July = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                jul = true;
            } else if(!jul) {
                julS = "Month: July = Revenue: 0£\n";
                jul = true;
            }
            if(result.getDouble("Month") == 8) {
                augS = "Month: August = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                aug = true;
            } else if(!aug) {
                augS = "Month: August = Revenue: 0£\n";
                aug = true;
            }
            if(result.getDouble("Month") == 9) {
                sepS = "Month: September = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                sep = true;
            } else if(!sep) {
                sepS = "Month: September = Revenue: 0£\n";
                sep = true;
            }
            if(result.getDouble("Month") == 10) {
                outS = "Month: August = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                out = true;
            } else if(!out) {
                outS = "Month: August = Revenue: 0£\n";
                out = true;
            }
            if(result.getDouble("Month") == 11) {
                novS = "Month: November = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                nov = true;
            } else if(!nov) {
                novS = "Month: November = Revenue: 0£\n";
                nov = true;
            }
            if(result.getDouble("Month") == 12) {
                decS = "Month: December = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                dec = true;
            } else if(!dec) {
                decS = "Month: December = Revenue: 0£\n";
                dec = true;
            }
        }
//        Combine all message together
            revenues = janS + febS + marS + aprS + mayS + junS + julS + augS + sepS + outS + novS + decS;
//        Populate the revenues into the revenues label
            this.revenuesl.setText(revenues);
    }
}
