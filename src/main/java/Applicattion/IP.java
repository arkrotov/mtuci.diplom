package Applicattion;

import jpcap.packet.IPPacket;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Pattern;

@Data
public class IP {

    public IP(IPPacket ipPacket) {
        this.ipPacket = ipPacket;
        timestamp = Timestamp.from(Instant.now());
    }

    private IPPacket ipPacket;
    private Timestamp timestamp;

    //TODO: Уйти от костыля  throw new Exception("Нельзя вызывать метод у класса Applicattion.IP");
    public int getTransportDataLength () throws Exception {
        throw new Exception("Нельзя вызывать метод у класса Applicattion.IP");
    }
    public int getTransportHeaderLength () throws Exception {
        throw new Exception("Нельзя вызывать метод у класса Applicattion.IP");
    }

    public boolean isToMe () {

        final String FIRST_IP = "localhost";
        final String SECOND_IP = "95.165.131.144";
        final Pattern THIRD_IP = Pattern.compile("192.168.[0-2]?[0-2]?[0-5]?.[0-2]?[0-2]?[0-5]?");

        String ip = ipPacket.dst_ip.toString().substring(1);

        return ip.equals(FIRST_IP) || ip.equals(SECOND_IP) || THIRD_IP.matcher(ip).matches();
    }


}
