package analyzer.network;

import jpcap.packet.IPPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 09.04.2017.
 */

public class Flow {

    public Flow(List<IPPacket> flow) {

        double sizeOfPacketFromClient = 0;
        double sizeOfPacketFromServer = 0;
        double sizeDataFromClient = 0;
        double sizeDataFromServer = 0;


        for (IPPacket packet : flow) {
            if (IP.isToMe(packet)) {

                sizeDataFromClient += packet.length;
                sizeOfPacketFromClient += packet.data.length;
                flowFromClient.add(packet);
                sizesOfSegmentsFromClient.add((int) packet.length);
                sizesOfDataFromClient.add(packet.data.length);
            } else {

                sizeDataFromServer += packet.length;
                sizeDataFromServer += packet.data.length;
                flowFromServer.add(packet);
                sizesOfSegmentsFromServer.add((int) packet.length);
                sizesOfDataFromServer.add(packet.data.length);
            }
        }

        averageSizeOfPacketFromClient = sizeDataFromClient / flowFromClient.size();
        averageSizeOfPacketFromServer = sizeOfPacketFromServer / flowFromServer.size();
        averageSizeOfDataFromClient = sizeDataFromClient / flowFromClient.size();
        averageSizeOfDataFromServer = sizeDataFromServer / flowFromServer.size();

    }

    // Расчетные данные

    // Пакеты в потоке
    private List<IPPacket> flowFromClient = new ArrayList<>();
    private List<IPPacket> flowFromServer = new ArrayList<>();

    // Размеры сегментов со стороны клиента
    private List<Integer> sizesOfSegmentsFromClient = new ArrayList<>();

    // Размеры сегментов со стороны сервера
    private List<Integer> sizesOfSegmentsFromServer = new ArrayList<>();

    // Размеры порций данных со стороны клиента
    private List<Integer> sizesOfDataFromClient = new ArrayList<>();

    // Размеры порций данных со стороны сервера
    private List<Integer> sizesOfDataFromServer = new ArrayList<>();


    public static void main(String[] args) {
        List <Integer> list = new ArrayList<>();
        list.add(1);
        list.add(7);
        list.add(9);
        list.add(0);

        double sr = 0;
        for (Integer integer : list) {
            sr += integer;
        }

        sr /= list.size();

        double o = 0;
        for (Integer integer : list) {
            o += Math.pow((integer - sr), 2);
            System.out.print(integer + " " + sr + " " + Math.pow((integer - sr), 2) + " ");
        }
        System.out.println(o);

        o = Math.sqrt(o / (list.size() - 1));
        System.out.println(sr);
        System.out.println(list.size());
        System.out.println(o);
    }


    // Экспериментальные данные

    // Средний размер пакета со стороны клиента
    private double averageSizeOfPacketFromClient;

    // Стандартное отклонение размера пакета со стороны клиента
    private double standardDeviationOfPacketSizeFromClient;

    // Средний размер пакета со стороны сервера
    private double averageSizeOfPacketFromServer;

    // Стандартное отклонение размера пакета со стороны сервера
    private double standardDeviationOfPacketSizeFromServer;

    // Средний размер порции данных со стороны клиента
    private double averageSizeOfDataFromClient;

    // Стандартное отклонение размера порции данных со стороны клиента
    private double standardDeviationOfDataSizeFromClient;

    // Средний размер данных со стороны сервера
    private double averageSizeOfDataFromServer;

    // Стандартное отклонение размера данных со стороны сервера
    private double standardDeviationOfDataSizeFromServer;

    // Среднее число пакетов на порцию данных со стороны клиента
    private double averageNumberOfDataPacketsFromClient;

    //Среднее число пакетов на порцию данных со стороны сервера
    private double averageNumberOfDataPacketsFromServer;

    //КПД клиента – количество переданной нагрузки прикладного уровня,
    //делённое на общее количество переданной нагрузки прикладного и транспортного уровня
    private double efficiencyOfClient;

    // КПД сервера
    private double efficiencyOfServer;

    //Соотношение байт – во сколько раз клиент передал больше байт, чем сервер
    private double ratio;

    //Соотношение полезной нагрузки – во сколько раз клиент передал больше байт, чем сервер
    private double ratioOfData;

    //Соотношение пакетов – во сколько раз клиент передал больше пакетов, чем сервер
    private double ratioOfNumberOfPackets;

    //Общее количество переданных байт со стороны клиента
    private double totalBytesFromClient;

    //Общее количество переданной нагрузки прикладного уровня со стороны клиента
    private double totalBytesOfDataFromClient;

    //Общее количество переданных сегментов транспортного уровня со стороны клиента
    private double totalNubmerFromClient;

}
