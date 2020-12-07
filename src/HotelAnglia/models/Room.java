package HotelAnglia.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import HotelAnglia.controllers.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room {

    private int room_id;
    private String availability;
    private String type;
    private double price;

    public Room() {

    }

    public Room(int room_id, String type, double price) {
        this.room_id = room_id;
        this.availability = "Available";
        this.type = type;
        this.price = price;
    }

    public Room(int room_id, String availability, String type, double price) {
        this.room_id = room_id;
        this.availability = availability;
        this.type = type;
        this.price = price;
    }

//    public void updateRoomAvailability(String availability) {
//        try {
//            String updateRoomQuery = "UPDATE room SET availability = '" + availability + "' WHERE room_id = " + this.room_id + ";";
//            Connect.sqlUpdate(updateRoomQuery);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void updateRoomAvailability(String availability) {
        try {
            String updateRoomQuery = "UPDATE room SET availability = '" + availability + "' WHERE room_id = " + this.room_id + ";";
            Connect.sqlUpdate(updateRoomQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reserveRoom() {
//        if(this.type.equals("Available")) {
        try {
            String query = "UPDATE room SET availability = 'Reserved' WHERE room_id = " + this.room_id + ";";
            Connect.sqlUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        } else {
//
//        }
    }

    public static ArrayList<String> getRoomTypes() {

        ArrayList<String> roomTypesArray = new ArrayList<>();

        try {
            String roomTypesQuery = "SELECT type FROM room WHERE availability = 'Available' GROUP BY type;";
            ResultSet roomTypes = Connect.sqlExecute(roomTypesQuery);


                while (roomTypes.next()) {
                    for (int i = 1; i <= roomTypes.getMetaData().getColumnCount(); i++) {
                        System.out.println(roomTypes.getString(i));
                        roomTypesArray.add(roomTypes.getString(i));
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roomTypesArray;
    }

    public static ObservableList<Room> getRoomObservableList(String roomType) throws SQLException {
        ObservableList<Room> roomsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM room WHERE type = '" + roomType + "' ORDER BY room_id ASC;";
        System.out.println(query);
        ResultSet results = Connect.sqlExecute(query);
        Room room;
        while(results.next()) {
//            System.out.println(results.getInt("room_id"));
            room = new Room(results.getInt("room_id"), results.getString("availability"), results.getString("type"), results.getDouble("price"));
            roomsList.add(room);
        }
        return roomsList;
    }

    public static Double getRoomPriceWhereType(String type) throws SQLException {
         Double price = null;

        System.out.println(type);
        String query = "SELECT price FROM room WHERE type = '" + type + "' GROUP BY price;";
        ResultSet result = Connect.sqlExecute(query);
//        Connect.resultPrinter(result);
//        System.out.println(result.getString("price"));
        while (result.next()) {
//            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
//                price += Double.parseDouble(result.getString(i));
//                System.out.println(result.getString(i));
//            }
            price = result.getDouble("price");
            System.out.println(result.getDouble("price"));
        }
        System.out.println(price);

        return price;
    }

    public static void updateRoomPrice(Double newPrice, String type) {
        String query = "UPDATE room SET price = '" + newPrice + "' WHERE type = '" + type + "';";
        Connect.sqlUpdate(query);
    }

    public static Room getAvailableRoom(String type) throws SQLException {
        System.out.println(type);
        Room room = new Room();
        String query = "SELECT * FROM room WHERE availability = 'Available' AND type = '" + type + "' ORDER BY room_id ASC LIMIT 1";
        System.out.println(query);
        ResultSet result = Connect.sqlExecute(query);
        while(result.next()) {
            room = new Room(result.getInt("room_id"), result.getString("availability"), result.getString("type"), result.getDouble("price"));
        }

        return room;
    }

    public int getRoom_id() { return this.room_id; }
    public String getType() { return this.type; }
    public String getAvailability() { return this.availability; }
    public double getPrice() { return this.price; }
}
