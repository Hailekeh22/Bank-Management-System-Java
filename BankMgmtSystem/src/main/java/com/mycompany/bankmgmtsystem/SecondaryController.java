package com.mycompany.bankmgmtsystem;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

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