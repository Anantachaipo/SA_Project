package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("first_page.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(),600,800);
//        configRoute();
//        stage.setTitle("Lawyer For The Public");
//        stage.setScene(scene);
//        stage.show();
//
//    }
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        com.github.saacsos.FXRouter.bind(this, stage, "Lawyer Of The Public", 600, 800);
        configRoute();
        com.github.saacsos.FXRouter.goTo("first_page");
    }
    private static void configRoute() {
        String packageStr = "ku/cs/";
        com.github.saacsos.FXRouter.when("first_page", packageStr+"first_page.fxml");
        com.github.saacsos.FXRouter.when("lawyer_login", packageStr+"lawyer_login.fxml");
        com.github.saacsos.FXRouter.when("user_login", packageStr+"user_login.fxml");
        com.github.saacsos.FXRouter.when("user_register", packageStr+"user_register.fxml");
        com.github.saacsos.FXRouter.when("lawyer_register", packageStr+"lawyer_register.fxml");
        com.github.saacsos.FXRouter.when("user_menu", packageStr+"user_menu.fxml");
    }
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }
}
