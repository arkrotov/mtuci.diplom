package mocks;

public class MockApp {

    private static String[] mas = new String[]{
            "firstApp",
            "secondApp",
            "thirdApp",
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
