
package courierservice;

import java.sql.*;
import java.util.*;

public class Office {
    
    private int officeID;
    private Long contactNo;
    private String emailID;
    private String address;
    
    public Office(){
        
    }
    
    public Office(Statement stmt, String customerID){
        this.getDetails(stmt,customerID);
    }
    
    
    public void setOfficeID(int id){
        officeID=id;    
    }
    public int getOfficeID(){
        return this.officeID;
    }
    public void setContactNo(Long id){//
        contactNo=id;    
    }
    public Long getContacNo(){
        return this.contactNo;
    }
    
    public void getDetails(Statement stmt , String customerID){
        
        System.out.println("OFFICE DETAILS:");
        
        String sql = "select o.* from office o"
                + " inner join (select officeID from shipment"
                + " where customerID ='"+ customerID+"'"
                + " order by pickupDate desc limit 1)s"
                + " on o.officeID = s.officeID;";
        
        try{
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                System.out.println("OFFICE ID : "+rs.getInt(1));
                System.out.println("CONTACT NO: "+ rs.getLong(2));
                System.out.println("EMAIL ID  : "+ rs.getString(3));
                System.out.println("ADDRESS   : "+ rs.getString(4)+"\n");
            }
            
            rs.close();
        }
        catch(SQLException e){
        e.printStackTrace();
        }
    }
    
    public void addOffice(Statement stmt){
    
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------------NEW OFFICE-----------------------------\n");
        
        try{
            ResultSet rs = stmt.executeQuery("select count(office) from office;");
            while(rs.next()){
                this.officeID = rs.getInt(1)+1;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        System.out.print("Enter Contact Number : ");
        this.contactNo = sc.nextLong();
        
        System.out.print("Enter Email Id : ");
        this.emailID = sc.next();
        
        sc.nextLine();
        System.out.print("Enter Address : ");
        this.address = sc.nextLine();
        
        try{
            String sql = "insert into office values ('"+this.officeID +"',"+this.contactNo+",'"+this.emailID+"','"+this.address+"')";
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }
}
