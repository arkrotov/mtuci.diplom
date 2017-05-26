import classifiers.Test;
import classifiers.TestFile;
import jpcap.JpcapCaptor;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import models.IP;
import network.Captor;
import network.Stream;
import network.TestApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import services.GroupService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MainClassT {


    private static boolean enable = true;

    public static void main(String[] args) throws Exception {
        // SpringApplication.run(Main.class, args);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    s = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //    if (s.equals("stop")) {
                    enable = false;
            //    }
            }
        }).start();



        JpcapCaptor instance = Captor.getInstance();

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

        System.out.println("---------------------------------------------");
        System.out.printf("Итоговое количество потоков - %d\n", GroupService.getResultSize());

        List<List<IP>> resultGroupFlow = GroupService.getResultGroupFlow();

        List<Stream> streamList = new ArrayList<>();


        FileWriter fileWriter = new FileWriter(new File("StreamInfo"));
        for (List<IP> ipList : resultGroupFlow) {
            Stream e = new Stream(ipList);
            streamList.add(e);
            fileWriter.write(e.toString());
            fileWriter.flush();
        }


        TestFile testFile = new TestFile("streams",
                Stream.getNameFields(), TestApp.getMasToString(), streamList);

        testFile.write();

        // Test test = new Test(streamList);


    }
}
