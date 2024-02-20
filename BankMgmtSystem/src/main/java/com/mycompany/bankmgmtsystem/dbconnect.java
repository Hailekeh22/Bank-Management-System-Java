package com.mycompany.bankmgmtsystem;



import java.sql.*;



public class dbconnect {
    
    
   public static Connection databaseconnection() {
        Connection connection = null; 
       
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");                     
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root","h@ile22199253");     

        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        return connection;
    }

}