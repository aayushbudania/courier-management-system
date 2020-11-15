
package courierservice;

import java.sql.*;
import java.util.*;

public class Vehicle {
       
    private int vehicleId;
    private int registrationNo;
    private String manf_company;
    private double capacity;
    private float cost;
    private double distanceTravelled;
    
    public Vehicle(Statement stmt,String customerID){
        this.getDetails(stmt, customerID);
    }
    
    public void getDetails(Statement stmt, String customerID){
        
        System.out.println("VEHICLE DETAILS:");
       
        String sql = "select v.* from vehicle v"
                + " inner join (select d.vehicleID from"
                + " deliveryBoy d inner join (select deliveryManID"
                + " from shipment"
                + " where customerID = '"+customerID+"'"
                + " order by pickupDate desc limit 1)s"
                + " on d.deliveryManID = s.deliveryManID)tmp"
                + " on v.vehicleID = tmp.vehicleID;";
        
        try{
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                System.out.println("VEHICLE ID     : "+ rs.getInt(1));
                System.out.println("VEHICLE NAME   : "+ rs.getString(2));
                System.out.println("REGISTRATION NO: "+ rs.getString(3));
                System.out.println("COST           : "+ rs.getDouble(4));
                System.out.println("CAPACITY       : "+ rs.getDouble(5));
                System.out.println("DISTANCE       : "+ rs.getInt(6)+"\n");
            }
            
            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }
}
