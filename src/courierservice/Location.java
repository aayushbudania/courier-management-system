
package courierservice;

import java.sql.*;

abstract class Location {
    
    private int shipmentID;
    private String street;
    private String city;
    private String state;
    
    abstract void getAddress(Statement stmt,String customerID);
    //abstract void setAddress();
    
}
