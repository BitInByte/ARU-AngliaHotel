package HotelAnglia.controllers;

import java.sql.*;

public class Connect {

        private  static String userName ;
        private  static String  passWord;
        private  static Connection db;
        private static String dbUrl  = "jdbc:postgresql://localhost:5432/HotelAnglia";



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
                System.out.println("SQL Statement Updated Successfully...");
                return rs;

            }catch (Exception e){
                System.out.println("SQL Error...");
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

        public static void prepupdate(String pStatement, String[] values){
            try{
                PreparedStatement prepStat = db.prepareStatement(pStatement);
                prepStat.setInt(1, Integer.parseInt(values[2]));
                prepStat.setString(2,values[1]);
                prepStat.setString(3,values[0]);

                prepStat.setDate(4,Date.valueOf(values[3]));
                prepStat.executeUpdate();
                System.out.println("Entry Updated... ");

            }catch (Exception e){
                System.out.println("SQL Error...");
                System.out.println(e);
            }
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
