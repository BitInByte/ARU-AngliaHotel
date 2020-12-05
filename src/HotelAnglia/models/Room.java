package HotelAnglia.models;

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
        String query = "SELECT * FROM room WHERE type = '" + roomType + "';";
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

    public String getRoom_id() { return Integer.toString(this.room_id); }
    public String getType() { return this.type; }
    public String getAvailability() { return this.availability; }
    public double getPrice() { return this.price; }
}
