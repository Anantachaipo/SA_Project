package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        Router.bind(this, stage, "SA Project - Fruit Farm", 800, 600);
        configRoute();
        Router.goTo("login");
    }

    // TODO: ทำ path ของ fxml
    private static void configRoute() {
        String pkg = "ku/cs/";
        Router.when("login", pkg + "login.fxml");
        Router.when("register", pkg + "register.fxml");
        Router.when("menu", pkg + "menu.fxml");
        Router.when("menu_manager", pkg + "menu_manager.fxml");
        Router.when("manage_order", pkg + "manage_order.fxml");
        Router.when("reserve", pkg + "reserve.fxml");
        Router.when("new_order", pkg + "new_order.fxml");
        Router.when("view_contract", pkg + "view_contract");
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
