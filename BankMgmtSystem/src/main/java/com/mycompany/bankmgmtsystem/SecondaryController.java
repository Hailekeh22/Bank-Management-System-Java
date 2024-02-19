package com.mycompany.bankmgmtsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SecondaryController {
    
    
    @FXML
    private AnchorPane withdrawalpane;
    
    @FXML
    private AnchorPane transferpane;
    
    @FXML
    private AnchorPane depositpane;
    
    @FXML
    private AnchorPane createaccountpane;
    
    @FXML
    private AnchorPane allcustomerspane;
    
    @FXML
    private AnchorPane homepane;
    
    // CUSTOMER REGISTRATION INPUTS AND SUBMIT BUTTON
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
     
    @FXML
    private TextField sexField;
    
    @FXML
    private TextField phoneNumberField;
    
    @FXML
    private TextField addressField;
    
    @FXML
    private TextField initialDepositField;
    
    
    @FXML
    private void registerUser() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String sex = sexField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String initialDeposit = initialDepositField.getText();

        
        if (firstName.isEmpty() || lastName.isEmpty() || sex.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || initialDeposit.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Error", "PLease Fill all the Inputs");
            return;
        }

       
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root","h@ile22199253");
            String sql = "INSERT INTO customers (firstname, lastname, sex, phonenumber, address, amount) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, sex);
            statement.setString(4, phoneNumber);
            statement.setString(5, address);
            statement.setString(6, initialDeposit);

            int registerd = statement.executeUpdate();
            
            if (registerd > 0) {
                showAlert(Alert.AlertType.INFORMATION,"Success", "Sucessfully Registerd ");
                firstNameField.setText("");
                lastNameField.setText("");
                sexField.setText("");
                phoneNumberField.setText("");
                addressField.setText("");
                initialDepositField.setText("");
            } else {
                showAlert(Alert.AlertType.ERROR,"Error", "There is an Error In registering the user ");
            }

            connection.close();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR,"Error", "Database error: " + e.getMessage());
        }
             
}

    private void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    
    // CUSTOMER REGISTRATION ENDS
    
    
    
    
    
    // DEPOSIT TO ACCOUNT FUNCTIONALITY
    
    @FXML
    private TextField accountnumberfordeposit;
    
    @FXML
    private TextField depositamount;
    
    
    @FXML
private void deposit() {
    String accountNumber = accountnumberfordeposit.getText();
    String depositAmount = depositamount.getText();

    if (accountNumber.isEmpty() || depositAmount.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "Fill The Inputs Before Depositing");
        return;
    }

    try {
        
        int depositValue = Integer.parseInt(depositAmount);


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");
        String sql = "UPDATE customers SET amount = amount + ? WHERE accountnumber = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, depositValue);
        statement.setString(2, accountNumber);

        int completedDeposit = statement.executeUpdate();

        if (completedDeposit > 0) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Sucessfully Deposited");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Deposit Failed. Account number Doesn't exist.");
        }

        connection.close();
    } catch (NumberFormatException e) {
        showAlert(Alert.AlertType.ERROR, "Error", "Invalid deposit amount. Please enter a valid number.");
    } catch (SQLException e) {
        showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
    }
}

    
    // DEPOSIT TO ACCOUNT ENDS
      
    @FXML
    private void initialize() {       
        showHomePage();
    }

    @FXML
    private void showHomePage() {
        setVisibility(homepane);
    }

    @FXML
    private void showCreateAccountPage() {
        setVisibility(createaccountpane);
    }

    @FXML
    private void showDepositPage() {
        setVisibility(depositpane);
    }

    @FXML
    private void showWithdrawalPage() {
        setVisibility(withdrawalpane);
    }

    @FXML
    private void showTransferPage() {
        setVisibility(transferpane);
    }

    @FXML
    private void showAllCustomersPage() {
        setVisibility(allcustomerspane);
    }
    
    
    @FXML
    private void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Loging Out");
        alert.setHeaderText(null);
        alert.setContentText("Logout successful. See you soon!");
        alert.showAndWait();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) homepane.getScene().getWindow();
        stage.setTitle("Zemen Bank");
        stage.setScene(scene);
        stage.show();
    }

    private void setVisibility(AnchorPane pane) {
        // Hide all AnchorPanes
        homepane.setVisible(false);
        createaccountpane.setVisible(false);
        depositpane.setVisible(false);
        withdrawalpane.setVisible(false);
        transferpane.setVisible(false);
        allcustomerspane.setVisible(false);

        pane.setVisible(true);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

}