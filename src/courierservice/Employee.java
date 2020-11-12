
package courierservice;

public class Employee {

    protected int empID;
    protected String empName;
    protected String empAddr;
    protected int contactNo;
    protected double salary;
    protected char gender;
    protected String date;//"dd-mm-yyyy"
    
    public void setEmpID(int id){//
        empID=id;    
    }
    public int getEmpID(){
        return this.empID;
    }
    
    public void setEmpName(String name){//
        empName=name; 
    }
    public String getEmpName(){
        return this.empName;
    }    
    
    public void setContactNo(int num){//
        contactNo=num;    
    }
    public int getContacNo(){
        return this.contactNo;
    }
    
    public void setSalary(double sal){//
        salary = sal;
    }
    public double getSalary(){
        return this.salary;
    }
    
    public void setAddress(String addr){//
        empAddr = addr;    
    }
    public String getAddress(){
        return this.empAddr;
    }

}
