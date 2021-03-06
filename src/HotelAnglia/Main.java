package HotelAnglia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import HotelAnglia.controllers.Connect;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Load main window
        Parent root = FXMLLoader.load(getClass().getResource("views/mainWindow.fxml"));
        primaryStage.setTitle("ARU Anglia Hotel");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

//        Create database
        this.createDB();

//        Connect to database
        if(!Connect.getConnected("postgres", "admin")){
            System.out.println("Wrong Password...");
            System.exit(0);

        } else {
            System.out.println("DataBase Connected Successfully...");
        }

//        Call table creation method
        this.createTables();
    }


    public static void main(String[] args) {
        launch(args);
    }

//    Create database
    private void createDB() {
       Connect.createDatabase("postgres", "admin");
    }

    private void createTables() {

//        Enable password encryptation
        String cryptoEnable = "CREATE EXTENSION pgcrypto;";
        Connect.sqlUpdate(cryptoEnable);
//        Create tables
        String accountTable = "CREATE TABLE management_user (account_id SERIAL PRIMARY KEY, username VARCHAR(30) NOT NULL, password TEXT NOT NULL, CONSTRAINT username_unique UNIQUE(username));";
        Connect.sqlUpdate(accountTable);
        String customerTable = "CREATE TABLE customer (customer_id SERIAL, full_name VARCHAR(80) NOT NULL, email VARCHAR(80) NOT NULL, PRIMARY KEY (customer_id));";
        Connect.sqlUpdate(customerTable);
        String roomTable = "CREATE TABLE room (room_id SERIAL PRIMARY KEY, room_number VARCHAR(5) NOT NULL, type VARCHAR(30) NOT NULL, availability VARCHAR(30) NOT NULL, price NUMERIC NOT NULL);";
        Connect.sqlUpdate(roomTable);
        String serviceTable = "CREATE TABLE service (service_id SERIAL PRIMARY KEY, type VARCHAR(30) NOT NULL, price DOUBLE PRECISION NOT NULL);";
        Connect.sqlUpdate(serviceTable);
        String paymentTable = "CREATE TABLE payment (payment_id SERIAL PRIMARY KEY, date DATE, isPaid BOOLEAN NOT NULL, payment_method VARCHAR(30) NOT NULL, total_price DOUBLE PRECISION NOT NULL);";
        Connect.sqlUpdate(paymentTable);
        String bookingTable = "CREATE TABLE booking (booking_id SERIAL PRIMARY KEY, reservation_date DATE NOT NULL, checkout_date DATE, status VARCHAR(30) NOT NULL, room_type VARCHAR(30) NOT NULL, booking_date DATE NOT NULL, room_id INTEGER, customer_id INTEGER NOT NULL, payment_id INTEGER NOT NULL, FOREIGN KEY (room_id) REFERENCES room(room_id) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE RESTRICT ON DELETE RESTRICT, FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON UPDATE CASCADE ON DELETE CASCADE);";
        Connect.sqlUpdate(bookingTable);
        String serviceList = "CREATE TABLE service_list (service_list_id SERIAL PRIMARY KEY, service_id INTEGER NOT NULL, booking_id INTEGER NOT NULL, FOREIGN KEY (service_id) REFERENCES service(service_id) ON UPDATE RESTRICT ON DELETE RESTRICT, FOREIGN KEY (booking_id) REFERENCES booking(booking_id) ON UPDATE CASCADE ON DELETE CASCADE);";
        Connect.sqlUpdate(serviceList);



//        Add management user
        String adminUser = "INSERT INTO management_user (account_id, username, password) VALUES (1, 'admin', crypt('admin', gen_salt('bf')));";
        Connect.sqlUpdate(adminUser);

//        Adding rooms data
        String roomDataQuery;

        int singleRoomQuantity = 20;
        int doubleRoomQuantity = 30 + singleRoomQuantity;
        int executiveRoomQuantity = 10 + doubleRoomQuantity;
        int presidentialRoomQuantity = 5 + executiveRoomQuantity;

//        Dummy data injection
        int pos = 1;

        for(int i = 0; i < singleRoomQuantity; i ++) {
            roomDataQuery = "INSERT INTO room (room_id, room_number, type, availability, price) VALUES (" + i + ", 'S" + pos + "', 'Single', 'Available', 30);";
            pos++;
            Connect.sqlUpdate(roomDataQuery);
        }

        pos = 0;
        for(int i = singleRoomQuantity; i < doubleRoomQuantity; i ++) {
            roomDataQuery = "INSERT INTO room (room_id, room_number, type, availability, price) VALUES (" + i + ", 'D" + pos + "', 'Double', 'Available', 60);";
            pos++;
            Connect.sqlUpdate(roomDataQuery);
        }

        pos = 0;
        for(int i = doubleRoomQuantity; i < executiveRoomQuantity; i ++) {
            roomDataQuery = "INSERT INTO room (room_id, room_number, type, availability, price) VALUES (" + i + ", 'E" + pos + "', 'Executive', 'Available', 100);";
            pos++;
            Connect.sqlUpdate(roomDataQuery);
        }

        pos = 0;
        for(int i = executiveRoomQuantity; i < presidentialRoomQuantity; i ++) {
            roomDataQuery = "INSERT INTO room (room_id, room_number, type, availability, price) VALUES (" + i + ", 'P" + pos + "', 'Presidential', 'Available', 150);";
            pos++;
            Connect.sqlUpdate(roomDataQuery);
        }

        String foodService = "INSERT INTO service (service_id, type, price) VALUES (1, 'Food', 14.99)";
        Connect.sqlUpdate(foodService);

        String beverageService = "INSERT INTO service (service_id, type, price) VALUES (2, 'Beverage', 7.50)";
        Connect.sqlUpdate(beverageService);

        String spaService = "INSERT INTO service (service_id, type, price) VALUES (3, 'SPA', 49.99)";
        Connect.sqlUpdate(spaService);

        String taxiService = "INSERT INTO service (service_id, type, price) VALUES (4, 'Taxi', 100)";
        Connect.sqlUpdate(taxiService);

        String customService = "INSERT INTO service (service_id, type, price) VALUES (5, 'Custom', 75)";
        Connect.sqlUpdate(customService);

    }
}
