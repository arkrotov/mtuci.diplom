package Applicattion;

import lombok.Data;

@Data
public class MockApp {

    private String[] mas = new String[]{
            "Yandex",
            "Google",
            "VK",
            "Appstore",
            "Chess",
            "Safari",
            "IE"

    };

    public String getMasToString() {
        StringBuilder sb = new StringBuilder();

        for (String ma : mas) {
            sb.append(ma).append(", ");
        }

        sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(" "));


        return sb.toString();
    }
}
