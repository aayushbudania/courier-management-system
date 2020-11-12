
package courierservice;

import java.util.*;
import java.sql.*;


public class DeliveryLocation extends Location{
    
    public void getAddress(Statement stmt,String customerID){
    
        System.out.print("DELIVERY LOCATION : ");
        try{
            
            String sql = "select l.deliveryStreet,l.deliveryCity,l.deliveryState from location l"
                    + " inner join (select shipmentID from shipment where"
                    + " customerID = '"+customerID+"'"
                    + " order by pickupDate desc)s"
                    + " on l.shipmentID = s.shipmentID"
                    + " limit 1;";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                System.out.println(rs.getString(1)+" ,"+ rs.getString(2)+" ,"+ rs.getString(3)+"\n");
            }
           
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    
    }
    
}
