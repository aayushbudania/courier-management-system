
package courierservice;


import java.sql.*;
import java.util.*;
        
public class DeliveryBoy extends Employee{
    
    private int deliveryManID;
    private String name;
    private int vehicleID;
    private Double rating;
    private Long phoneNumber;
    
    public DeliveryBoy(){
        
    }
    //overload constructor
    public DeliveryBoy(Statement stmt, String customerID){
        this.getDetails(stmt,customerID);
    }
    
    public DeliveryBoy(Statement stmt){
        this.selectDeliveryBoy(stmt);
    }
    
    public void getDetails(Statement stmt,String customerID){
    
        System.out.println("DELIVERY BOY DETAILS:");
        
        try{
            
            String sql = "select d.* from deliveryBoy d"
                    + " inner join (select deliveryManID from shipment"
                    + " where customerID = '"+ customerID +"'"
                    + " order by pickupDate desc limit 1) s"//
                    + " on d.deliveryManID = s.deliveryManID;";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                                System.out.println("DELIVERYMAN ID:"+rs.getInt(1)+"\nNAME : "+rs.getString(2)+"\nVEHICLE ID: "+rs.getInt(3)+"\nRATING: "+rs.getDouble(4)+"\nPHONE NUMBER "+ rs.getLong(5));
                
            }
            
            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    public void selectDeliveryBoy(Statement stmt){
    
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your ID : ");
        int checkID = sc.nextByte();
        
        String sql = "select * from deliveryBoy where DeliveryManID = "+checkID+";";
        
        try{
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next()){
                this.deliveryManID = rs.getInt(1);
                this.name = rs.getString(2);
                this.vehicleID = rs.getInt(3);
                this.rating = rs.getDouble(4);
                this.phoneNumber = rs.getLong(5);
                System.out.println("Logging In...");
                this.deliveryBoyOptions(stmt);
            }
            else{
                System.out.println("Invalid ID , Please try again.");
                this.selectDeliveryBoy(stmt);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        
    }
    
    public void deliveryBoyOptions(Statement stmt){
        
        Scanner sc = new Scanner(System.in);
        
        while(true){
        System.out.println("\n**************************************");
        System.out.println("PRESS 1 : To set status of shipment.");
        System.out.println("PRESS 2 : To Show Shipments remaining to be fetched ");
        System.out.println("PRESS 3 : TO show your Details");
        System.out.println("PRESS 4 : To Log Out.");
        System.out.println("**************************************\n");
        
        System.out.println("Enter your choice : ");
        int check = sc.nextInt();
        

        switch(check){
            
            case 1 : this.setStatus(stmt);
                     break;
            
            case 2 : this.getRemainingShipment(stmt);
                     break;
                     
            case 3 : this.showDetails(stmt);
                     break;
                     
            case 4 : System.out.println("Logging out....");
                     break;
                     
            default : System.out.println("Invalid option , Try again.");
               
        }
        if(check==4) break;
       }
    }
    
    public void setStatus(Statement stmt){
    
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------------------SET STATUS----------------------------------\n");
    
        System.out.print("Enter Customer ID :");
        String checkID = sc.next();
        
        System.out.print("Enter Status [fetched/shipped/delivered] : ");
        String newStatus = sc.next();
        
        try{
            String sql = "update shipment set status = '"+newStatus+"'"
                    + " where deliveryManID = "+this.deliveryManID
                    + " and customerId = '"+checkID+"';";
            
            int check = stmt.executeUpdate(sql);
            
            if(check>0){
                System.out.println("Status Changed Successfully.");
            }
            else if(check==0) System.out.println("No Shipment From This Customer Is Available For You.");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------------------------------------\n");
    }
    
    public void getRemainingShipment(Statement stmt){
        
        System.out.println("-----------------------------REMAINING SHIPMENTS-------------------------------\n");
        
        try{
            String sql = "select * from shipment where deliveryManID = "+ this.deliveryManID+" and status = 'Not fetched yet'";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                System.out.println("SHIPMENT ID : "+rs.getInt(1)+" OFFICE ID : "+rs.getInt(2)+" COST : "+rs.getDouble(4)+" CUSTOMER ID : "+rs.getString(6)+" PICKUP DATE : "+rs.getDate(7)+" DELIVERY DATE : "+rs.getDate(8));
            
            }  
        }
        catch(SQLException e){
                    e.printStackTrace();
                    }
        System.out.println("-----------------------------------------------------------------------\n");
    }
    
    public void showDetails(Statement stmt){
        
        System.out.println("---------------------------DELIVERY BOY DETAILS-----------------------------\n");
        
        try{
            String sql = "select * from deliveryBoy where deliveryManID ="+this.deliveryManID;
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                System.out.println("DELIVERYMAN ID : "+rs.getInt(1));
                System.out.println("NAME           : "+rs.getString(2));
                System.out.println("VEHICLE ID     : "+rs.getInt(3));
                System.out.println("RATING         : "+rs.getDouble(4));
                System.out.println("PHONE NUMBER   : "+rs.getLong(5));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public int getDeliveryManID(){
        return deliveryManID;
    }
    
}


