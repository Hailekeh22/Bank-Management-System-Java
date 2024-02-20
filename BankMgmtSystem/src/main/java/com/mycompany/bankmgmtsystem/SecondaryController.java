package com.mycompany.bankmgmtsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private AnchorPane deletepane;
    
    @FXML
    private AnchorPane createaccountpane;
    
    @FXML
    private AnchorPane allcustomerspane;
    
    @FXML
    private AnchorPane homepane;
    
    // CUSTOMER REGISTRATION SECTION STARTS
    
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
            showAlert(Alert.AlertType.ERROR,"Error", "PLease Fill all the Inputs First");
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
                initializetable();
            } else {
                showAlert(Alert.AlertType.ERROR,"Error", "The Customer Is Not Registerd Please Try again");
            }

            connection.close();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR,"Error",e.getMessage());
        }
             
}   // CUSTOMER REGISTRATION ENDS
    
    
    
    //Alert Control section

    private void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    
    //Alert Control section ends
}
    

    
    
    // DEPOSIT TO ACCOUNT FUNCTIONALITY STARTES
    
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
            accountnumberfordeposit.setText("");
            depositamount.setText("");
            initializetable();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Deposit Failed. Account number Doesn't exist.");
        }

        connection.close();
    } catch (NumberFormatException e) {
        showAlert(Alert.AlertType.ERROR, "Error", "Deposit Amount is not correct");
    } catch (SQLException e) {
        showAlert(Alert.AlertType.ERROR, "Error",e.getMessage());
    }
}

    // DEPOSIT TO ACCOUNT ENDS




    // WITHDRAWAL MONEY FUNCTIONALITY STARTS

    @FXML
    private TextField withdrawalamountinput;
    
    @FXML
    private TextField witdrawalaccountinput;

    @FXML
    private void withdrawal() {
    String accountNumber = witdrawalaccountinput.getText();
    String withdrawalAmount = withdrawalamountinput.getText();

    if (accountNumber.isEmpty() || withdrawalAmount.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all the inputs.");
        return;
    }

    try {
        
        int withdrawalValue = Integer.parseInt(withdrawalAmount);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");
        String sql = "SELECT amount FROM customers WHERE accountnumber = ?";
        PreparedStatement selectStatement = connection.prepareStatement(sql);
        selectStatement.setString(1, accountNumber);

        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
            double currentAmount = resultSet.getDouble("amount");

            if (currentAmount >= withdrawalValue) {
                sql = "UPDATE customers SET amount = amount - ? WHERE accountnumber = ?";
                PreparedStatement updateStatement = connection.prepareStatement(sql);
                updateStatement.setDouble(1, withdrawalValue);
                updateStatement.setString(2, accountNumber);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Withdrawal successful .");
                    withdrawalamountinput.setText("");
                    witdrawalaccountinput.setText("");
                    initializetable();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to withdraw.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Your Account Does't have the Requested Amount of Money");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Account Doesn't exist");
        }

        connection.close();
    } catch (NumberFormatException e) {
        showAlert(Alert.AlertType.ERROR, "Error", "Invalid withdrawal amount. Please enter a valid number.");
    } catch (SQLException e) {
        showAlert(Alert.AlertType.ERROR, "Error",e.getMessage());
    }
}

    // WITHDRAWAL MONEY FUNCTIONALITY ENDS





    //SHOW USERS FUNCTIONALITY STARTS

    @FXML
    private TableView<Customer> tableView;

    @FXML
    private TableColumn<Customer, Integer> accountNumberColumn;

    @FXML
    private TableColumn<Customer, String> firstNameColumn;

    @FXML
    private TableColumn<Customer, String> lastNameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, Integer> amountColumn;

    // Method to populate TableView with data from the database
    @FXML
    private void initializetable() {
        try {
            accountNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomeraccount().asObject());
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomerfname());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomerlname());
            addressColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomeraddress());
            amountColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomeramount().asObject());

            tableView.setItems(CustomerDAO.getAllRecords());
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
        }
    }  

    //SHOW USERS ENDS
    
    //DELETE ACCOUNT FUNCTIONALITY STARTS
    
    @FXML
    private TextField accounttoremoveinput;
    
    @FXML
