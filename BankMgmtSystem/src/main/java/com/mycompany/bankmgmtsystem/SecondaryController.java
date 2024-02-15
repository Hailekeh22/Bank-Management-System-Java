package com.mycompany.bankmgmtsystem;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
        stage.setTitle("Zemen Bank's Banking System");
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