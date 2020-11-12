package courierservice;


import java.sql.*;
import java.util.*;


public class Shipment {
    
    private int shipmentID;
    private int officeID;
    private int deliveryManID;
    private double cost;
    private String status;
    

    public void placeShipment(Statement stmt,String customerID){
        
        System.out.println("------------------------NEW SHIPMENT-----------------------\n");
        
        System.out.println("Genrating shipment...");
        Scanner sc = new Scanner(System.in);
        
        int tmp=0;
        
        /*try{
            
            ResultSet rs = stmt.executeQuery("select count(shipmentID) from shipment;");
            
            while(rs.next()){
                tmp= rs.getInt(1)+1;
            }
            rs.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }*/
        
        this.shipmentID = tmp;
        this.officeID = tmp % 4;
        this.deliveryManID = tmp % 8;
        
        Random rand =new Random();
        int upperbound = 600;
        
        shipmentID = rand.nextInt(upperbound);
        
        officeID = shipmentID % 4;
        
        deliveryManID = shipmentID %8;
        
        System.out.print("Enter pickup date (YYYY-MM-DD): ");
        String pickupDate = sc.next();
        
        System.out.print("Enter expected delivery date (YYYY-MM-DD): ");
        String deliveryDate = sc.next();
        
        /*System.out.print("Enter your customerID : ");
        String tmpID = sc.next();*/
        

        
        //--------------------------------------------------------------//
        
        System.out.println("\nENTER PARCEL DETAILS : ");
        System.out.print("Enter weight of parcel (in Kg): ");
        double weight=sc.nextDouble();
        
        System.out.print("Enter size of parcel (in cubic-meter): ");
        double dimension = sc.nextDouble();
        
        System.out.print("Enter type of parcel : ");
        String type = sc.next();
        
        cost = (weight * dimension)/2;
        
        status = "Not fetched yet";
        
        String sql = "insert into shipment values ("+ shipmentID +","+ officeID +","+ deliveryManID+","+ cost +",'"+ status +"','"+ customerID +"','"+ pickupDate +"','"+deliveryDate+"');";
        try{
        stmt.execute(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        
        sql = "insert into parcel values ("+ shipmentID + ","+ weight +","+ dimension +",'"+ type+"');";
        try{
            stmt.execute(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        //-------------------------------------------------------------//
        
        System.out.println("ENTER LOCATION DETAILS : \n");
        sc.nextLine();
        System.out.println("Enter pickup street :");
        String pickupStreet = sc.nextLine();
        
        System.out.println("Enter pickup city : ");
        String pickupCity = sc.next();
        
        System.out.println("Enter pickup State : ");
        String pickupState = sc.next();
        
        sc.nextLine();
        System.out.println("\nEnter delivery street : ");
        String deliveryStreet = sc.nextLine();
        
        System.out.println("Enter delivery city : ");
        String deliveryCity = sc.next();
        
        System.out.println("Enter delivery State : ");
        String deliveryState = sc.next();
        
        sql = "insert into location values ("+ shipmentID +",'"+ pickupStreet +"','"+ pickupCity +"','"+ pickupState +"','"+ deliveryStreet +"','"+ deliveryCity +"','"+ deliveryState +"');";
        
        try{
            stmt.execute(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        System.out.println("Your Shipment is Placed . Go to 'track shipment' to locate your shipment.");
    }
    
    
    
    
    public void trackShipment(Statement stmt,String customerID){
        
        System.out.println("---------------------------------TRACK SHIPMENT----------------------------\n");
        
        Scanner sc = new Scanner(System.in);
        
        while(true){
        System.out.println("\n---------------------------------------------");
        System.out.println("PRESS 1 : To show all shipments.");
        System.out.println("PRESS 2 : To track current shipment.");
        System.out.println("PRESS 3 : To go back.\n");
        System.out.println("---------------------------------------------\n");
        
        System.out.print("Enter your choice : ");
        int check = sc.nextInt();
        
        if(check==1){
            
            
            try{     
            System.out.println("**************YOUR SHIPMENTS***************\n");
            

            ResultSet rs = stmt.executeQuery("select * from shipment where customerID = '"+ customerID+"';");
            
            while(rs.next()){
                System.out.println("SHIPMENT ID:"+rs.getInt(1)+" OFFICE ID:"+rs.getInt(2)+" DELIVERYMAN ID:"+rs.getInt(3)+" COST:"+rs.getDouble(4)+" STATUS:"+rs.getString(5)+" CUSTOMER ID:"+rs.getString(6)+" PICKUP DATE:"+rs.getDate(7)+" DELIVERY DATE:"+rs.getDate(8));
            }
                rs.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            System.out.println("********************************************\n");
        }
        
        else if(check==2){
            System.out.println("\n---------------------------------------------");
            System.out.println("\nPRESS 1 : To check location.");
            System.out.println("PRESS 2 : To Show Delivery Man's Details.");
            System.out.println("PRESS 3 : To show parcel Details.");
            System.out.println("PRESS 4 : To show office Details.");
            System.out.println("PRESS 5 : To show vehicle Details.");
            System.out.println("PRESS 6 : To go back.\n");
            System.out.println("---------------------------------------------\n");
            System.out.print("Enter your choice : ");
            
            int ch2 = sc.nextInt();
            
            switch(ch2){
                
                case 1 : new PickupLocation().getAddress(stmt,customerID);
                         new DeliveryLocation().getAddress(stmt, customerID);
                         break;
                case 2 : new DeliveryBoy(stmt,customerID);
                         break;
                case 3 : new Parcel(stmt,customerID);
                         break;
                case 4 : new Office(stmt,customerID);
                         break;
                case 5 : new Vehicle(stmt,customerID);
                         break;
                case 6 : System.out.println("Going Back");
                default: System.out.println("Invalid Option , Going Back.");
                         break;
                         
            }
            
        }
        else if(check==3)
            {
                System.out.println("Going back...");
                System.out.println("-----------------------------------------------------------------\n");
                break;
            }
        
        else System.out.println("Invalid Option , Try Again.");
        }

    }
    
    public void cancelShipment(Statement stmt,String customerID){
        
        Scanner sc= new Scanner(System.in);
        
        System.out.println("-------------------------CANCEL SHIPMENT--------------------------\n");
        
        String sql = "select shipmentID from shipment where customerID = '"+customerID+"' order by pickupDate desc limit 1";
        
        try{
        ResultSet rs = stmt.executeQuery(sql);
        
        
        if(rs.next()){

                this.shipmentID = rs.getInt(1);
                System.out.println(rs.getInt(1));
    
            System.out.println("Do you want to cancel your shipment [Y/n]:");
            String check = sc.next();
            
            if(check.compareTo("Y")==0 || check.compareTo("y")==0){
                
                sql = "delete from location where shipmentID ="+ this.shipmentID;
                stmt.execute(sql);
                
                sql = "delete from parcel where parcelID = "+ this.shipmentID;
                stmt.execute(sql);
                
                sql = "delete from shipment where shipmentID = "+ this.shipmentID;
                stmt.execute(sql);
                
                System.out.println("Shipment is cancelled Successfully.");
            }
            
            else if(check.compareTo("N")==0 || check.compareTo("n")==0){
                System.out.println("Going Back...");
                }
            
            }
        else System.out.println("No Shipment Placed.");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        /*try{
        String sql = "select shipmentID from shipment where"
                + " customerID = '"+customerID+"'"
                + " order by pickupDate desc limit 1";
        
        ResultSet rs = stmt.executeQuery(sql);
        
        if(rs.next()){
            tmp = true;
            while(rs.next()){
                this.shipmentID = rs.getInt(1);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        if(tmp){
            System.out.println("Do you want to cancel your current Shipment [Y/n]:");
            String check = sc.next();
            
            if(check.compareTo("Y")==0 || check.compareTo("y")==0){
                
            try{    
                String sql = "delete from location where shipmentID ="+ this.shipmentID;
                stmt.execute(sql);
                }
            catch(SQLException e){
                e.printStackTrace();
            }
            try{
                String sql = "delete from parcel where parcelID = "+ this.shipmentID;
                stmt.execute(sql);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            try{
                String sql = "delete from shipment where shipmentID = "+ this.shipmentID;
                stmt.execute(sql);
                
                System.out.println("Your Shipment is cancelled Successfully.");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
                
                             
            }     
         
            else if(check.compareTo("N")==0 || check.compareTo("n")==0){
                System.out.println("Going Back...");
                }
            
            }
        else System.out.println("No New Shipment is placed");
           
    }*/
    
    }
}