private void deleteAccount() {
    String accountNumber = accounttoremoveinput.getText();

    if (accountNumber.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please enter the account number to delete.");
        return;
    }

    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");

        
        String balanceQuery = "SELECT amount FROM customers WHERE accountnumber = ?";
        PreparedStatement balanceStatement = connection.prepareStatement(balanceQuery);
        balanceStatement.setString(1, accountNumber);
        ResultSet balanceResult = balanceStatement.executeQuery();

        if (balanceResult.next()) {
            int balance = balanceResult.getInt("amount");

            if (balance == 0) {
                String deleteQuery = "DELETE FROM customers WHERE accountnumber = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setString(1, accountNumber);

                int deletedRows = deleteStatement.executeUpdate();

                if (deletedRows > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Account deleted successfully.");
                    accounttoremoveinput.setText("");
                    initializetable(); 
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Account not found or deletion failed.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Account balance is not 0. Please withdraw the money before deleting the account.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Account not found.");
        }

        connection.close();
    } catch (SQLException e) {
        showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
    }
}

    
    //DELETE ACCOUNT FUNCTIONALITY ENDS



    //MONEY TRANSFER SECTION STARTS

    @FXML
    private TextField reciveraccount;

    @FXML
    private TextField senderaccount;

    @FXML
    private TextField sendingamount;
    
    @FXML
    private void transferMoney() {
    String senderAccountNumber = senderaccount.getText();
    String receiverAccountNumber = reciveraccount.getText();
    String amountStr = sendingamount.getText();

    if (senderAccountNumber.isEmpty() || receiverAccountNumber.isEmpty() || amountStr.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please Enter All The Inputs");
        return;
    }

    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");

      
        String senderBalanceQuery = "SELECT amount FROM customers WHERE accountnumber = ?";
        PreparedStatement senderBalanceStatement = connection.prepareStatement(senderBalanceQuery);
        senderBalanceStatement.setString(1, senderAccountNumber);
        ResultSet senderBalanceResult = senderBalanceStatement.executeQuery();

        if (senderBalanceResult.next()) {
            int senderBalance = senderBalanceResult.getInt("amount");
            int amount = Integer.parseInt(amountStr);

            if (senderBalance >= amount) {
                
                int newSenderBalance = senderBalance - amount;
                String withdrawQuery = "UPDATE customers SET amount = ? WHERE accountnumber = ?";
                PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);
                withdrawStatement.setInt(1, newSenderBalance);
                withdrawStatement.setString(2, senderAccountNumber);
                withdrawStatement.executeUpdate();

                
                String receiverBalanceQuery = "SELECT amount FROM customers WHERE accountnumber = ?";
                PreparedStatement receiverBalanceStatement = connection.prepareStatement(receiverBalanceQuery);
                receiverBalanceStatement.setString(1, receiverAccountNumber);
                ResultSet receiverBalanceResult = receiverBalanceStatement.executeQuery();

                if (receiverBalanceResult.next()) {
                    int receiverBalance = receiverBalanceResult.getInt("amount");
                    int newReceiverBalance = receiverBalance + amount;

                    String creditQuery = "UPDATE customers SET amount = ? WHERE accountnumber = ?";
                    PreparedStatement creditStatement = connection.prepareStatement(creditQuery);
                    creditStatement.setInt(1, newReceiverBalance);
                    creditStatement.setString(2, receiverAccountNumber);
                    creditStatement.executeUpdate();

                    showAlert(Alert.AlertType.INFORMATION, "Success", "Money transferred successfully.");
                    
                    reciveraccount.setText("");
                    senderaccount.setText("");
                    sendingamount.setText("");
                    initializetable(); 
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Receiver account not found.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient balance in the sender's account.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Sender account not found.");
        }

        connection.close();
    } catch (SQLException | NumberFormatException e) {
        showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
    }
}


    //MONEY TRANSFER SECTION ENDS
      
    @FXML
    private void initialize() {       
        showHomePage();
        initializetable();
    }

    @FXML
    private void showHomePage() {
        setVisibility(homepane);
    }
    
    @FXML
    private void showDeleteAccountPage(){
        setVisibility(deletepane);
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
        homepane.setVisible(false);
        createaccountpane.setVisible(false);
        deletepane.setVisible(false);
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