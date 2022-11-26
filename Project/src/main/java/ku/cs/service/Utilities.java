package ku.cs.service;

import ku.cs.Router;

import java.io.IOException;

public class Utilities {


    public static void gotoPage(String toPage) {
        try {
            Router.goTo(toPage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't go to " + toPage);
        }
    }
}
