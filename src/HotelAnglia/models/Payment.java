package HotelAnglia.models;

import HotelAnglia.controllers.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Payment {

//    Declare fields
    private int paymentId;
    private Date paymentDate;
    private Boolean isPaid;
    private String paymentMethod;
    private double totalPrice;

//    Constructors
    public Payment() {  }

    public Payment(String paymentMethod, double totalPrice) {
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.isPaid = false;
    }

    public Payment(int paymentId, Date paymentDate, Boolean isPaid, String paymentMethod, Double totalPrice) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

//    Create new payment SQL query
    public void createNewPayment() {
//        Prepare parameters
        String[] params = {this.isPaid.toString(), this.paymentMethod, Double.toString(this.totalPrice)};


//        SQL string query
        String query = "INSERT INTO payment (isPaid, payment_method, total_price) VALUES (?, ?, ?);";

//        Execute query and retrieve new payment id
        int paymentId = Connect.prepUpdatePayment(query, params);

//        Update local id
        if(paymentId >= 0) {
            this.paymentId = paymentId;
        }
    }

//    Update total price by payment id SQL query
    public void updatePaymentTotalPriceById() {
        String query = "UPDATE payment SET total_price = " + this.totalPrice + " WHERE payment_id = " + this.paymentId + ";";
        Connect.sqlUpdate(query);
    }

//    Update payment is paid by payment id SQL query
    public void updatePaymentIsPaidById() {
        String query = "UPDATE payment SET isPaid = true WHERE payment_id = " + this.paymentId + ";";
        Connect.sqlUpdate(query);
    }

//    Update payment date by payment id SQL query
    public void updatePaymentDateById() {
        this.paymentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String query = "UPDATE payment SET date = '" + this.paymentDate + "' WHERE payment_id = " + this.paymentId + ";";
        Connect.sqlUpdate(query);
    }

//    Get revenue years SQL query
    public static ArrayList<String> getRevenueYears() throws SQLException {
        ArrayList<String> revenueYearsArray = new ArrayList<>();
        String query = "SELECT EXTRACT(YEAR FROM date) AS \"Revenue Years\" FROM payment WHERE date IS NOT NULL GROUP BY EXTRACT(YEAR FROM date);";
        ResultSet result = Connect.sqlExecute(query);

        while (result.next()) {
            revenueYearsArray.add(result.getString("Revenue Years"));
        }

        return revenueYearsArray;
    }

//    Get revenue year by year SQL query
    public static ResultSet getRevenueYearByYear(String year) {
        String query = "SELECT EXTRACT(MONTH FROM date) AS \"Month\", SUM(total_price) AS \"Total Month Income\" FROM payment WHERE date IS NOT NULL AND EXTRACT(YEAR FROM date) = " + year + " GROUP BY EXTRACT(MONTH FROM date) ORDER BY \"Month\";";
        ResultSet result = Connect.sqlExecute(query);

        return result;
    }

//    Setters
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setIsPaid(Boolean status) { this.isPaid = status; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

//    Getters
    public double getTotalPrice() { return totalPrice; }
    public int getPaymentId() { return paymentId; }
    public String getPaymentDate() {
        if(this.paymentDate != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(this.paymentDate);
        } else {
            return "Not Paid";
        }
    }
    public String getPaymentMethod() { return paymentMethod; }
    public String getIsPaid() {
        if(this.isPaid) {
            return "Paid";
        } else {
            return "Not Paid";
        }
    }
}
