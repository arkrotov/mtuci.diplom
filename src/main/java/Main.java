import javafx.application.Application;
import javafx.stage.Stage;
import services.*;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import network.IP;
import network.Stream;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tests.ScatterChartSample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main extends Application{


    private static boolean enable = true;

    public static void main(String[] args) throws Exception {

        // SpringApplication.run(Main.class, args);

        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                reader.readLine();
                enable = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        JpcapCaptor instance = CaptorService.getInstance();

        System.out.println("Старт");
        for (int i = 0; i < 1000000; i++) {

            if (!enable) break;

            Packet packet = instance.getPacket();
            //System.out.println(i + " " + packet);

            if (i % 100 == 0 && i != 0) {
                System.out.println(i + " : " + GroupService.getResultSize());
            }
            if (packet == null || !(packet instanceof TCPPacket)) {
                i--;
                continue;
            }

            GroupService.formFlow((TCPPacket) packet);
        }

        GroupService.endGroup();

        System.out.printf("Итоговое количество потоков - %d\n", GroupService.getResultSize());

        List<List<IP>> resultGroupFlow = GroupService.getResultGroupFlow();

        List<Stream> streamList = new ArrayList<>();

    //    FileWriter fileWriter = new FileWriter(new File("StreamInfo"));

        for (List<IP> ipList : resultGroupFlow) {
              Stream e = new Stream(ipList);
              streamList.add(e);
            String metricValue = String.valueOf(e.getAverageNumberOfDataPacketsFromClient());
            String testApp = e.getTestApp();
            String metric = "averageNumberOfDataPacketsFromClient";
            ScatterChartSample.setEntites(metricValue, testApp, metric);
//            fileWriter.write(e.toString());
//            fileWriter.flush();
        }

      launch();

    //    FileService.write("streams",
        //        Stream.getNameFields(), MockApp.getMasToString(), streamList);

       // ClassifierService.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ScatterChartSample().start(primaryStage);
    }
}
