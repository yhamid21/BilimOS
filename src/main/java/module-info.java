module com.example.bilimosdashboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.formdev.flatlaf;


    opens com.example.bilimosdashboard to javafx.fxml;
//    exports com.example.bilimosdashboard;
}