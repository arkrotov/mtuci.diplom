package analyzer;

import jpcap.packet.TCPPacket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TCP {

    public static String toString(TCPPacket tcpPacket) {
        return //"Захваченные метка времени (сек): " + tcpPacket.sec + "\n" +
               // "Захваченные метка времени (микро сек): " + tcpPacket.usec + "\n" +
                (tcpPacket.version == 4 ?
                                 tcpPacket.src_ip +":"+ tcpPacket.src_port + "—> " + tcpPacket.dst_ip + ":" + tcpPacket.dst_port + "\n" +
                                "Протокол: " + tcpPacket.protocol + " " +
                                "Приоритет: " + tcpPacket.priority + " " +
                                "hop: " + tcpPacket.hop_limit + "\n" +
                                "Fragment offset (v4): " + tcpPacket.offset + " " +
                                "IDENTIFICATION (v4): " + tcpPacket.ident + " "
                        :
                        tcpPacket.src_ip + "—> " + tcpPacket.dst_ip + "\n" +
                                "Протокол: " + tcpPacket.protocol + " " +
                                "Приоритет: " + tcpPacket.priority + " " +
                                "Flow label (v6): " + tcpPacket.flow_label + "\n" +
                                "hop: " + tcpPacket.hop_limit + "\n") +
                "Тип: TCP \n" +
                "Sequence number: " + tcpPacket.sequence + " " +
                 "Window size: " + tcpPacket.window + "\n" +
                (tcpPacket.ack ? "Флаг: ack " + tcpPacket.ack_num + "\n" : "") +
                (tcpPacket.syn ? "Флаг: S\n" : "") +
                (tcpPacket.fin ? "Флаг: F\n" : "") +
                (tcpPacket.psh ? "Флаг: P\n" : "") +
                (tcpPacket.rst ? "Флаг: R\n" : "") +
                (tcpPacket.urg ? "Флаг: U\n" : "") +
                "header length: " + tcpPacket.header.length + "\n" +
                "data length: " + tcpPacket.data.length + "\n" +
                "packet length: " + tcpPacket.len + "\n" +
                "Packet length (v4/v6): " + tcpPacket.length + "\n\n" +
                        tcpPacket.data.toString() +
                "---------------------------------------------------\n";
    }

}
