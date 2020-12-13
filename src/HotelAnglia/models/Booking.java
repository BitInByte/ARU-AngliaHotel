package HotelAnglia.models;

import HotelAnglia.controllers.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Booking {

    private int bookingId;
    private LocalDate reservationDate;
    private LocalDate checkoutDate;
    private LocalDate bookingDate;
    private String status;
    private String roomType;
//    private int roomId;
//    private int customerId;
//    private int paymentId;
    private Room room;
    private Payment payment;
    private Customer customer;
    private ArrayList<Service> services;

    public Booking() {}

    public Booking(LocalDate reservationDate, Customer customer, Payment payment, String roomType) {
        this.reservationDate = reservationDate;
        this.bookingDate = LocalDate.now();
        this.status = "Under Confirmation";
        this.customer = customer;
        this.payment = payment;
//        this.room = room;
        this.roomType = roomType;
//        this.roomId = roomId;
//        this.customerId = customerId;
//        this.paymentId = paymentId;
    }

//    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Room room, Payment payment, Customer customer) {
    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Payment payment, Customer customer) {

        this.bookingId = bookingId;
        this.reservationDate = reservationDate;
        this.bookingDate = bookingDate;
        this.status = status;
//        this.room = room;
        this.payment = payment;
        this.customer = customer;
    }

        public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, String roomType, Room room, Payment payment, Customer customer) {
//    public Booking(int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Payment payment, Customer customer) {

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

    public void createNewBooking() {

        System.out.println("Room Type");
        System.out.println(this.roomType);

        String[] params = {this.reservationDate.toString(), this.status, this.bookingDate.toString(), this.roomType, Integer.toString(this.customer.getCustomerId()), Integer.toString(this.payment.getPaymentId())};


//        Create the query string
        String query = "INSERT INTO booking (reservation_date, status, booking_date, room_type, customer_id, payment_id) VALUES (?, ?, ?, ?, ?, ?);";

//        execute query returning the id
//        int paymentId = Connect.prepUpdateOld(query, params);
        int bookingId = Connect.prepUpdateBooking(query, params);

//        Update local id
        if(bookingId >= 0) {
            this.bookingId = bookingId;
        }
    }

    public ResultSet getAllBookings() {
//        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id INNER JOIN room as r ON b.room_id = r.room_id ORDER BY booking_id ASC;";
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id ORDER BY booking_id ASC;";

        System.out.println(query);
        ResultSet results = Connect.sqlExecute(query);
//        Connect.resultPrinter(results);
        return results;
    }

    public ResultSet getAllTodaysBookings() {
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id WHERE b.reservation_date = CURRENT_DATE AND NOT b.status = 'Checked-In' ORDER BY booking_id ASC;";
        System.out.println(query);
        ResultSet results = Connect.sqlExecute(query);

        return results;
    }

    public ResultSet getAllCheckedInBookings() {
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN room as r ON b.room_id = r.room_id INNER JOIN payment as p ON b.payment_id = p.payment_id WHERE b.status = 'Checked-In' ORDER BY booking_id ASC;";
        System.out.println(query);
        ResultSet results = Connect.sqlExecute(query);

        return results;
    }

    public void getBookingById(int bookingID) throws SQLException {
        String query = "SELECT * FROM booking as b INNER JOIN customer as c ON b.customer_id = c.customer_id INNER JOIN payment as p ON b.payment_id = p.payment_id WHERE b.booking_id = " + bookingID + ";";
        System.out.println(query);
        ResultSet result = Connect.sqlExecute(query);
//        Connect.resultPrinter(result);
        while (result.next()) {
            this.payment = new Payment(result.getInt("payment_id"), result.getDate("date"), result.getBoolean("ispaid"), result.getString("payment_method"), result.getDouble("total_price"));
            this.customer = new Customer(result.getInt("customer_id"), result.getString("full_name"), result.getString("email"));
//            room = new Room(results.getInt("room_id"), results.getString("availability"), results.getString("type"), results.getDouble("price"));
//            int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Room room, Payment payment, Customer customer
            this.bookingId = result.getInt("booking_id");
            this.reservationDate = result.getDate("reservation_date").toLocalDate();
            this.bookingDate = result.getDate("booking_date").toLocalDate();
            this.status = result.getString("status");
            this.roomType = result.getString("room_type");
//            booking = new Booking(result.getInt("booking_id"), result.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"),payment, customer);
        }
    }

    public void approveBooking() {
        String query = "UPDATE booking SET status = 'Approved' WHERE booking_id = " + this.bookingId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
    }

    public void updateReservationDateById() {
        String query = "UPDATE booking SET reservation_date = '" + this.reservationDate + "' WHERE booking_id = " + this.bookingId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
    }

    public void updateCheckOutDateById() {
        String query = "UPDATE booking SET checkout_date = '" + this.checkoutDate + "' WHERE booking_id = " + this.bookingId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
    }

    public void cancelReservationById() {
        String query = "UPDATE booking SET status = 'Canceled' WHERE booking_id = " + this.bookingId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
    }

    public void checkInById(Room room) {
        if(!this.status.equals("Checked-In")) {
            System.out.println(this.status);
            this.room = room;
//            room.updateRoomAvailability("In Use");
            this.room.occupyRoom();
            this.status = "Checked-In";
            String query = "UPDATE booking SET status = '" + this.status + "', room_id = " + this.room.getRoom_id() +" WHERE booking_id = " + this.bookingId + ";";
            Connect.sqlUpdate(query);
        }
    }

    public void checkOutById() {
        System.out.println("checking out");
    }

    public void closeBookingByID() {
        String query = "UPDATE booking SET status = 'Closed' WHERE booking_id = " +this.bookingId + ";";
        System.out.println(query);
        Connect.sqlUpdate(query);
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
//        Room room;
        ObservableList<Booking> bookingsList = FXCollections.observableArrayList();
//        To Loop through results again is should be at the start. Since it is looping now, it will stop at the end
//        A reset iteration should be performed to be able to loop through it again
        while (results.next()) {
            payment = new Payment(results.getInt("payment_id"), results.getDate("date"), results.getBoolean("ispaid"), results.getString("payment_method"), results.getDouble("total_price"));
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
//            room = new Room(results.getInt("room_id"), results.getString("availability"), results.getString("type"), results.getDouble("price"));
//            int bookingId, LocalDate reservationDate, LocalDate bookingDate, String status, Room room, Payment payment, Customer customer
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"),payment, customer);
            bookingsList.add(booking);
        }

        System.out.println("Booking List");
        System.out.println(bookingsList);
        return bookingsList;
    }

    public ObservableList<Booking> listAllTodaysBookings() throws SQLException {
        ResultSet results = this.getAllTodaysBookings();

        Booking booking;
        Customer customer;
        ObservableList<Booking> todaysBookingList = FXCollections.observableArrayList();
        while(results.next()) {
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"), customer);
            todaysBookingList.add(booking);
        }
        System.out.println("Todays Booking List");
        System.out.println(todaysBookingList);
        return todaysBookingList;
    }

    public ObservableList<Booking> listAllCheckedInBookings() throws SQLException {
        ResultSet results = this.getAllCheckedInBookings();

        Booking booking;
        Customer customer;
        Payment payment;
        Room room;
        ObservableList<Booking> checkedInBookingList = FXCollections.observableArrayList();
        while(results.next()) {
            System.out.println("DATE");
            System.out.println(results.getDate("date"));
            customer = new Customer(results.getInt("customer_id"), results.getString("full_name"), results.getString("email"));
            payment = new Payment(results.getInt("payment_id"), results.getDate("date"), results.getBoolean("ispaid"), results.getString("payment_method"), results.getDouble("total_price"));
            room = new Room(results.getInt("room_id"), results.getString("room_number"), results.getString("availability"), results.getString("type"), results.getDouble("price"));
            booking = new Booking(results.getInt("booking_id"), results.getDate("reservation_date").toLocalDate(), results.getDate("booking_date").toLocalDate(), results.getString("status"), results.getString("room_type"), room ,payment, customer);
            checkedInBookingList.add(booking);
        }
        System.out.println("Checked In Booking List");
        System.out.println(checkedInBookingList);
        return checkedInBookingList;
    }

    public int getBookingId() { return bookingId; }
    public LocalDate getReservationDate() { return reservationDate; }

    public LocalDate getCheckoutDate() { return checkoutDate; }

    public LocalDate getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
//    public int getRoomId() { return roomId; }
//    public int getCustomerId() { return customerId; }
//    public int getPaymentId() { return paymentId; }
    public Customer getCustomer() { return customer; }
    public Payment getPayment() { return payment; }
    public Room getRoom() { return room; }
    public String getRoomType() { return this.roomType; }
    public ArrayList<Service> getServices() { return services; }

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
    //    public void setRoomId(int roomId) { this.roomId = roomId; }
//    public void setCustomerId(int customerId) { this.customerId = customerId; }
//    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
}
