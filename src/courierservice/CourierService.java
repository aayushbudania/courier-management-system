/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

import java.sql.*;
import java.math.*;
import java.util.Scanner;

public class CourierService {

    static final String user = "root";
    static final String pass = "aayush@123";
    static Connection conn = null;
    static Statement stmt = null;
    
    public static void connectDB(){
        
        try{
                Class.forName("com.mysql.cj.jdbc.Driver");//Register JDBC Driver.
                
                System.out.println("Connecting to database");
                Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/courier_service",user,pass);
                System.out.println("Connected to database successfully");
                
                stmt = conn.createStatement();
                String sql = "show tables";

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
                e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        connectDB();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------Courier Service Management System--------------------\n");
        System.out.print("Customer or DeliveryBoy [C/d] :");
        String ch1 = sc.next();
        
        if(ch1.compareTo("c")==0 || ch1.compareTo("C")==0){
            
            Customer cs = new Customer();
            
            System.out.println("Are you a new Customer [Y/n] :");
            String ch2 = sc.next();
            
            if(ch2.compareTo("y")==0 || ch2.compareTo("Y")==0)
                cs.addCustomer(stmt);
            
            else if(ch2.compareTo("n")==0 || ch2.compareTo("N")==0)
                cs.selectCustomer(stmt);
            
            System.out.println("Thank you.");
            
            
        }
        else if(ch1.compareTo("D")==0 || ch1.compareTo("d")==0){
            
            DeliveryBoy b = new DeliveryBoy(stmt);
            
            System.out.println("Thank You.");
        }
       
        try{
                stmt.close();
                //conn.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        
    }
    
}























