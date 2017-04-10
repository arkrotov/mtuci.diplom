package analyzer.network;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import java.io.IOException;

/**
 * Created by Me on 09.04.2017.
 */
public class Captor {

    private static JpcapCaptor instance;

    public static JpcapCaptor getInstance()  {

        if (instance == null) {

            int index = 0;
            NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            try {
                instance = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
            } catch (IOException e) {
                System.exit(400);
            }
        }

        return instance;
    }
}
