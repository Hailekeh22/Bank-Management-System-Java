package com.mycompany.bankmgmtsystem;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class dbconnect {
    
    
   public static Connection databaseconnection() {
        Connection connection = null; 
       
        try {
                         
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root","h@ile22199253");     

        } catch ( SQLException e) {
        }
        
        return connection;
    }

}