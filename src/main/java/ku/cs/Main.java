package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ku.cs.controllers.*;

import java.io.IOException;


public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        com.github.saacsos.FXRouter.bind(this, stage, "Lawyer Of The Public", 1000, 800);
        configRoute();
        com.github.saacsos.FXRouter.goTo("first_page");
    }
    private static void configRoute() {
        String packageStr = "ku/cs/";
        com.github.saacsos.FXRouter.when("admin", packageStr+"admin_controller.fxml");
        com.github.saacsos.FXRouter.when("first_page", packageStr+"first_page.fxml");
        com.github.saacsos.FXRouter.when("lawyer_login", packageStr+"lawyer_login.fxml");
        com.github.saacsos.FXRouter.when("user_login", packageStr+"user_login.fxml");
        com.github.saacsos.FXRouter.when("user_register", packageStr+"user_register.fxml");
        com.github.saacsos.FXRouter.when("lawyer_register", packageStr+"lawyer_register.fxml");
        com.github.saacsos.FXRouter.when("user_menu", packageStr+"user_menu.fxml");
        com.github.saacsos.FXRouter.when("user_home_page", packageStr+"user_home_page.fxml");
/*
        com.github.saacsos.FXRouter.when("user_consult_lawyer", packageStr+"user_consult_lawyer.fxml");
        com.github.saacsos.FXRouter.when("user_home_page", packageStr+"user_home_page.fxml");
        com.github.saacsos.FXRouter.when("user_settings", packageStr+"user_settings.fxml");
        com.github.saacsos.FXRouter.when("user_warn", packageStr+"user_warn.fxml");
        com.github.saacsos.FXRouter.when("user_history", packageStr+"user_history.fxml");
        com.github.saacsos.FXRouter.when("search_lawyer", packageStr+"search_lawyer.fxml");
*/
        com.github.saacsos.FXRouter.when("test", packageStr+"lawyer_home_page.fxml");
        com.github.saacsos.FXRouter.when("lawyer_home_page", packageStr+"lawyer_home_page.fxml",String.valueOf(LawyerHomePageController.class));
        com.github.saacsos.FXRouter.when("lawyer_consultation_service", packageStr+"lawyer_consultation_service.fxml",String.valueOf(LawyerConsultationServiceController.class));
        com.github.saacsos.FXRouter.when("lawyer_consultation_service2", packageStr+"lawyer_consultation_service2.fxml",String.valueOf(LawyerConsultationServiceController2.class));
        com.github.saacsos.FXRouter.when("lawyer_history", packageStr+"lawyer_history.fxml",String.valueOf(LawyerHistoryController.class));
        com.github.saacsos.FXRouter.when("lawyer_warn", packageStr+"lawyer_warn.fxml",String.valueOf(LawyerWarnController.class));


        com.github.saacsos.FXRouter.when("user_consult_lawyer", packageStr + "user_consult_lawyer.fxml", String.valueOf(UserConsultLawyerController.class));
        com.github.saacsos.FXRouter.when("user_settings", packageStr + "user_settings.fxml", String.valueOf(UserSettingsController.class));
        com.github.saacsos.FXRouter.when("user_warn", packageStr + "user_warn.fxml", String.valueOf(UserWarnController.class));
        com.github.saacsos.FXRouter.when("user_history", packageStr + "user_history.fxml", String.valueOf(UserHistoryController.class));
        com.github.saacsos.FXRouter.when("search_lawyer", packageStr + "search_lawyer.fxml", String.valueOf(SearchLawyerController.class));
        com.github.saacsos.FXRouter.when("lawyer_home_page", packageStr + "lawyer_home_page.fxml", String.valueOf(LawyerHomePageController.class));
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
