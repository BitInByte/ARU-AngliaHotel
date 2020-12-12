package HotelAnglia.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Connect {

        private  static String userName ;
        private  static String  passWord;
        private  static Connection db;
        private static String dbUrl  = "jdbc:postgresql://localhost:5432/HotelAnglia";


        public static void createDatabase(String userName, String password) {
            String postgresqlpath = "jdbc:postgresql://localhost:5432/";
            try {
                Connection db = DriverManager.getConnection(postgresqlpath, userName, password);
                Statement sqlStat = db.createStatement();
                sqlStat.executeUpdate("CREATE DATABASE 'HotelAnglia'");
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public static boolean getConnected(String userName, String passWord){

            try {
                db = DriverManager.getConnection(dbUrl, userName, passWord);
                System.out.println("Connection Established Successfully... ");
                return true;
            }catch (Exception e){
                System.out.println("Ops....Connection Failed....");
                return false;
            }
        }

        public  static void sqlUpdate(String statement){
            try{
                Statement sqlStat = db.createStatement();
                sqlStat.executeUpdate(statement);
                System.out.println("SQL Statement Updated Successfully...");
            }catch (Exception e){
                System.out.println("SQL Error...");
            }
        }

        public static ResultSet sqlExecute(String statement){
            try {
                Statement sqlStat = db.createStatement();
                ResultSet rs = sqlStat.executeQuery(statement);
                System.out.println("SQL Statement Executed Successfully...");
                return rs;

            }catch (Exception e){
                System.out.println("SQL Error...");
                e.printStackTrace();
            }
            return null;

        }

        public static void disconnect(){
            try {
                db.close();
                System.out.println("Database Disconnected Successfully...");
            }catch (Exception e){
                System.out.println("Database Disconnected Unsuccessfully...");
            }

        }

        public static int prepUpdatePayment(String pStatement, String[] values){
            int returnedId = -1;
            try{
                PreparedStatement prepStat = db.prepareStatement(pStatement, Statement.RETURN_GENERATED_KEYS);
//                prepStat.setDate(1, Date.valueOf(values[0]));
                prepStat.setBoolean(1, Boolean.getBoolean(values[0]));
                prepStat.setString(2, values[1]);
                prepStat.setDouble(3, Double.parseDouble(values[2]));

                prepStat.executeUpdate();
                System.out.println("Entry Updated... ");

                ResultSet id = prepStat.getGeneratedKeys();
                while (id.next()) {
                    System.out.println("ID:");
                    System.out.println(id.getInt(1));
                    returnedId = id.getInt(1);
                }

            }catch (Exception e){
                System.out.println("SQL Error...");
//                System.out.println(e);
                e.printStackTrace();
            }

            return returnedId;
        }

    public static int prepUpdateCustomer(String pStatement, String[] values){
        int returnedId = -1;
        try{
            PreparedStatement prepStat = db.prepareStatement(pStatement, Statement.RETURN_GENERATED_KEYS);
            prepStat.setString(1,values[0]);
            prepStat.setString(2,values[1]);

            prepStat.executeUpdate();
            System.out.println("Entry Updated... ");

            ResultSet id = prepStat.getGeneratedKeys();
            while (id.next()) {
                System.out.println("ID:");
                System.out.println(id.getInt(1));
                returnedId = id.getInt(1);
            }

        }catch (Exception e){
            System.out.println("SQL Error...");
//            System.out.println(e);
            e.printStackTrace();
        }

        return returnedId;
    }

    public static int prepUpdateBooking(String pStatement, String[] values){
        int returnedId = -1;
        try{
            PreparedStatement prepStat = db.prepareStatement(pStatement, Statement.RETURN_GENERATED_KEYS);
            prepStat.setDate(1, Date.valueOf(values[0]));
            prepStat.setString(2,values[1]);
            prepStat.setDate(3, Date.valueOf(values[2]));
            prepStat.setString(4, values[3]);
            prepStat.setInt(5,Integer.parseInt(values[4]));
            prepStat.setInt(6,Integer.parseInt(values[5]));


            prepStat.executeUpdate();
            System.out.println("Entry Updated... ");

            ResultSet id = prepStat.getGeneratedKeys();
            while (id.next()) {
                System.out.println("ID:");
                System.out.println(id.getInt(1));
                returnedId = id.getInt(1);
            }

        }catch (Exception e){
            System.out.println("SQL Error...");
//            System.out.println(e);
            e.printStackTrace();
        }

        return returnedId;
    }

//    Dynamic query execute returning the stored id
    public static int prepUpdate(String pStatement, HashMap<String, String> values) {
//            https://stackoverflow.com/questions/201887/primary-key-from-inserted-row-jdbc
            int pos = 1;
            int returnedId = -1;
            try {
                PreparedStatement prepStat = db.prepareStatement(pStatement, Statement.RETURN_GENERATED_KEYS);
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    System.out.println(pos);
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                    switch (entry.getKey()) {
                        case "string":
                        case "string2":
                            prepStat.setString(pos, entry.getValue());
                            pos++;
                            break;
                        case "int":
                            prepStat.setInt(pos, Integer.parseInt(entry.getValue()));
                            pos++;
                            break;
                        case "double":
                            System.out.println(Double.valueOf(entry.getValue()));
                            prepStat.setDouble(pos, Double.parseDouble(entry.getValue()));
                            pos++;
                            break;
                        case "date":
                            prepStat.setDate(pos, Date.valueOf(entry.getValue()));
                            pos++;
                            break;
                    }
                }
                prepStat.executeUpdate();
                System.out.println("Entry Updated... ");
                ResultSet id = prepStat.getGeneratedKeys();
                while (id.next()) {
                    System.out.println(id.getInt(1));
                    returnedId = id.getInt(1);
                }
//                return returnedId;
            } catch (Exception e) {
                System.out.println("SQL Error...");
                e.printStackTrace();
            }
            return returnedId;
    }

        public static ResultSet prepExecute(String pStatment, String value){
            try {
                PreparedStatement prepStat = db.prepareStatement(pStatment);
                prepStat.setString(1,value);
                ResultSet rs = prepStat.executeQuery();
                System.out.println("Query Executed...");
                return rs;
            }catch (Exception e){
                System.out.println("SQL Error...");
            }
            return null;
        }

        public static void resultPrinter(ResultSet result){
            try{
                while (result.next()){
                    String row ="";
                    for(int i=1; i<=result.getMetaData().getColumnCount(); i++){
                        row += result.getString(i) + ", ";
                    }
                    System.out.println(row);
                }
            }catch (Exception e){
                System.out.println("Error In Printing");
            }
        }



    }
