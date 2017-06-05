package Applicattion;


import lombok.Data;
import lombok.Synchronized;

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


        FileService.write("TrainStreams", Stream.getNameFields(), app.getMasToString(), trainStreams);
        FileService.write("TestStreams", Stream.getNameFields(), app.getMasToString(), testStreams);
    }


    public void push() {
        List<Stream> groupFlow = getStreams();
    }

}