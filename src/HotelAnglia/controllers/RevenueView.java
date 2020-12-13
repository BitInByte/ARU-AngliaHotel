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

//        int month = 1;
        while(result.next()) {
            System.out.println(result.getDouble("Month"));
            System.out.println(result.getDouble("Total Month Income"));
//            revenue.put("January", "12312451");
//            switch (Double.toString(result.getDouble("Month"))) {
//                case "1":
//                    revenue.put("January", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "2":
//                    revenue.put("February", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "3":
//                    revenue.put("March", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "4":
//                    revenue.put("April", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "5":
//                    revenue.put("May", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "6":
//                    revenue.put("June", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "7":
//                    revenue.put("July", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "8":
//                    revenue.put("August", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "9":
//                    revenue.put("September", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "10":
//                    revenue.put("October", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "11":
//                    revenue.put("November", result.getDouble("Total Month Income") + "£");
//                    break;
//                case "12":
//                    revenue.put("December", result.getDouble("Total Month Income") + "£");
//                    break;
            String revenues = "";
            if(result.getDouble("Month") == 1) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: January = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: January = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 2) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: February = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: February = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 3) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: March = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: March = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 4) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: April = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: April = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 5) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: May = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: May = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 6) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: June = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: June = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 7) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: July = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: July = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 8) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: August = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: August = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 9) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: September = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: September = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 10) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: August = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: August = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 11) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: November = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: November = Revenue: 0£\n";
            }
            if(result.getDouble("Month") == 12) {
//                revenue.put("January", result.getDouble("Total Month Income") + "£");
                revenues += "Month: December = Revenue: "  + result.getDouble("Total Month Income") + "£\n";
            } else {
                revenues += "Month: December = Revenue: 0£\n";
            }

            System.out.println(revenues);
//            month++;
        }
    }
}
