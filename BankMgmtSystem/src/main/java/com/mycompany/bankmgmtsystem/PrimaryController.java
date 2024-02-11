package com.mycompany.bankmgmtsystem;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    
    @FXML
    private Button loginbtn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;


    public void show() {
    System.out.println("Hello you Clicked the login buton");
    };
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}