package models;

import jpcap.packet.IPPacket;
import jpcap.packet.UDPPacket;
import lombok.Data;

@Data
public class UDP extends IP {

    private UDPPacket udpPacket;

    public UDP(IPPacket iPacket) {
        super(iPacket);
        udpPacket = (UDPPacket) iPacket;
    }

    @Override
    public int getTransportDataLength() {
        return udpPacket.data.length;
    }

    @Override
    public int getTransportHeaderLength() {
        return udpPacket.header.length;
    }
}
