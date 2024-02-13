module com.mycompany.bankmgmtsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;    

    opens com.mycompany.bankmgmtsystem to javafx.fxml;
    exports com.mycompany.bankmgmtsystem;
}
