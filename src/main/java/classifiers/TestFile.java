package classifiers;

import network.Stream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by me on 24.05.17.
 */
public class TestFile {

    private String relation = "applications";
    private Object[] attributions;
    private String classes;
    private List<Stream> masOfdata;

    public TestFile(String relation, Object[] attributions, String classes, List<Stream> masOfdata) {
        this.relation = relation;
        this.attributions = attributions;
        this.classes = classes;
        this.masOfdata = masOfdata;
    }

    public void write() throws IOException {

        File file = new File("TestStreams.txt");

        FileWriter fileWriter = new FileWriter(file);

        System.out.println(file.canWrite());

        StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append("@relation ").append(relation).append("\n\n");

        for (int i = 0; i < attributions.length - 1; i++) {
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

        for (Stream data : masOfdata) {
            fileWriter.write(data.toFile());
            fileWriter.write("\n");
            fileWriter.flush();
        }
    }

}
