package Applicattion;

import lombok.Data;

@Data
public class MockApp {

    private String[] mas = new String[]{
            "Skype",
            "VK",
            "Google Play",
            "YouTube",
            "Google Chrome",
            "Instagram",
            "Shazam",
            "Google Maps",
            "Twitter",
            "HearthStone"
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
