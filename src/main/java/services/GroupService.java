package services;

import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;
import models.IP;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 09.04.2017.
 */

public class GroupService {

    // Группы потоков (служенбная, результирующая)
    static private List<List<IP>> groupFlow = new ArrayList<>();
    static private List<List<IP>> resultGroupFlow = new ArrayList<>();


    public static int getResultSize () {
        return resultGroupFlow.size();
    }

    // Формирование потока
    public static void formFlow(IP currentPacket) {


        for (int i = 0; i < groupFlow.size(); i++) {
            for (int j = 0; j < groupFlow.get(i).size(); j++) {

                List<IP> flow = groupFlow.get(i);
                IP packet = flow.get(j);

                if (belongToFlow(packet, currentPacket)) {


                    if (currentPacket.getIpPacket() instanceof TCPPacket) {
                        if (((TCPPacket) currentPacket.getIpPacket()).fin) {
                            flow.add(currentPacket);
                            resultGroupFlow.add(flow);
                            groupFlow.remove(flow);
                            return;
                        }
                    } else if (currentPacket.getIpPacket() instanceof UDPPacket) {
                        if (currentPacket.getTimestamp().getMinutes() - flow.get(0).getTimestamp().getMinutes() >= 5) {
                            flow.add(currentPacket); // TODO: Сощдавать новый поток?!
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

    // Проверка на принадлежность к существующим потокам
    static private boolean belongToFlow(IP o1, IP o2) {

        if (o1.getIpPacket().protocol != o2.getIpPacket().protocol)
            return false;

        if (!((o1.getIpPacket().dst_ip.equals(o2.getIpPacket().dst_ip) && o1.getIpPacket().src_ip.equals(o2.getIpPacket().src_ip))
                || (o1.getIpPacket().dst_ip.equals(o2.getIpPacket().src_ip) && o1.getIpPacket().src_ip.equals(o2.getIpPacket().dst_ip))))
            return false;

        if (o1.getIpPacket() instanceof UDPPacket && o2.getIpPacket() instanceof  UDPPacket) {

            UDPPacket o1UDP = (UDPPacket) o1.getIpPacket();
            UDPPacket o2UDP = (UDPPacket) o2.getIpPacket();

            return (o1UDP.dst_port == o2UDP.dst_port && o1UDP.src_port == o2UDP.src_port)
                    || (o1UDP.dst_port == o2UDP.src_port && o1UDP.src_port == o2UDP.dst_port);
        }

        if (o1.getIpPacket() instanceof TCPPacket && o2.getIpPacket() instanceof TCPPacket) {

            TCPPacket o1TCP = (TCPPacket) o1.getIpPacket();
            TCPPacket o2TCP = (TCPPacket) o2.getIpPacket();

            return (o1TCP.dst_port == o2TCP.dst_port && o1TCP.src_port == o2TCP.src_port)
                    || (o1TCP.dst_port == o2TCP.src_port && o1TCP.src_port == o2TCP.dst_port);
        }

        return false;

    }

    // Вывод потоков на экран
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

            List<IP> flow = resultGroupFlow.get(i);

            Timestamp time = null;
            for (int j = 0; j < flow.size(); j++) {

                IP obj = flow.get(j);

                if (j == 0)
                    time = obj.getTimestamp();

                System.out.println(obj.getIpPacket());

                if (j == flow.size() - 1) {
                    System.out.println("Разница " +
                            (obj.getTimestamp().getMinutes() - time.getMinutes()) + "минут");
                    if (obj.getIpPacket() instanceof TCPPacket) System.out.println(((TCPPacket) obj.getIpPacket()).fin);
                }
            }

            System.out.println();
        }
    }


}
