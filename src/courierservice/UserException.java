/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;


public class UserException extends Exception{
    
    public UserException(String where)
    {
        System.out.println("Invalid input encountered in "+where+" part");
        System.out.println("Read the instructions and enter a valid input!\n");
    }
    
}
