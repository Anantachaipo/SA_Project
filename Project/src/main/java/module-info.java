module appDBConnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens ku.cs to javafx.fxml;
    exports ku.cs to javafx.fxml, javafx.graphics;

    exports ku.cs.controller to javafx.fxml;
    opens ku.cs.controller to javafx.fxml;

    exports ku.cs.service to javafx.fxml;
    opens ku.cs.service to javafx.fxml;
}