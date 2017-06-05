package Applicattion;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CaptorService implements Runnable{

    private boolean enable = true;
    private JpcapCaptor instance;

    private JpcapCaptor getInstance() throws InterruptedException {

        if (instance == null) {

            NetworkInterface[] devices = JpcapCaptor.getDeviceList();

            int index = 0;

            try {
                instance = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return instance;
    }

    @Override
    public void run(){

        try {

            System.out.println("Старт");

            JpcapCaptor instance = getInstance();

            int count = 1; // TODO: В спринге поставить Scheduler на логирование. (Future)

            while (enable) {

                Packet packet = instance.getPacket();

//                if (count % 100 == 0) {
//                    System.out.printf("Обработано пакетов - %d, сформировано потоков - %d\n",
//                            count, GroupService.getResultSize());
//                }

                if (packet == null || !(packet instanceof TCPPacket)) {
                    continue;
                }

                count++;

                GroupService.formFlow((TCPPacket) packet);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     Thread lifeCycleOfCaptorService = new Thread(() -> {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            reader.readLine();
            enable = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
}
