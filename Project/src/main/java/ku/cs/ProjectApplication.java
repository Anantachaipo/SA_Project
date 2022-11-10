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
        // --- Startup ---
        Router.when("login", pkg + "login.fxml");
        Router.when("register", pkg + "register.fxml");
        // --- Customer side ---
        Router.when("menu", pkg + "menu.fxml");
        Router.when("manage_order", pkg + "manage_order.fxml");
        Router.when("reserve", pkg + "reserve.fxml");
        Router.when("new_order", pkg + "new_order.fxml");
        Router.when("new_order_checkout", pkg + "new_order_checkout.fxml");
        Router.when("view_contract", pkg + "view_contract.fxml");
        // --- Manager side ---
        Router.when("menu_manager", pkg + "menu_manager.fxml");
        Router.when("manage_contract", pkg + "manage_contract.fxml");
        Router.when("manage_manager_order", pkg + "manage_manager_order.fxml");
        Router.when("manage_product", pkg + "manage_product.fxml");
        Router.when("new_product", pkg + "new_product.fxml");
        Router.when("new_contract", pkg + "new_contract.fxml");
        Router.when("contract_history", pkg + "contract_history.fxml");
        Router.when("receipt", pkg + "receipt.fxml");
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
