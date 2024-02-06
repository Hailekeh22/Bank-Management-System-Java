module com.mycompany.bankmgmt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.bankmgmt to javafx.fxml;
    exports com.mycompany.bankmgmt;
}
