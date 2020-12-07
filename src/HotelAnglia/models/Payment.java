package HotelAnglia.models;

import HotelAnglia.controllers.Connect;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

//    Setters
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setIsPaid(Boolean status) { this.isPaid = status; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

//    Getters
    public double getTotalPrice() { return totalPrice; }
    public int getPaymentId() { return paymentId; }
    public Date getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public Boolean getIsPaid() { return isPaid; }


}
