/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

import java.sql.*;
import java.util.*;


public class Parcel {
    
    private int parcelId;
    private double weight;
    private double volume;
    private String type;
    
    public Parcel(){
        
    }
    
    public Parcel(Statement stmt,String customerID){
        this.getDetails(stmt,customerID);
    }
    
    public void getDetails(Statement stmt,String customerID){
        
        System.out.println("PARCEL DETAILS:");
        try{
            
            String sql = "select p.weight,p.dimension,p.type"
                    + " from parcel p"
                    + " inner join (select shipmentID from shipment"
                    + " where customerID = '"+ customerID +"'"
                    + " order by pickupDate desc) s"
                    + " on p.parcelID = s.shipmentID"
                    + " limit 1 ;";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                System.out.println("WEIGHT:"+rs.getDouble(1)+" VOLUME:"+rs.getDouble(2)+" TYPE:"+rs.getString(3));
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
}
