package analyzer;

import ru.krotov.logics.network.Captor;
import jpcap.JpcapCaptor;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import ru.krotov.services.GroupService;


import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;


public class Main {

    private static JpcapCaptor captor = Captor.getInstance();

    public static void main(String[] args) throws IOException, InterruptedException {

        int count = 0, nol = 0, g = 0;
        while (Timestamp.from(Instant.now()).getHours() != 5){

            Packet packet = captor.getPacket();

            if (packet == null) {
                nol++;
                continue;
            }

            if (!(packet instanceof IPPacket)) {
                g = 0;
                continue;
            }


            count += 1;
            if (count % 100 == 0)
                System.out.printf("Обработали %d пакетов, из них: \n" +
                        "null - %d\n" +
                        "принадлежат IP протоколу - %d\n" +
                        "остальные - %d\n" +
                        "количество потоков - %d\n\n\n", count, nol, count-nol-g, g, GroupService.getResultSize());
        }





        }

}
