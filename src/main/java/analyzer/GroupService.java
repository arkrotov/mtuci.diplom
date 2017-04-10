package analyzer;

import analyzer.network.IP;
import jpcap.packet.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 09.04.2017.
 */
public class GroupService {

    // Группа потоков
    static volatile private List<List<IP>> groupFlow = new ArrayList<>();
    static volatile private List<List<IP>> resultGroupFlow = new ArrayList<>();


    static public int getResultSize () {
        return resultGroupFlow.size();
    }

    public static void formFlow(IP currentPacket) {


        for (int i = 0; i < groupFlow.size(); i++) {
            for (int j = 0; j < groupFlow.get(i).size(); j++) {

                List<IP> flow = groupFlow.get(i);
                IP packet = flow.get(j);

                if (belongToFlow(packet, currentPacket)) {

                    if (currentPacket.getPacket() instanceof TCPPacket) {
                        if (((TCPPacket) currentPacket.getPacket()).fin) {
                            flow.add(currentPacket);
                            resultGroupFlow.add(flow);
                            groupFlow.remove(flow);
                            return;
                        }
                    } else if (currentPacket.getPacket() instanceof UDPPacket) {
                        if (currentPacket.getTimestamp().getMinutes() - flow.get(0).getTimestamp().getMinutes() >= 5) {
                            flow.add(currentPacket);
                            resultGroupFlow.add(flow);
                            groupFlow.remove(flow);
                            return;
                        }
                    }

                    flow.add(currentPacket);
                    return;
                }

            }
        }


        List<IP> flow = new ArrayList<>();
        flow.add(currentPacket);
        groupFlow.add(flow);

    }

    static private boolean belongToFlow(IP o1, IP o2) {

        if (o1.getPacket().protocol != o2.getPacket().protocol)
            return false;

        if (!((o1.getPacket().dst_ip.equals(o2.getPacket().dst_ip) && o1.getPacket().src_ip.equals(o2.getPacket().src_ip))
                || (o1.getPacket().dst_ip.equals(o2.getPacket().src_ip) && o1.getPacket().src_ip.equals(o2.getPacket().dst_ip))))
            return false;

        if (o1.getPacket() instanceof UDPPacket && o2.getPacket() instanceof  UDPPacket) {

            UDPPacket o1UDP = (UDPPacket) o1.getPacket();
            UDPPacket o2UDP = (UDPPacket) o2.getPacket();

            return (o1UDP.dst_port == o2UDP.dst_port && o1UDP.src_port == o2UDP.src_port)
                    || (o1UDP.dst_port == o2UDP.src_port && o1UDP.src_port == o2UDP.dst_port);
        }

        if (o1.getPacket() instanceof TCPPacket && o2.getPacket() instanceof TCPPacket) {

            TCPPacket o1TCP = (TCPPacket) o1.getPacket();
            TCPPacket o2TCP = (TCPPacket) o2.getPacket();

            return (o1TCP.dst_port == o2TCP.dst_port && o1TCP.src_port == o2TCP.src_port)
                    || (o1TCP.dst_port == o2TCP.src_port && o1TCP.src_port == o2TCP.dst_port);
        }

        return false;

    }


    public static void soutFlows() {

        File file = new File("src/main/java/packets/test.txt");
        try {
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < resultGroupFlow.size(); i++) {
            System.out.println("Поток №" + i);

            List<IP> flow = groupFlow.get(i);

            Timestamp time = null;
            for (int j = 0; j < flow.size(); j++) {

                IP obj = flow.get(j);

                if (j == 0)
                    time = obj.getTimestamp();

                System.out.println(obj.getPacket());

                if (j == flow.size() - 1) {
                    System.out.println("Разница " +
                            (obj.getTimestamp().getMinutes() - time.getMinutes()) + "минут");
                    if (obj.getPacket() instanceof TCPPacket) System.out.println(((TCPPacket) obj.getPacket()).fin);
                }
            }

            System.out.println();
        }
    }

}
