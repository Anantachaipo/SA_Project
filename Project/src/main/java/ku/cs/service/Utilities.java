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

    public static String thousandSeparator(int v) {
        String s = String.valueOf(v);
        // value is less than a thousand, return input
        if (s.length() <= 3) return s;

        // value is more than a thousand
        StringBuilder sb = new StringBuilder();
        int count = 0, r;
        for (int i=s.length(); i>0; i--) {
            r=v%10;
            v=v/10;
            count++;
            if (count%3==0 && i-1>0)
                sb.append(r).append(",");
            else
                sb.append(r);
        }
        return sb.reverse().toString();
    }
}
