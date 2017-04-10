package ru.krotov.logics.network;

import jpcap.packet.IPPacket;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Pattern;

/**
 * Created by Me on 09.04.2017.
 */
public class IP {

    private IPPacket packet;
    private Timestamp timestamp;

    public IP(IPPacket packet) {
        this.packet = packet;
        timestamp = Timestamp.from(Instant.now());
    }

    public IPPacket getPacket() {
        return packet;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public static boolean isToMe (IPPacket packet) {

        final String FIRST_IP = "localhost";
        final String SECOND_IP = "95.165.131.144";
        final Pattern THIRD_IP = Pattern.compile("192.168.[0-2]?[0-2]?[0-5]?.[0-2]?[0-2]?[0-5]?");

        String ip = packet.dst_ip.toString().substring(1);

        return ip.equals(FIRST_IP) || ip.equals(SECOND_IP) || THIRD_IP.matcher(ip).matches();
    }

}
