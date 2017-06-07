package Applicattion;


import lombok.Data;
import lombok.Synchronized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class DaoService {

    final private List<Stream> streams;
    final private MockApp app;

    @Synchronized
    public List<Stream> getStreams() {
        return streams;
    }

    public DaoService() {
        streams = new ArrayList<>();
        app = new MockApp();
    }

    public void pull() throws IOException {

        List<Stream> streams = getStreams();

        List<Stream> trainStreams = new ArrayList<>();
        List<Stream> testStreams = new ArrayList<>();


        for (int i = 0; i < streams.size() * 2 / 3; i++) {
            trainStreams.add(streams.get(i));
        }

        for (int i = streams.size() * 2 / 3; i < streams.size(); i++) {
            testStreams.add(streams.get(i));
        }


        write("TrainStreams", Stream.getNameFields(), app.getMasToString(), trainStreams);
        write("TestStreams", Stream.getNameFields(), app.getMasToString(), testStreams);
    }


    public void push() {
        List<Stream> groupFlow = getStreams();
    }

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
