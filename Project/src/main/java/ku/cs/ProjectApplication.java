package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApplication extends Application {

    private static Scene scene;
    // TODO: แก้ start ให้เปิดหน้า login
    @Override
    public void start(Stage stage) throws Exception {
        Router.bind(this, stage, "Login", 800, 600);
        configRoute();
        Router.goTo("login");
    }

    // TODO: ทำ path ของ fxml
    private static void configRoute() {
        String pkg = "ku/cs/";
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
