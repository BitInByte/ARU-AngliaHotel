package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerBill {

//    Create a new date formatter
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//    Declare fields
    private Booking paidBooking;

//    Declare UI elements
    @FXML
    private Label customernamel;

    @FXML
    private Label totalpricel;

    @FXML
    private Label servicesl;

    @FXML
    private Button closebtn;

//    Fetch data from the last window
    public void initData(Booking paidBooking) throws ParseException {
//        Create a new services string variable
        String services = "";
//        Store data retrieved from the last window
        this.paidBooking = paidBooking;
//        set text on the labels
        this.customernamel.setText(this.paidBooking.getCustomer().getFullName());
        this.totalpricel.setText(Double.toString(this.paidBooking.getPayment().getTotalPrice()) + "£");
//        Parse the date
        Date checkIn = dateFormat.parse(this.paidBooking.getReservationDate().toString());
        Date checkOut = dateFormat.parse(this.paidBooking.getCheckoutDate().toString());
//        Convert the day from timestamp into date
        long daysStayed = ((checkOut.getTime() - checkIn.getTime()) / (1000*60*60*24));
//        Get the days total price
        double price = daysStayed * this.paidBooking.getRoom().getPrice();
//        Add room price information
        services = this.paidBooking.getRoom().getType() + "(x" + daysStayed + ")...." + price + "£";
//        Check if there is any service
        if (!this.paidBooking.getServices().isEmpty()) {
//            If it is, then populate the price information to the bill message
            services += "\n";
//            Loop through all services
            for (Service service : this.paidBooking.getServices()) {
                services += service.getType() + "...." + service.getPrice() +"£\n";
            }
        }
//        Set the bill message to the services label
        this.servicesl.setText(services);
    }

//    Close page after close button push
    public void closePage() {
        UI UI = new UI();
        UI.closeUIElement(this.closebtn);
    }
}
