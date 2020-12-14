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

//    Declare fields
        private  static String userName ;
        private  static String  passWord;
        private  static Connection db;
        private static String dbUrl  = "jdbc:postgresql://localhost:5432/HotelAnglia";

//        Method to create a database
        public static void createDatabase(String userName, String password) {
//            Create a string with the postgresql server path
            String postgresqlpath = "jdbc:postgresql://localhost:5432/";
            try {
//                Start a connection
                Connection db = DriverManager.getConnection(postgresqlpath, userName, password);
//                Create a db statement
                Statement sqlStat = db.createStatement();
//                Execute a create database statement
                sqlStat.executeUpdate("CREATE DATABASE 'HotelAnglia'");
            } catch (Exception e) {
//                Catch errors and log it to the console
                e.printStackTrace();
            }
        }

//        Create a connection between the software and the database
        public static boolean getConnected(String userName, String passWord){

            try {
//                Start connection
                db = DriverManager.getConnection(dbUrl, userName, passWord);
                System.out.println("Connection Established Successfully... ");
                return true;
            }catch (Exception e){
//                Catch errors and log it to the console
                e.printStackTrace();
                System.out.println("Ops....Connection Failed....");
                return false;
            }
        }

//        Update query on the database
        public  static void sqlUpdate(String statement){
            try{
//                Create a new statement
                Statement sqlStat = db.createStatement();
//                Execute the statement query
                sqlStat.executeUpdate(statement);
                System.out.println("SQL Statement Updated Successfully...");
            }catch (Exception e){
//                Catch errors and log it to the console
                System.out.println("SQL Error...");
                e.printStackTrace();
            }
        }

//        Execute query on the database
        public static ResultSet sqlExecute(String statement){
            try {
//                Create a new statement
                Statement sqlStat = db.createStatement();
//                Return a result set from the executed query
                ResultSet rs = sqlStat.executeQuery(statement);
                System.out.println("SQL Statement Executed Successfully...");
//                Return the result set
                return rs;
            }catch (Exception e){
//                Catch errors and log it to the console
                System.out.println("SQL Error...");
                e.printStackTrace();
            }
//            Return null if something wrong happens
            return null;
        }

//        Disconnect connection from the server
        public static void disconnect(){
            try {
//                Close connection to the server
                db.close();
                System.out.println("Database Disconnected Successfully...");
            }catch (Exception e){
//                Catch erros and log it to the console
                System.out.println("Database Disconnected Unsuccessfully...");
                e.printStackTrace();
            }
        }

//        Prepare Statement update query
        public static int prepUpdatePayment(String pStatement, String[] values){
//            Create a integer to store the returned id
            int returnedId = -1;
            try{
//                Create a new prepared statement to execute the update query
                PreparedStatement prepStat = db.prepareStatement(pStatement, Statement.RETURN_GENERATED_KEYS);
//                Change the dynamic values to the wanted values
                prepStat.setBoolean(1, Boolean.getBoolean(values[0]));
                prepStat.setString(2, values[1]);
                prepStat.setDouble(3, Double.parseDouble(values[2]));

//                Execute the query
                prepStat.executeUpdate();
                System.out.println("Entry Updated... ");

//                Store the id to be returned
                ResultSet id = prepStat.getGeneratedKeys();
//                Loop through the ids
                while (id.next()) {
//                    Store the returned it
                    returnedId = id.getInt(1);
                }
            }catch (Exception e){
//                Catch errors to be logged to the console
                System.out.println("SQL Error...");
                e.printStackTrace();
            }
//            Return the returned id
            return returnedId;
        }

//        Prepare update to Customer queries
    public static int prepUpdateCustomer(String pStatement, String[] values){
//            Create a new variable to store the returned id
        int returnedId = -1;
        try{
//            Create a new prepared statement
            PreparedStatement prepStat = db.prepareStatement(pStatement, Statement.RETURN_GENERATED_KEYS);
//            Convert dynamic content
            prepStat.setString(1,values[0]);
            prepStat.setString(2,values[1]);

//            Execute query
            prepStat.executeUpdate();
            System.out.println("Entry Updated... ");

//            Store returned is to a new reuslt ser
            ResultSet id = prepStat.getGeneratedKeys();
//            Loop through the results
            while (id.next()) {
//                Store the returned id into the variable
                returnedId = id.getInt(1);
            }
        }catch (Exception e){
//            Catch possible error to log it to the console
            System.out.println("SQL Error...");
            e.printStackTrace();
        }
//        Return the returned id
        return returnedId;
    }

//    Prepare update to booking queries
    public static int prepUpdateBooking(String pStatement, String[] values){
//            Create a new variable to store the returned id
        int returnedId = -1;
        try{
//            Create a new prepared statement
            PreparedStatement prepStat = db.prepareStatement(pStatement, Statement.RETURN_GENERATED_KEYS);
//            Convert dynamic content
            prepStat.setDate(1, Date.valueOf(values[0]));
            prepStat.setString(2,values[1]);
            prepStat.setDate(3, Date.valueOf(values[2]));
            prepStat.setString(4, values[3]);
            prepStat.setInt(5,Integer.parseInt(values[4]));
            prepStat.setInt(6,Integer.parseInt(values[5]));

//            Execute prepared statement query
            prepStat.executeUpdate();
            System.out.println("Entry Updated... ");

//            Store returned data to a new result set
            ResultSet id = prepStat.getGeneratedKeys();
//            Loop through the results
            while (id.next()) {
//                Store the returned id into the variable
                returnedId = id.getInt(1);
            }
        }catch (Exception e){
//            Catch some possible error to log it to the console
            System.out.println("SQL Error...");
            e.printStackTrace();
        }
//        Return returned id
        return returnedId;
    }

//    Prepare execute query
        public static ResultSet prepExecute(String pStatment, String value){
            try {
//                Create a new prepare statement
                PreparedStatement prepStat = db.prepareStatement(pStatment);
//                Convert dynami content
                prepStat.setString(1,value);
//                Execute query and store results
                ResultSet rs = prepStat.executeQuery();
                System.out.println("Query Executed...");
//                Return results
                return rs;
            }catch (Exception e){
//                Catch possible errors and log it to the console
                System.out.println("SQL Error...");
                e.printStackTrace();
            }
//            Return null if some error occur
            return null;
        }

//        Result printer method to print the result set
//    ATTENTION: Using this method will avoid the result set to be user again after. This happens because, when it loop
//    through the result set, at the end, it will not go back to the initial position and stays at the last position
        public static void resultPrinter(ResultSet result){
            try{
//                Loop throug the result set
                while (result.next()){
//                    Create a new row string variable
                    String row ="";
//                    Loop through result set metadata
                    for(int i=1; i<=result.getMetaData().getColumnCount(); i++){
//                        Add information to the row variable
                        row += result.getString(i) + ", ";
                    }
                }
            }catch (Exception e){
//                Catch possible errors
                System.out.println("Error In Printing");
                e.printStackTrace();
            }
        }
    }
