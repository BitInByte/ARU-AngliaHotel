package HotelAnglia.models;

import HotelAnglia.controllers.Connect;

import java.sql.ResultSet;

public class Manager {

//    Declare fields
    private int accountId;
    private String username;
    private String password;

//    Declare context static fields
    private static int storedAccountId;
    private static String storedUsername;

//    Constructores
    public Manager() {  }

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    Login SQL query
    public ResultSet login() {
        String query = "SELECT account_id, username FROM management_user WHERE username = '" + this.username + "' AND password = crypt('" + this.password + "', password);";
        ResultSet result = Connect.sqlExecute(query);
        return result;
    }

//    Change password SQL query
    public void changePassword() {
        String query = "UPDATE management_user SET password = crypt('" + this.password + "', password) WHERE account_id = " + this.accountId + ";";
        Connect.sqlUpdate(query);
    }


//    Getters
    public int getAccountId() { return accountId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

//    Setters
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

//    Context static getters
    public static int getStoredAccountId() { return storedAccountId; }
    public static String getStoredUsername() { return storedUsername; }

//    Context static setters
    public static void setStoredAccountId(int storedAccountId) { Manager.storedAccountId = storedAccountId; }
    public static void setStoredUsername(String storedUsername) { Manager.storedUsername = storedUsername; }
}
