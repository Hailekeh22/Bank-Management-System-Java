module com.mycompany.bankmgmtsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.bankmgmtsystem to javafx.fxml;
    exports com.mycompany.bankmgmtsystem;
}
