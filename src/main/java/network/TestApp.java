package network;

import classifiers.Test;
import lombok.Data;
import lombok.Getter;

/**
 * Created by me on 24.05.17.
 */


public class TestApp {

    private static String[] mas = new String[]{
            "firstApp",
            "secondApp",
            "thirdApp",
//            "fourthApp",
//            "fifthApp",
//            "sixthApp",
//            "seventhApp",
//            "eighthApp",
//            "ninthApp",
//            "tenthApp"
    };

    public static String[] getMas() {
        return mas;
    }

    public static String getMasToString() {
        StringBuilder sb = new StringBuilder();

        for (String ma : mas) {
            sb.append(ma).append(", ");
        }

        sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(" "));


        return sb.toString();
    }
}
