package HotelAnglia.models;

import HotelAnglia.controllers.Connect;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {

    private int serviceId;
    private String type;
    private Double price;
    private int bookingId;

    public Service() {  }

    public Service (int serviceId, String type, Double price, int bookingId) {
        this.serviceId = serviceId;
        this.type = type;
        this.price = price;
        this.bookingId = bookingId;
    }

    public Service (int serviceId, String type, Double price) {
        this.serviceId = serviceId;
        this.type = type;
        this.price = price;
    }

    public void getServiceByType() throws SQLException {
        String query = "SELECT * FROM service WHERE type = '" + this.type + "';";
        ResultSet result = Connect.sqlExecute(query);
        while (result.next()) {
            this.serviceId = result.getInt("service_id");
//            this.type = result.getString("type");
            this.price = result.getDouble("price");
        }
    }

    public void setNewServiceByBookingId() {
//        Create query to create new service
        String query = "INSERT INTO service_list (service_id, booking_id) VALUES (" + this.serviceId + ", " + this.bookingId + ");";
//        Execute query
        Connect.sqlUpdate(query);
    }

    public static double getTotalPriceByBookingID(int bookingId) throws SQLException {
        double totalPrice = 0;
        String query = "SELECT SUM(price) AS \"Total Price\", booking_id FROM service AS s INNER JOIN service_list AS sl ON s.service_id = sl.service_id WHERE booking_id = " + bookingId + " GROUP BY booking_id;";
        ResultSet result = Connect.sqlExecute(query);
        while (result.next()) {
            totalPrice = result.getDouble("Total Price");
        }
        return totalPrice;
    }

    public static ArrayList<Service> getAllServicesByBookingId(int bookingId) throws SQLException {
        ArrayList<Service> allServices = new ArrayList<>();

        String query = "SELECT s.service_id, s.type, s.price, sl.booking_id FROM service_list AS sl INNER JOIN service AS s ON sl.service_id = s.service_id WHERE booking_id = " + bookingId + ";";
        ResultSet results = Connect.sqlExecute(query);

        while (results.next()) {
            Service newService = new Service(results.getInt("service_id"), results.getString("type"), results.getDouble("price"));
            allServices.add(newService);
        }

        return allServices;
    }

    public static ArrayList<String> getServiceTypes() throws SQLException {
//        Create new service array
        ArrayList<String> serviceTypesArray = new ArrayList<>();

//        Create query
        String serviceTypesQuery = "SELECT type FROM service GROUP BY type;";
//        Execute query
        ResultSet serviceTypes = Connect.sqlExecute(serviceTypesQuery);
//        Loop through results
        while (serviceTypes.next()) {
            for (int i = 1; i <= serviceTypes.getMetaData().getColumnCount(); i++) {
//                Add all types to the array
                serviceTypesArray.add(serviceTypes.getString(i));
            }
        }

        return serviceTypesArray;
    }

    public int getServiceId() { return serviceId; }
    public String getType() { return type; }
    public Double getPrice() { return price; }
    public int getBookingId() { return bookingId; }

    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public void setType(String type) { this.type = type; }
    public void setPrice(Double price) { this.price = price; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
}
