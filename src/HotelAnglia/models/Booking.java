package HotelAnglia.models;

import HotelAnglia.controllers.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Booking {

//    Declare fields
    private int bookingId;
    private LocalDate reservationDate;
    private LocalDate checkoutDate;
    private LocalDate bookingDate;
    private String status;
    private String roomType;
    private Room room;
    private Payment payment;
    private Customer customer;
    private ArrayList<Service> services;

//    Constructors
    public Booking() {}

    public Booking(LocalDate reservationDate, Customer customer, Payment payment, String roomType) {
        this.reservationDate = reservationDate;
        this.bookingDate = LocalDate.now();
        this.status = "Under Confirmation";
        this.customer = customer;
        this.payment = payment;
        this.roomType = roomType;
    }

    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Payment payment, Customer customer) {

        this.bookingId = bookingId;
        this.reservationDate = reservationDate;
        this.bookingDate = bookingDate;
        this.status = status;
        this.payment = payment;
        this.customer = customer;
    }

    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, String roomType, Room room, Payment payment, Customer customer) {
        this.bookingId = bookingId;
        this.reservationDate = reservationDate;
        this.bookingDate = bookingDate;
        this.status = status;
        this.roomType = roomType;
        this.room = room;
        this.payment = payment;
        this.customer = customer;
    }

    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, String roomType, Payment payment, Customer customer) {
        this.bookingId = bookingId;
        this.reservationDate = reservationDate;
        this.bookingDate = bookingDate;
        this.status = status;
        this.roomType = roomType;
        this.payment = payment;
        this.customer = customer;
    }

    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, String roomType, Customer customer) {
        this.bookingId = bookingId;
        this.reservationDate = reservationDate;
        this.bookingDate = bookingDate;
        this.status = status;
        this.roomType = roomType;
        this.customer = customer;
    }

//    Create new booking database query
    public void createNewBooking() {

//        Prepare parameters
        String[] params = {this.reservationDate.toString(), this.status, this.bookingDate.toString(), this.roomType, Integer.toString(this.customer.getCustomerId()), Integer.toString(this.payment.getPaymentId())};

//        Create the query string
        String query = "INSERT INTO booking (reservation_date, status, booking_date, room_type, customer_id, payment_id) VALUES (?, ?, ?, ?, ?, ?);";

//        execute query returning the id
        int bookingId = Connect.prepUpdateBooking(query, params);

//        Update local id
        if(bookingId >= 0) {
            this.bookingId = bookingId;
        }
    }

//    Get all bookings database query
    public ResultSet getAllBookings() {
//      SQL query string
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id ORDER BY booking_id ASC;";

//        Create a new result set results with the returned results
        ResultSet results = Connect.sqlExecute(query);
//        Return the results
        return results;
    }

//    Get all not deleted bookings SQL query
    public ResultSet getAllNotDeletedBookings() {
//        SQL query string
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id WHERE NOT b.status='Canceled' ORDER BY booking_id ASC;";
//        Create a new result set results with the returned results
        ResultSet results = Connect.sqlExecute(query);
//        Return results
        return results;
    }

//    Get all todays bookings SQL query
    public ResultSet getAllTodaysBookings() {
//        SQL query string
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id WHERE b.reservation_date = CURRENT_DATE AND NOT b.status = 'Checked-In' AND NOT b.status = 'Canceled' ORDER BY booking_id ASC;";
//        Create a new result set with the returned results
        ResultSet results = Connect.sqlExecute(query);
//        Return results
        return results;
    }

//    Get all checked in bookings SQL query
    public ResultSet getAllCheckedInBookings() {
//        SQL query string
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN room as r ON b.room_id = r.room_id INNER JOIN payment as p ON b.payment_id = p.payment_id WHERE b.status = 'Checked-In' ORDER BY booking_id ASC;";
//        Create a new result set with the returned results
        ResultSet results = Connect.sqlExecute(query);
//        Return results
        return results;
    }

//    Get booking by booking id SQL query
    public void getBookingById(int bookingID) throws SQLException {
//        SQL query string
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id WHERE b.booking_id = " + bookingID + ";";
//        Create a new result set with the returned results
        ResultSet result = Connect.sqlExecute(query);
//        Loop through the results
        while (result.next()) {
//            Create new instance of the returned data
            this.payment = new Payment(result.getInt("payment_id"), result.getDate("date"), result.getBoolean("ispaid"), result.getString("payment_method"), result.getDouble("total_price"));
            this.customer = new Customer(result.getInt("customer_id"), result.getString("full_name"), result.getString("email"));
            this.bookingId = result.getInt("booking_id");
            this.reservationDate = result.getDate("reservation_date").toLocalDate();
            this.bookingDate = result.getDate("booking_date").toLocalDate();
            this.status = result.getString("status");
            this.roomType = result.getString("room_type");
        }
    }
//   Update approve booking SQL query
    public void approveBooking() {
//        SQL query string
        String query = "UPDATE booking SET status = 'Approved' WHERE booking_id = " + this.bookingId + ";";
//        Execute query
        Connect.sqlUpdate(query);
    }

//    Update a reservation date by booking id SQL query
    public void updateReservationDateById() {
//        SQL query string
        String query = "UPDATE booking SET reservation_date = '" + this.reservationDate + "' WHERE booking_id = " + this.bookingId + ";";
//        Execute query
        Connect.sqlUpdate(query);
    }

