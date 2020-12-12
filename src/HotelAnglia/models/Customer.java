package HotelAnglia.models;

import HotelAnglia.controllers.Connect;

public class Customer {

    private int customerId;
    private String fullName;
    private String email;

    public Customer() {  }

    public Customer(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Customer(int customerId, String fullName, String email) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
    }

    public void createNewCustomer() {
//        HashMap<String, String> params = new HashMap();

//        TreeMap<String, String> params = new TreeMap();
        String[] params = {this.fullName, this.email};


//        Create the query string
        String query = "INSERT INTO customer (full_name, email) VALUES (?, ?);";

        int customerId = Connect.prepUpdateCustomer(query, params);

//        Update local id
        if(customerId >= 0) {
            this.customerId = customerId;
        }
    }

    public void updateCustomerName() {
        String query = "UPDATE customer SET full_name = '" + this.fullName + "' WHERE customer_id = " + this.customerId + ";";
        Connect.sqlUpdate(query);
    }

    public void updateCustomerEmail() {
        String query = "UPDATE customer SET email = '" + this.email + "' WHERE customer_id = " + this.customerId + ";";
        Connect.sqlUpdate(query);
    }

    public int getCustomerId() { return customerId; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }

    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setEmail(String email) { this.email = email; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}
