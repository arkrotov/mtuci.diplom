package analyzer;

import analyzer.database.Database;
import analyzer.database.TCPPacketDB;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainClass {

    static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
    static int index = 0;

    public static void main(String[] args) throws IOException {

        JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
        Database database = new Database();

        String target = "src/main/java/packets/";
        String fileName = UUID.randomUUID().toString().substring(0,6)+".txt";
        File file = new File(target+fileName);

        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        System.out.println(file.getPath());


        List<aPacket> packets = new ArrayList<>();
        List<aPacket> activePackets = new ArrayList<>();
        int count = 0;
        aPacket activePacket = null;
        while (true) {

            Packet packet = captor.getPacket();

            if (packet instanceof TCPPacket) {

                activePacket = null;

                for (aPacket activePacket1 : activePackets) {
                    activePacket = activePacket1;
                    if (activePacket.getSrc_ip().equals(((TCPPacket) packet).src_ip)
                            && activePacket.getDst_ip().equals(((TCPPacket) packet).dst_ip) ||
                            activePacket.getSrc_ip().equals(((TCPPacket) packet).dst_ip)
                            && activePacket.getDst_ip().equals(((TCPPacket) packet).src_ip)) {
                        break;
                    }
                    activePacket = null;
                }

                if (activePacket == null) {
                    activePacket = new aPacket((TCPPacket) packet);
                    activePackets.add(activePacket);
                }

                if (activePacket.getSrc_ip().toString().contains("192.168.1.3")) {
                    activePacket.getSizesOfSegmentsFromServer().add(packet.len);
                } else
                    activePacket.getSizesOfSegmentsFromClient().add(packet.len);

                if (((TCPPacket) packet).fin) {
                    activePackets.remove(activePacket);
                    packets.add(activePacket);
                    count++;
                }

                if (count == 10) break;

                System.out.println(activePackets.size() + " " + count);
                /*TCPPacketDB.setPacket(database.connection, (TCPPacket) packet);
                System.out.println(TCP.toString((TCPPacket) packet));
                bf.write(TCP.toString((TCPPacket) packet));
                bf.flush();
                if (((TCPPacket) packet).fin) System.out.println("++++++++++++++++++++++++++++++++++++++");*/
            }

        }

        for (aPacket activePaacket : packets) {
            System.out.print("From Client: ");
            for (Integer integer : activePaacket.getSizesOfSegmentsFromClient()) {
                System.out.print(integer + " ");
            }
            System.out.print("\nFrom Server: ");
            for (Integer integer : activePaacket.getSizesOfSegmentsFromServer()) {
                System.out.print(integer + " ");
            }
            System.out.println();
            System.out.println(activePaacket.getSizesOfSegmentsFromClient().size() + " " + activePaacket.getSizesOfSegmentsFromServer().size());
        }
    }


    public static void info () {

        //for each network interface
        for (int i = 0; i < devices.length; i++) {
            //print out its name and description
            System.out.println(i + ": " + devices[i].name + "(" + devices[i].description + ")");

            //print out its datalink name and descript  ion
            System.out.println(" datalink: " + devices[i].datalink_name + "(" + devices[i].datalink_description + ")");

            //print out its MAC address
            System.out.print(" MAC address:");
            for (byte b : devices[i].mac_address)
                System.out.print(Integer.toHexString(b & 0xff) + ":");
            System.out.println();

            //print out its IP address, subnet mask and broadcast address
            for (NetworkInterfaceAddress a : devices[i].addresses)
                System.out.println(" address:" + a.address + " " + a.subnet + " " + a.broadcast);
            System.out.println("--------------------------------------");
        }
    }
}
