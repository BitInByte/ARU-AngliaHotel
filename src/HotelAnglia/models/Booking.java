package HotelAnglia.models;

import HotelAnglia.controllers.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private LocalDate reservationDate;
    private LocalDate bookingDate;
    private String status;
    private int roomId;
    private int customerId;
    private int paymentId;
    private Room room;
    private Payment payment;
    private Customer customer;

    public Booking() {}

    public Booking(LocalDate reservationDate, int roomId, int customerId, int paymentId) {
        this.reservationDate = reservationDate;
        this.bookingDate = LocalDate.now();
        this.status = "Under Confirmation";
        this.roomId = roomId;
        this.customerId = customerId;
        this.paymentId = paymentId;
    }

    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Room room, Payment payment, Customer customer) {
        this.bookingId = bookingId;
        this.reservationDate = reservationDate;
        this.bookingDate = bookingDate;
        this.status = status;
        this.room = room;
        this.payment = payment;
        this.customer = customer;
    }

    public void createNewBooking() {
        String[] params = {this.reservationDate.toString(), this.status, this.bookingDate.toString(), Integer.toString(this.roomId), Integer.toString(this.customerId), Integer.toString(this.paymentId)};


//        Create the query string
        String query = "INSERT INTO booking (reservation_date, status, booking_date, room_id, customer_id, payment_id) VALUES (?, ?, ?, ?, ?, ?);";

//        execute query returning the id
//        int paymentId = Connect.prepUpdateOld(query, params);
        int paymentId = Connect.prepUpdateBooking(query, params);

//        Update local id
        if(paymentId >= 0) {
            this.bookingId = paymentId;
        }
    }

    public ResultSet getAllBookings() {
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id INNER JOIN room as r ON b.room_id = r.room_id;";
        System.out.println(query);
        ResultSet results = Connect.sqlExecute(query);
//        Connect.resultPrinter(results);
        return results;
    }

    public ObservableList<Booking> listAllBookings() throws SQLException {
//        String query = "SELECT \tb.booking_id as Booking_ID,\n" +
//                "\t\tb.reservation_date as Booking_Reservation_Date,\n" +
//                "\t\tb.status as Booking_Status,\n" +
//                "\t\tb.booking_date as Booking_Date,\n" +
//                "\t\tc.full_name as Customer_Name,\n" +
//                "\t\tc.email as Customer_Email,\n" +
//                "\t\tp.date as Payment_Date,\n" +
//                "\t\tp.payment_method as Payment_Method,\n" +
//                "\t\tp.total_price as Booking_Price,\n" +
//                "\t\tr.type as Room_Type,\n" +
//                "\t\tr.availability as Room_Availability,\n" +
//                "\t\tr.price as Room_Price\t\t\n" +
//                "FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id INNER JOIN room as r ON b.room_id = r.room_id";
        ResultSet results = this.getAllBookings();
//        System.out.println("Results");
//        Connect.resultPrinter(results);

        Booking booking;
        Payment payment;
        Customer customer;
        Room room;
        ObservableList<Booking> bookingsList = FXCollections.observableArrayList();
//        To Loop through results again is should be at the start. Since it is looping now, it will stop at the end
//        A reset iteration should be performed to be able to loop through it again
        while (results.next()) {
            payment = new Payment(results.getInt("payment_id"), results.getDate("date"), results.getBoolean("ispaid"), results.getString("payment_method"), results.getDouble("total_price"));
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
            room = new Room(results.getInt("room_id"), results.getString("availability"), results.getString("type"), results.getDouble("price"));
//            int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Room room, Payment payment, Customer customer
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), room, payment, customer);
            bookingsList.add(booking);
        }

        System.out.println("Booking List");
        System.out.println(bookingsList);
        return bookingsList;
    }

    public int getBookingId() { return bookingId; }
    public LocalDate getReservationDate() { return reservationDate; }
    public LocalDate getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
    public int getRoomId() { return roomId; }
    public int getCustomerId() { return customerId; }
    public int getPaymentId() { return paymentId; }
    public Customer getCustomer() { return customer; }
    public Payment getPayment() { return payment; }
    public Room getRoom() { return room; }

    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    public void setStatus(String status) { this.status = status; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
}
