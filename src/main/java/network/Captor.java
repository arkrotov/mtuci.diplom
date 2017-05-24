package network;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import java.io.IOException;

/**
 * Created by Me on 09.04.2017.
 */
public class Captor {

    private static JpcapCaptor instance;


    public static JpcapCaptor getInstance() throws InterruptedException {

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
}
