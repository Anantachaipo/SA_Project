package ku.cs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;

public final class Router {
    private static final Double WINDOW_WIDTH = 800.0D;
    private static final Double WINDOW_HEIGHT = 600.0D;
    private static Router router;
    private static Object mainRef;
    private static Stage window;
    private static String windowTitle;
    private static Double windowWidth;
    private static Double windowHeight;
    private static AbstractMap<String, RouteScene> routes = new HashMap();
    private static RouteScene currentRoute;

    private Router() {
    }

    public static void bind(Object ref, Stage win) {
        checkInstances(ref, win);
    }

    public static void bind(Object ref, Stage win, String winTitle) {
        checkInstances(ref, win);
        windowTitle = winTitle;
    }

    public static void bind(Object ref, Stage win, double winWidth, double winHeight) {
        checkInstances(ref, win);
        windowWidth = winWidth;
        windowHeight = winHeight;
    }

    public static void bind(Object ref, Stage win, String winTitle, double winWidth, double winHeight) {
        checkInstances(ref, win);
        windowTitle = winTitle;
        windowWidth = winWidth;
        windowHeight = winHeight;
    }

    private static void checkInstances(Object ref, Stage win) {
        if (mainRef == null) {
            mainRef = ref;
        }

        if (router == null) {
            router = new Router();
        }

        if (window == null) {
            window = win;
        }

    }

    public static void when(String routeLabel, String scenePath) {
        RouteScene routeScene = new RouteScene(scenePath);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, String winTitle) {
        RouteScene routeScene = new RouteScene(scenePath, winTitle);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, double sceneWidth, double sceneHeight) {
        RouteScene routeScene = new RouteScene(scenePath, sceneWidth, sceneHeight);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, String winTitle, double sceneWidth, double sceneHeight) {
        RouteScene routeScene = new RouteScene(scenePath, winTitle, sceneWidth, sceneHeight);
        routes.put(routeLabel, routeScene);
    }

    public static void goTo(String routeLabel) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        loadNewRoute(route);
    }

    public static void goTo(String routeLabel, Object data) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        route.data = data;
        loadNewRoute(route);
    }

    private static void loadNewRoute(RouteScene route) throws IOException {
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Parent resource = (Parent)FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));
        window.setTitle(route.windowTitle);
        window.setScene(new Scene(resource, route.sceneWidth, route.sceneHeight));
        window.show();
    }
    
    public static Object getData() {
        return currentRoute.data;
    }

    private static class RouteScene {
        private String scenePath;
        private String windowTitle;
        private double sceneWidth;
        private double sceneHeight;
        private Object data;

        private RouteScene(String scenePath) {
            this(scenePath, getWindowTitle(), getWindowWidth(), getWindowHeight());
        }

        private RouteScene(String scenePath, String windowTitle) {
            this(scenePath, windowTitle, getWindowWidth(), getWindowHeight());
        }

        private RouteScene(String scenePath, double sceneWidth, double sceneHeight) {
            this(scenePath, getWindowTitle(), sceneWidth, sceneHeight);
        }

        private RouteScene(String scenePath, String windowTitle, double sceneWidth, double sceneHeight) {
            this.scenePath = scenePath;
            this.windowTitle = windowTitle;
            this.sceneWidth = sceneWidth;
            this.sceneHeight = sceneHeight;
        }

        private static String getWindowTitle() {
            return Router.windowTitle != null ? Router.windowTitle : "";
        }

        private static double getWindowWidth() {
            return Router.windowWidth != null ? Router.windowWidth : Router.WINDOW_WIDTH;
        }

        private static double getWindowHeight() {
            return Router.windowHeight != null ? Router.windowHeight : Router.WINDOW_HEIGHT;
        }
    }
}