package models;

import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import lombok.Data;

@Data
public class TCP extends IP{

    private TCPPacket tcpPacket;

    public TCP(IPPacket iPacket) {
        super(iPacket);
        tcpPacket = (TCPPacket) iPacket;
    }

    @Override
    public int getTransportDataLength() {
        return tcpPacket.data.length;
    }

    @Override
    public int getTransportHeaderLength() {
        return tcpPacket.header.length;
    }
}
