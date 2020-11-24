
package courierservice;

import java.util.*;
import java.sql.*;

public class Customer {
    
    private String customerID;
    private String customerName;
    private Long phoneNo;
    private String email;
    private String password;
    private String gender;
    
    public void addCustomer(Statement stmt) throws SQLException{
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Customer ID :");
        customerID = sc.next();
        sc.nextLine();
        System.out.print("Enter Your Full Name :");
        customerName = sc.nextLine();
        System.out.print("Enter your Contact Number :");
        phoneNo = sc.nextLong();
        System.out.print("Enter your Gender [F/M/O]: ");
        gender = sc.next();
        System.out.print("Enter your Email ID : ");
        email = sc.next();
        System.out.print("Enter your Password : ");
        password = sc.next();
        
        
        System.out.println("Signing in to the System...");
        stmt.execute("insert into customer values ('" + customerID +"','" + customerName + "',"+ phoneNo +",'" + email +"','"+ password + "','"+ gender +"')");
  
        
        System.out.println("Signed in to the System Successfully.");
        
        this.customerOptions(stmt);
        
    }
    
    public void selectCustomer(Statement stmt) throws SQLException{
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your customerID :");
        String checkID = sc.nextLine();
        System.out.println("Enter your password : ");
        String checkPass = sc.nextLine();
        
        String sql = "select count(customerID) from customer where customerID = '"+ checkID + "' and password = '"+ checkPass +"'";
        this.customerID = checkID;
        this.password = checkPass;
        
        int tmp = 0; 
        
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){    
            tmp = rs.getInt(1);     
         } 
            rs.close();  
        
            
        if(tmp==0){
                System.out.println("customerID or password is incorrect.");
                this.selectCustomer(stmt);
            }
            else{
                System.out.println("Logged in to the System successfully.\n");
                
                this.customerOptions(stmt);
            }
    }
    
    public void customerOptions(Statement stmt){
        
        Shipment sp = new Shipment();
        Scanner sc = new Scanner(System.in);
      
        while(true){
            
        System.out.println("\n********************************************************");
        System.out.println("PRESS 1 : To Place New Shipment.");
        System.out.println("PRESS 2 : To track your Shipment.");
        System.out.println("PRESS 3 : To cancel your Shipment.");
        System.out.println("PRESS 4 : To Show your Information.");
        System.out.println("PRESS 5 : To edit your Information.");
        System.out.println("PRESS 6 : To Log out from the System.\n");
        System.out.println("********************************************************\n");
        
        System.out.print("Enter your choice : ");
        int ch = sc.nextInt();
        
        switch(ch){
            case 1 : sp.placeShipment(stmt,this.customerID);
                     break;
            case 2 : sp.trackShipment(stmt,this.customerID);
                     break;
            case 3 : sp.cancelShipment(stmt,this.customerID);
                     break;
            case 4 : this.showDetails(stmt);
                     break;
            case 5 : this.editDetails(stmt);
                     break;
            case 6 : System.out.println("Logging out of the System...");
                     break;
            default : System.out.println("Invalid option , Please Try again.");
                
        }
        
        if(ch==6) break;
    }
        
    }
    
    
    public void showDetails(Statement stmt){
        
        //System.out.print("Enter your customerID : ");
        String checkId = this.customerID;             //sc.next();
        //System.out.println("Enter your Password : ");
        String checkPass = this.password;             //sc.nextLine();
        
        String sql = "select * from customer where customerID='"+ checkId +"'and password ='"+ checkPass +"';";
        try{
        ResultSet rs2 = stmt.executeQuery(sql);
        
        
        
        System.out.println("----------------------CUSTOMER DETAILS--------------------------\n");
        
        while(rs2.next()){
            System.out.print("CUSTOMER ID : \t");
            System.out.println(rs2.getString(1));
            
            System.out.print("CUSTOMER NAME : ");
            System.out.println(rs2.getString(2));
            
            System.out.print("CONTACT NO : \t");
            System.out.println(rs2.getLong(3));
            
            System.out.print("EMAIL ID : \t");
            System.out.println(rs2.getString(4));
            
            System.out.print("GENDER : \t");
            System.out.println(rs2.getString(6));
            
            System.out.println("-------------------------------------------------------------\n");            

        }
            rs2.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void editDetails(Statement stmt){
        
        System.out.println("-----------------------------EDIT DETAILS---------------------------\n");
        
        while(true){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n********************************************************");
        System.out.println("PRESS 1 : To Change Your Name.");
        System.out.println("PRESS 2 : To Change Your Contact Number.");
        System.out.println("PRESS 3 : To Change Your Email ID.");
        System.out.println("PRESS 4 : To Change Your Gender.");
        System.out.println("PRESS 5 : To Change Your Password.");
        System.out.println("PRESS 6 : To Go Back.");
        System.out.println("********************************************************\n");
        
        System.out.print("Enter Your Choice : ");
        int check = sc.nextInt();
        
        switch(check){
            
            case 1 : sc.nextLine();
                     System.out.print("Enter your new name : ");
                     String newName = sc.nextLine();
                     this.setName(newName,stmt);
                     break;
            
            case 2 : System.out.print("Enter your new Contact Number : ");
                     Long newNumber = sc.nextLong();
                     this.setPhoneNumber(newNumber,stmt);
                     break;
                     
            case 3 : System.out.print("Enter your new emailID : ");
                     String newEmail = sc.next();
                     this.setEmailID(newEmail,stmt);
                     break;
                     
            case 4 : System.out.print("Enter your gender [F/M/O]: ");
                     String newGender = sc.next();
                     this.setGender(newGender,stmt);
                     break;
                     
            case 5 : System.out.print("Enter your new password : ");
                     String newPass = sc.next();
                     this.setPassword(newPass,stmt);
                     break;
                     
            case 6 : System.out.println("Going Back...");
                     break;
                     
            default : System.out.println("Invalid Option , Please Try Again.");
            }
        
            if(check==6)
                   break;
        }
        
        System.out.println("-------------------------------------------------------------\n");            

    }
    
    private void setName(String newName,Statement stmt){
        
        this.customerName = newName;
        try{
            stmt.executeUpdate("update customer set customerName = '"+ newName +"' where customerID = '"+ this.customerID+"';");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Your Name is changed successfully.");
        
    }
    private void setPhoneNumber(Long newNumber,Statement stmt){
        
        this.phoneNo = newNumber;
        try{
            stmt.executeUpdate("update customer set phoneNumber = '"+ newNumber +"' where customerID = '"+ this.customerID+"';");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
         System.out.println("Your Contact Number is changed successfully.");
        
    }
    private void setEmailID(String newEmail,Statement stmt){
        
        this.email = newEmail;
        try{
            stmt.executeUpdate("update customer set email = '"+ newEmail +"' where customerID = '"+ this.customerID+"';");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
         System.out.println("Your Email is changed successfully.");
    }
    private void setGender(String newGender,Statement stmt){
        
        this.gender = newGender;
        try{
            stmt.executeUpdate("update customer set gender = '"+ newGender +"' where customerID = '"+ this.customerID+"';");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
         System.out.println("Your Gender is changed successfully.");
    }
    private void setPassword(String newPass,Statement stmt){
            
        this.password = newPass;
        try{
            stmt.executeUpdate("update customer set password = '"+ newPass +"' where customerID = '"+ this.customerID+"';");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
         System.out.println("Your Password is changed successfully.");
    }
 
}
