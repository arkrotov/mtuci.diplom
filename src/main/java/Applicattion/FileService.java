package Applicattion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class FileService {

    public static void write(String relation, Object[] attributions, String classes, List<Stream> masOfData) throws IOException {

        File file = new File("src/main/resources/" + relation + ".txt");

        FileWriter fileWriter = new FileWriter(file);

        System.out.println(file.canWrite());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@relation ").append(relation).append("\n\n");

        for (int i = 0; i < 2; i++) {
            stringBuilder.append("@attribute ").append(attributions[i]).append(" string\n");
        }
        for (int i = 2; i < attributions.length - 1; i++) {
            stringBuilder.append("@attribute ").append(attributions[i]).append(" numeric\n");
        }

        stringBuilder
                .append("@attribute ")
                .append(attributions[attributions.length - 1])
                .append(" {")
                .append(classes)
                .append("}\n\n")
                .append("@data\n");

        fileWriter.write(stringBuilder.toString());
        fileWriter.flush();

        for (Stream data : masOfData) {
            fileWriter.write(data.toFile());
            fileWriter.write("\n");
            fileWriter.flush();
        }
    }

}
