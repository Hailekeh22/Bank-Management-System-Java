package com.mycompany.bankmgmtsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    // CUSTOMER REGISTRATION CODE STARTS
    
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
        String fname = firstNameField.getText();
        String sex = sexField.getText();
        String phoneno = phoneNumberField.getText();
        String lname = lastNameField.getText();     
        String addressvalue = addressField.getText();
        String initialDepositvalue = initialDepositField.getText();

        
        if (fname.isEmpty() 
             || lname.isEmpty() || sex.isEmpty() || phoneno.isEmpty() 
             || addressvalue.isEmpty() || initialDepositvalue.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Error", "PLease Fill All The Inputs First Before Submiting");
            
            return;
        }

       
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root","h@ile22199253");
            String sqlquery = "INSERT INTO customers (firstname, lastname, sex, phonenumber, address, amount) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlquery);
            
            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, sex);
            statement.setString(4, phoneno);
            statement.setString(5, addressvalue);
            statement.setString(6, initialDepositvalue);

            int registerd = statement.executeUpdate();
            
                 if (registerd > 0) {
                     showAlert(Alert.AlertType.INFORMATION,"Success", "Successfully Registerd ");           
                     firstNameField.setText("");
                     lastNameField.setText("");
                     sexField.setText("");
                     phoneNumberField.setText("");
                     addressField.setText("");
                     initialDepositField.setText("");           
                 initializetable();
            } else {
                showAlert(Alert.AlertType.ERROR,"Error", "The Customer Is not Registerd! Please Enter A Valid Input and Try Again later ");
                }
                 
                 connection.close();
        
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error",e.getMessage());
        }
             
}   // CUSTOMER REGISTRATION CODE ENDS
    
    
    
    //ALERT CONTROL CODE STARTS

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    
    //ALERT CONTROL CODE ENDS
}
    

    
    
    
    // DEPOSIT TO ACCOUNT CODE STARTES
    
    @FXML
    private TextField accountnumberfordeposit;
    
    @FXML
    private TextField depositamount;
      
    @FXML
    private void deposit() {
    String accountnumbervalue = accountnumberfordeposit.getText();
    String depositamountvalue = depositamount.getText();

         if (accountnumbervalue.isEmpty() 
             || depositamountvalue.isEmpty()) {   
             showAlert(Alert.AlertType.ERROR, "Error", "Please Enter Account Number And Amount");
      
        }

    try {
        
        int depositValue = Integer.parseInt(depositamountvalue);


            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");
            String sql = "UPDATE customers SET amount = amount + ? WHERE accountnumber = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, depositValue);
            statement.setString(2, accountnumbervalue);

        int depositdone = statement.executeUpdate();

        if (depositdone > 0) {
            
            showAlert(Alert.AlertType.INFORMATION, "Success", "Sucessfully Deposited To Customers Account");
            accountnumberfordeposit.setText("");
            depositamount.setText("");
            initializetable();
            
        } else {
            
            showAlert(Alert.AlertType.ERROR, "Error", "The Entered Account Number Is Not Found In The Database!");
        }

        connection.close();
        
    } catch (Exception e) {
        
        showAlert(Alert.AlertType.ERROR, "Error", "Please Enter A valid Amount " + e.getMessage());
    } 
}

    // DEPOSIT TO ACCOUNT CODE ENDS




    // WITHDRAWAL MONEY CODE STARTS

    @FXML
    private TextField withdrawalamountinput;
    
    @FXML
    private TextField witdrawalaccountinput;

    @FXML
    private void withdrawal() {
        
    String accountforwithdrawal = witdrawalaccountinput.getText();
    String withdrawalamount = withdrawalamountinput.getText();

        if (accountforwithdrawal.isEmpty()|| withdrawalamount.isEmpty()) {
            
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all the inputs.");
                return;
             }

    try {
        
        int withdrawalvalue = Integer.parseInt(withdrawalamount);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");
        String withdrawalquery = "SELECT amount FROM customers WHERE accountnumber = ?";
        PreparedStatement selectStatement = connection.prepareStatement(withdrawalquery);
        selectStatement.setString(1, accountforwithdrawal);

        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
            
            double currentamountvalue = resultSet.getDouble("amount");

            if (currentamountvalue >= withdrawalvalue) {
                
                withdrawalquery = "UPDATE customers SET amount = amount - ? WHERE accountnumber = ?";
                PreparedStatement updateStatement = connection.prepareStatement(withdrawalquery);
                updateStatement.setDouble(1, withdrawalvalue);
                updateStatement.setString(2, accountforwithdrawal);

                int withdrawaldone = updateStatement.executeUpdate();

                if (withdrawaldone > 0) {
                    
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Withdrawal successful .");
                    withdrawalamountinput.setText("");
                    witdrawalaccountinput.setText("");
                    initializetable();
                } else {
                    
                    showAlert(Alert.AlertType.ERROR, "Error", "Withdrawal Error!.");
                }
            } else {
                
                showAlert(Alert.AlertType.ERROR, "Error", "Your Account Does't have the Requested Amount of Money");
            }
        } else {
            
            showAlert(Alert.AlertType.ERROR, "Error", "Account Doesn't exist");
        }
        connection.close();
        
    } catch (Exception e) {
        
        showAlert(Alert.AlertType.ERROR, "Error", "Invalid withdrawal amount. Please enter a valid number.");
    } 
}

    // WITHDRAWAL MONEY CODE ENDS





    //SHOW USERS CODE STARTS

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

    @FXML
    private void initializetable() {
        
        try {
            
            accountNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomeraccount().asObject());
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomerfname());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomerlname());
            addressColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomeraddress());
            amountColumn.setCellValueFactory(cellData -> cellData.getValue().getcustomeramount().asObject());

            tableView.setItems(CustomerDAO.getAllRecords());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database error ");
        }
    }  

    //SHOW USERS CODE ENDS
    
    
    
    
    //DELETE ACCOUNT CODE STARTS
    
    @FXML
    private TextField accounttoremoveinput;
    
    @FXML
    private void deleteAccount() {
        
            String accountnumbertodelete = accounttoremoveinput.getText();

            if (accountnumbertodelete.isEmpty()) {
                   
                    showAlert(Alert.AlertType.ERROR, "Error", "Please Enter the Account number to delete.");
            }

    try {
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");

        
        String checkbalancequery = "SELECT amount FROM customers WHERE accountnumber = ?";
        PreparedStatement accountamount = connection.prepareStatement(checkbalancequery);
        accountamount.setString(1, accountnumbertodelete);
        ResultSet result = accountamount.executeQuery();

        if (result.next()) {
            
            int balance = result.getInt("amount");

            if (balance == 0) {
                
                String deleteQuery = "DELETE FROM customers WHERE accountnumber = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setString(1, accountnumbertodelete);

                int deleteuserdata = deleteStatement.executeUpdate();

                if (deleteuserdata > 0) {
                    
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Account has been deleted successfully.");
                    accounttoremoveinput.setText("");
                    initializetable(); 
                    
                } else {
                    
                    showAlert(Alert.AlertType.ERROR, "Error", "Account did not found");
                }
            } else {
                
                showAlert(Alert.AlertType.WARNING, "Warning", "Customers Account balance is not 0. Please withdraw the money before closing your account.");
            }
        } else {
            
            showAlert(Alert.AlertType.ERROR, "Error", "Account not found.");
        }

        connection.close();
        
    } catch (Exception e) {
        
        showAlert(Alert.AlertType.ERROR, "Error", "Can Occured on communication. with the database! " );
    }
}

    
    //DELETE ACCOUNT CODE ENDS



    //MONEY TRANSFER CODE STARTS

    @FXML
    private TextField reciveraccount;

    @FXML
    private TextField senderaccount;

    @FXML
    private TextField sendingamount;
    
    @FXML
    private void transferMoney() {
        
    String senderaccountnumber = senderaccount.getText();
    String reciveraccountnumber = reciveraccount.getText();
    String amounttobetransferd = sendingamount.getText();

    if (senderaccountnumber.isEmpty() || reciveraccountnumber.isEmpty() || amounttobetransferd.isEmpty()) {
        
        showAlert(Alert.AlertType.ERROR, "Error", "Please Enter All The Inputs");
        
    }

    try { 
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsys", "root", "h@ile22199253");

        String senderBalanceQuery = "SELECT amount FROM customers WHERE accountnumber = ?";
        PreparedStatement senderBalanceStatement = connection.prepareStatement(senderBalanceQuery);
        senderBalanceStatement.setString(1, senderaccountnumber);
        ResultSet senderBalanceResult = senderBalanceStatement.executeQuery();

        if (senderBalanceResult.next()) {
            
            int senderBalance = senderBalanceResult.getInt("amount");
            int amount = Integer.parseInt(amounttobetransferd);

            if (senderBalance >= amount) {
                
                int updatedsenderbalance = senderBalance - amount;
                String withdrawQuery = "UPDATE customers SET amount = ? WHERE accountnumber = ?";
                PreparedStatement withdrawallstatement = connection.prepareStatement(withdrawQuery);
                withdrawallstatement.setInt(1, updatedsenderbalance);
                withdrawallstatement.setString(2, senderaccountnumber);
                withdrawallstatement.executeUpdate();

                
                String reciveraccountquery = "SELECT amount FROM customers WHERE accountnumber = ?";
                PreparedStatement reciverbalancevalue = connection.prepareStatement(reciveraccountquery);
                reciverbalancevalue.setString(1, reciveraccountnumber);
                ResultSet reciverbalance = reciverbalancevalue.executeQuery();

                if (reciverbalance.next()) {
                    
                    int reciveroldbalance = reciverbalance.getInt("amount");
                    int recivernewbalance = reciveroldbalance + amount;

                    String creditbalancequery = "UPDATE customers SET amount = ? WHERE accountnumber = ?";
                    PreparedStatement creditreciveraccount = connection.prepareStatement(creditbalancequery);
                    creditreciveraccount.setInt(1, recivernewbalance);
                    creditreciveraccount.setString(2, reciveraccountnumber);
                    creditreciveraccount.executeUpdate();

                    showAlert(Alert.AlertType.INFORMATION, "Success", "Money transferred successfully.");
                    
                    reciveraccount.setText("");
                    senderaccount.setText("");
                    sendingamount.setText("");
                    
                    initializetable(); 
                } else {
                    
                    showAlert(Alert.AlertType.ERROR, "Error", "Receiver account is not found. Please Try Again!");
                }
            } else {
                
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient balance in the sender's account.");
            }
        } else {
            
            showAlert(Alert.AlertType.ERROR, "Error", "Sender account is not found. Please Try Again!");
        }

        connection.close();
    } catch (Exception e) {
        
        showAlert(Alert.AlertType.ERROR, "Error", "Error occured on Comunicating to the Database " );
    }
}

    //MONEY TRANSFER CODE ENDS
      
    
    
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
    
        
        //LOGOUT CODE STARTS
    
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

        //LOGOUT CODE ENDS
        
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