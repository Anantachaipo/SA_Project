module appDBConnect {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ku.cs to javafx.fxml;
    exports ku.cs;
}