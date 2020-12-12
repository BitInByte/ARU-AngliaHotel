package HotelAnglia.controllers;

import HotelAnglia.models.Booking;
import HotelAnglia.models.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerBill {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Booking paidBooking;

    @FXML
    private Label customernamel;

    @FXML
    private Label totalpricel;

    @FXML
    private Label servicesl;

    public void initData(Booking paidBooking) throws ParseException {
        String services = "";
//        Store data retrieved from the last window
        this.paidBooking = paidBooking;
//        set text on the labels
        this.customernamel.setText(this.paidBooking.getCustomer().getFullName());
        this.totalpricel.setText(Double.toString(this.paidBooking.getPayment().getTotalPrice()));
        System.out.println("SERVICES");
        Date checkIn = dateFormat.parse(this.paidBooking.getReservationDate().toString());
        Date checkOut = dateFormat.parse(this.paidBooking.getCheckoutDate().toString());
        long daysStayed = ((checkOut.getTime() - checkIn.getTime()) / (1000*60*60*24));
        double price = daysStayed * this.paidBooking.getRoom().getPrice();
        services = this.paidBooking.getRoom().getType() + "(x" + daysStayed + ")...." + price + "£";
        if (!this.paidBooking.getServices().isEmpty()) {
            services += "\n";
            for (Service service : this.paidBooking.getServices()) {
                System.out.println(service.getType());
                services += service.getType() + "...." + service.getPrice() +"£\n";
            }
        }
        System.out.println(services);
//        if(services.isEmpty()) {
//            services = "No services";
//        }
        System.out.println(services);
        this.servicesl.setText(services);
    }


}
