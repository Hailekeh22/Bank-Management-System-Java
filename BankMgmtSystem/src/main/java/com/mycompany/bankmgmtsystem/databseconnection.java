package com.mycompany.bankmgmtsystem;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class databseconnection {
    
    
   public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        
        Class.forName("com.mysql.cj.jdbc.Driver");        
                
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root","password");     
        System.out.println("connected! " +  connection.getCatalog()); 
}

}