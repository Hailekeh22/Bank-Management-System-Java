package com.mycompany.bankmgmtsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrimaryController {
    
    @FXML
    private Button loginbtn;
    
    @FXML
    private AnchorPane loginform;  

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

   
    private PreparedStatement prepare;
    private Connection connect;
    private ResultSet result;
    
    
    public void tellerlogin() {
        String sql = "SELECT * FROM teller WHERE username = ? and password = ?";
        
        connect = dbconnect.databaseconnection();
        
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            
            result = prepare.executeQuery();
            
            Alert alert;
            
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Please Write your username and password");
                alert.showAndWait();
            
            } else {
                if(result.next()){
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("WELCOME!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Sucess");
                    alert.showAndWait();
                    
                    
                    loginbtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    
                
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username Or Password");
                    alert.showAndWait();
                }
            }
            
        } catch(Exception e) {e.printStackTrace();}
    }
    

    public void show() {
    System.out.println("Hello you Clicked the login buton");
    };
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}