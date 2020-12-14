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

    @FXML
    private ComboBox<String> revenueyearcb;

    @FXML
    private Label revenuesl;

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
                e.printStackTrace();
            }
        });
    }

    private void listRevenues() throws SQLException {
        System.out.println(this.revenueyearcb.getValue());
        ResultSet result = Payment.getRevenueYearByYear(this.revenueyearcb.getValue());
//        HashMap<String, String> revenue = new HashMap<>();
//        revenue.put("January", "0£");
//        revenue.put("February", "0£");
//        revenue.put("March", "0£");
//        revenue.put("April", "0£");
//        revenue.put("May", "0£");
//        revenue.put("June", "0£");
//        revenue.put("July", "0£");
//        revenue.put("August", "0£");
//        revenue.put("September", "0£");
//        revenue.put("October", "0£");
//        revenue.put("November", "0£");
//        revenue.put("December", "0£");
            String revenues = "";

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

//        int month = 1;
        while(result.next()) {
            System.out.println(result.getDouble("Month"));
            System.out.println(result.getDouble("Total Month Income"));

            if(result.getDouble("Month") == 1) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                janS = "Month: January = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                jan = true;
            } else if(!jan) {
                janS = "Month: January = Revenue: 0£\n";
                jan = true;
            }
            if(result.getDouble("Month") == 2) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                febS = "Month: February = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                feb = true;
            } else if(!feb) {
                febS = "Month: February = Revenue: 0£\n";
                feb = true;
            }
            if(result.getDouble("Month") == 3) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                marS = "Month: March = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                mar = true;
            } else if(!mar) {
                marS = "Month: March = Revenue: 0£\n";
                mar = true;
            }
            if(result.getDouble("Month") == 4) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                aprS = "Month: April = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                apr = true;
            } else if(!apr) {
                aprS = "Month: April = Revenue: 0£\n";
                apr = true;
            }
            if(result.getDouble("Month") == 5) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                mayS = "Month: May = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                may = true;
            } else if(!may) {
                mayS = "Month: May = Revenue: 0£\n";
                may = true;
            }
            if(result.getDouble("Month") == 6) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                junS = "Month: June = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                jun = true;
            } else if(!jun) {
                junS = "Month: June = Revenue: 0£\n";
                jun = true;
            }
            if(result.getDouble("Month") == 7) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                julS = "Month: July = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                jul = true;
            } else if(!jul) {
                julS = "Month: July = Revenue: 0£\n";
                jul = true;
            }
            if(result.getDouble("Month") == 8) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                augS = "Month: August = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                aug = true;
            } else if(!aug) {
                augS = "Month: August = Revenue: 0£\n";
                aug = true;
            }
            if(result.getDouble("Month") == 9) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                sepS = "Month: September = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                sep = true;
            } else if(!sep) {
                sepS = "Month: September = Revenue: 0£\n";
                sep = true;
            }
            if(result.getDouble("Month") == 10) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                outS = "Month: August = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                out = true;
            } else if(!out) {
                outS = "Month: August = Revenue: 0£\n";
                out = true;
            }
            if(result.getDouble("Month") == 11) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                novS = "Month: November = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                nov = true;
            } else if(!nov) {
                novS = "Month: November = Revenue: 0£\n";
                nov = true;
            }
            if(result.getDouble("Month") == 12) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                decS = "Month: December = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
                dec = true;
            } else if(!dec) {
                decS = "Month: December = Revenue: 0£\n";
                dec = true;
            }

//            month++;
        }
            revenues = janS + febS + marS + aprS + mayS + junS + julS + augS + sepS + outS + novS + decS;
            System.out.println(revenues);
            this.revenuesl.setText(revenues);
    }
}
