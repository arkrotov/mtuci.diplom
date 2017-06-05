package Applicattion;

import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

import java.util.ArrayList;
import java.util.List;

public class GroupService {

    static boolean classify = false;
    static private List<List<IP>> groupFlow = new ArrayList<>();

    public static void formFlow(IPPacket currentPacket) throws Exception {

        DaoService daoService = App.app.getDaoService();

        for (int i = 0; i < groupFlow.size(); i++) {

            for (int j = 0; j < groupFlow.get(i).size(); j++) {

                List<IP> flow = groupFlow.get(i);

                IP packet = flow.get(j);

                if (belongToFlow(packet, new IP(currentPacket))) {

                    if (currentPacket instanceof TCPPacket) {
                        if (((TCPPacket) currentPacket).fin) {
                            flow.add(new TCP(currentPacket));
                            Stream stream = new Stream(flow);
                            daoService.getStreams().add(stream);
                            groupFlow.remove(flow);
                            if (classify) {
                                ClassifierService.classify(stream);// TODO: Classifier add packet!
                            }
                            return;
                        }
                        flow.add(new TCP(currentPacket));
                    }
                    return;
                }
            }
        }

        List<IP> flow = new ArrayList<>();
        flow.add(new TCP(currentPacket));
        groupFlow.add(flow);
    }


    private static boolean belongToFlow(IP o1, IP o2) {

        if (o1.getIpPacket().protocol != o2.getIpPacket().protocol)
            return false;

        if (!((o1.getIpPacket().dst_ip.equals(o2.getIpPacket().dst_ip) && o1.getIpPacket().src_ip.equals(o2.getIpPacket().src_ip))
                || (o1.getIpPacket().dst_ip.equals(o2.getIpPacket().src_ip) && o1.getIpPacket().src_ip.equals(o2.getIpPacket().dst_ip))))
            return false;

        if (o1.getIpPacket() instanceof UDPPacket && o2.getIpPacket() instanceof UDPPacket) {

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


}
