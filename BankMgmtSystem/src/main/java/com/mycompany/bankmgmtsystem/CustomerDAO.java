package com.mycompany.bankmgmtsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    // Method to fetch data from the database and return as ObservableList<Customer>
    public static ObservableList<Customer> getAllRecords() throws ClassNotFoundException, SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        // Establishing connection to the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253")) {
            String sql = "SELECT accountnumber, firstname, lastname, address, amount FROM customers";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Iterating through the ResultSet and adding Customer objects to the list
            while (resultSet.next()) {
                int accountNumber = resultSet.getInt("accountnumber");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                int amount = resultSet.getInt("amount");

                Customer customer = new Customer();
                customer.setcustaccount(accountNumber);
                customer.setcustfname(firstName);
                customer.setcustlname(lastName);
                customer.setcustaddress(address);
                customer.setcustamount(amount);

                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception for handling at a higher level
        }

        return customerList;
    }
}
