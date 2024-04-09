module com.example.bilregisterdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.bilregisterdesktop to javafx.fxml;
    exports com.example.bilregisterdesktop;
    exports com.example.bilregisterdesktop.Controller to javafx.fxml;
    exports com.example.bilregisterdesktop.Database to javafx.fxml;
    opens com.example.bilregisterdesktop.Controller to javafx.fxml;

}