//    Update checked out date by booking id
    public void updateCheckOutDateById() {
//        SQL query string
        String query = "UPDATE booking SET checkout_date = '" + this.checkoutDate + "' WHERE booking_id = " + this.bookingId + ";";
//        Execute query
        Connect.sqlUpdate(query);
    }

//    Cancel reservation by booking id
    public void cancelReservationById() {
//        SQL query string
        String query = "UPDATE booking SET status = 'Canceled' WHERE booking_id = " + this.bookingId + ";";
//        Execute query
        Connect.sqlUpdate(query);
    }

//    Update status by booking id
    public void checkInById(Room room) {
        if(!this.status.equals("Checked-In")) {
            this.room = room;
            this.room.occupyRoom();
            this.status = "Checked-In";
//            SQL query string
            String query = "UPDATE booking SET status = '" + this.status + "', room_id = " + this.room.getRoom_id() +" WHERE booking_id = " + this.bookingId + ";";
//            Execute query
            Connect.sqlUpdate(query);
        }
    }

//    Close booking by booking id
    public void closeBookingByID() {
//        SQL string query
        String query = "UPDATE booking SET status = 'Closed' WHERE booking_id = " +this.bookingId + ";";
//        Execute query
        Connect.sqlUpdate(query);
    }

//    List all bookings
    public ObservableList<Booking> listAllBookings() throws SQLException {
//        Get all bookings
        ResultSet results = this.getAllBookings();

//        Instantiate new classes
        Booking booking;
        Payment payment;
        Customer customer;
//        Create a new observable list
        ObservableList<Booking> bookingsList = FXCollections.observableArrayList();
//        To Loop through results again is should be at the start. Since it is looping now, it will stop at the end
//        A reset iteration should be performed to be able to loop through it again
        while (results.next()) {
//            Instantiate data into respective classes
            payment = new Payment(results.getInt("payment_id"), results.getDate("date"), results.getBoolean("ispaid"), results.getString("payment_method"), results.getDouble("total_price"));
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"),payment, customer);
//            Add the booking class to the observable list
            bookingsList.add(booking);
        }

//        Return observable list
        return bookingsList;
    }

//    List all not deleted bookings
    public ObservableList<Booking> listAllNotDeleteBookings() throws SQLException {
//        Create a new results set
        ResultSet results = this.getAllNotDeletedBookings();

//        Instantitate new classes
        Booking booking;
        Payment payment;
        Customer customer;
//        Create a new observable list
        ObservableList<Booking> bookingsList = FXCollections.observableArrayList();
//        To Loop through results again is should be at the start. Since it is looping now, it will stop at the end
//        A reset iteration should be performed to be able to loop through it again
        while (results.next()) {
            payment = new Payment(results.getInt("payment_id"), results.getDate("date"), results.getBoolean("ispaid"), results.getString("payment_method"), results.getDouble("total_price"));
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"),payment, customer);
//            Populate booking to the observable list
            bookingsList.add(booking);
        }

//        Return observable list
        return bookingsList;
    }


//    List all todays bookings
    public ObservableList<Booking> listAllTodaysBookings() throws SQLException {
//        Grt all todays bookings
        ResultSet results = this.getAllTodaysBookings();

//        Isntantiate classes
        Booking booking;
        Customer customer;
//        Create a new observable list
        ObservableList<Booking> todaysBookingList = FXCollections.observableArrayList();
//        Loop through results
        while(results.next()) {
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"), customer);
//            Populate bookings into the observable list
            todaysBookingList.add(booking);
        }
//        Return observable list
        return todaysBookingList;
    }


//    List all checeked in bookings
    public ObservableList<Booking> listAllCheckedInBookings() throws SQLException {
//        Get all checked in bookings
        ResultSet results = this.getAllCheckedInBookings();

//        Instantitate classes
        Booking booking;
        Customer customer;
        Payment payment;
        Room room;
//        Create a new observable list
        ObservableList<Booking> checkedInBookingList = FXCollections.observableArrayList();
//        Loop through results
        while(results.next()) {
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
            payment = new Payment(results.getInt("payment_id"), results.getDate("date"), results.getBoolean("ispaid"), results.getString("payment_method"), results.getDouble("total_price"));
            room = new Room(results.getInt("room_id"), results.getString("room_number"), results.getString("availability"), results.getString("type"), results.getDouble("price"));
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"), room ,payment, customer);
//            Populate booking into the observable list
            checkedInBookingList.add(booking);
        }
//        Return observable list
        return checkedInBookingList;
    }

//    Getters
    public int getBookingId() { return bookingId; }
    public LocalDate getReservationDate() { return reservationDate; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
    public Customer getCustomer() { return customer; }
    public Payment getPayment() { return payment; }
    public Room getRoom() { return room; }
    public String getRoomType() { return this.roomType; }
    public ArrayList<Service> getServices() { return services; }

//    Setters
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    public void setCheckoutDate(LocalDate checkoutDate) { this.checkoutDate = checkoutDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    public void setStatus(String status) { this.status = status; }
    public void setRoom(Room room) { this.room = room; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setServices(ArrayList<Service> service) { this.services = service; }
}
