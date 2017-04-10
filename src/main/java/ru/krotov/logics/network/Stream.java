package ru.krotov.logics.network;

import jpcap.packet.IPPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 09.04.2017.
 */

public class Stream {


    public Stream(List<IPPacket> flow) {

        double sizeOfPacketFromClient = 0;
        double sizeOfPacketFromServer = 0;
        double sizeDataFromClient = 0;
        double sizeDataFromServer = 0;
        int toClient = 3;
        int numberOfServingsFromServer = 0;
        int numberOfServingsFromClient = 0;

        for (IPPacket packet : flow) {

            if (IP.isToMe(packet)) {

                if (toClient != 1) { numberOfServingsFromClient++; toClient = 1; }

                sizeDataFromClient += packet.length;
                sizeOfPacketFromClient += packet.data.length;
                flowFromClient.add(packet);
                sizesOfSegmentsFromClient.add((int) packet.length);
                sizesOfDataFromClient.add(packet.data.length);
            } else {

                if (toClient != 0) {numberOfServingsFromServer++; toClient = 0; }

                sizeDataFromServer += packet.length;
                sizeOfPacketFromServer += packet.data.length;
                flowFromServer.add(packet);
                sizesOfSegmentsFromServer.add((int) packet.length);
                sizesOfDataFromServer.add(packet.data.length);
            }
        }   //КПД клиента – количество переданной нагрузки прикладного уровня,
        //делённое на общее количество переданной нагрузки прикладного и транспортного уровня

        averageSizeOfPacketFromClient = sizeOfPacketFromClient / flowFromClient.size();
        averageSizeOfPacketFromServer = sizeOfPacketFromServer / flowFromServer.size();
        averageSizeOfDataFromClient = sizeDataFromClient / flowFromClient.size();
        averageSizeOfDataFromServer = sizeDataFromServer / flowFromServer.size();
        standardDeviationOfDataSizeFromClient = standardDeviation(sizesOfDataFromClient);
        standardDeviationOfDataSizeFromServer = standardDeviation(sizesOfDataFromServer);
        standardDeviationOfPacketSizeFromClient = standardDeviation(sizesOfSegmentsFromClient);
        standardDeviationOfPacketSizeFromServer = standardDeviation(sizesOfSegmentsFromServer);
        averageNumberOfDataPacketsFromClient = sizesOfSegmentsFromClient.size() / numberOfServingsFromClient;
        averageNumberOfDataPacketsFromServer = sizesOfSegmentsFromServer.size() / numberOfServingsFromServer;

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



    // Остальные метрики

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

    public static double standardDeviation (List <? extends Number> entry) {

        double sr = 0;

        for (Number number : entry) {
            sr += (double) number;
        }

        sr /= entry.size();

        double o = 0;

        for (Number number : entry) {
            o += Math.pow(((double) number - sr), 2);
        }

        return Math.sqrt(o / (entry.size() - 1));
    }
}
