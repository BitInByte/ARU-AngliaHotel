package HotelAnglia.models;

import HotelAnglia.controllers.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Payment {

    private int paymentId;
    private Date paymentDate;
    private Boolean isPaid;
    private String paymentMethod;
    private double totalPrice;

    public Payment() {  }

    public Payment(String paymentMethod, double totalPrice) {
//        this.paymentDate = paymentDate;
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

    public void createNewPayment() {
//        HashMap<String, String> params = new HashMap();

//        TreeMap<String, String> params = new TreeMap();
        String[] params = {this.isPaid.toString(), this.paymentMethod, Double.toString(this.totalPrice)};


//        Create the query string
//        String query = "INSERT INTO payment (date, isPaid, payment_method, total_price) VALUES (?, ?, ?, ?);";
        String query = "INSERT INTO payment (isPaid, payment_method, total_price) VALUES (?, ?, ?);";


//        Prepare params
//        params.put("date", this.paymentDate.toString());
//        params.put("string", this.status);
//        params.put("string2", this.paymentMethod);
//        params.put("double", Double.toString(this.totalPrice));

//        execute query returning the id
//        int paymentId = Connect.prepUpdateOld(query, params);
        int paymentId = Connect.prepUpdatePayment(query, params);

//        Update local id
        if(paymentId >= 0) {
            this.paymentId = paymentId;
        }
    }

    public void updatePaymentTotalPriceById() {
        String query = "UPDATE payment SET total_price = " + this.totalPrice + " WHERE payment_id = " + this.paymentId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
    }

    public void updatePaymentIsPaid() {
        String query = "UPDATE payment SET isPaid = true WHERE payment_id = " + this.paymentId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
    }

    public void updatePaymentDate() {
        this.paymentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String query = "UPDATE payment SET date = '" + this.paymentDate + "' WHERE payment_id = " + this.paymentId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
    }

    public static ArrayList<String> getRevenueYears() throws SQLException {
        ArrayList<String> revenueYearsArray = new ArrayList<>();
        String query = "SELECT EXTRACT(YEAR FROM date) AS \"Revenue Years\" FROM payment WHERE date IS NOT NULL GROUP BY EXTRACT(YEAR FROM date);";
        ResultSet result = Connect.sqlExecute(query);

        while (result.next()) {
            revenueYearsArray.add(result.getString("Revenue Years"));
        }

        for(String year : revenueYearsArray) {
            System.out.println(year);
        }

        return revenueYearsArray;
    }

    public static ResultSet getRevenueYearByYear(String year) {
        String query = "SELECT EXTRACT(MONTH FROM date) AS \"Month\", SUM(total_price) AS \"Total Month Income\" FROM payment WHERE date IS NOT NULL AND EXTRACT(YEAR FROM date) = " + year + " GROUP BY EXTRACT(MONTH FROM date) ORDER BY \"Month\";";
        System.out.println(query);
        ResultSet result = Connect.sqlExecute(query);

//        Connect.resultPrinter(result);

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
//        System.out.println(this.paymentId);
        System.out.println(this.paymentDate);
        if(this.paymentDate != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(this.paymentDate);
//            return this.paymentDate.toString();
        } else {
            return "Not Paid";
        }
    }
    public String getPaymentMethod() { return paymentMethod; }
    public String getIsPaid() {
//        System.out.println("Is Paid");
//        System.out.println(this.isPaid);
        if(this.isPaid) {
            return "Paid";
        } else {
            return "Not Paid";
        }
    }


}
