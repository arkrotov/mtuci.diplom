package ru.krotov.logics.network;

import lombok.Data;
import ru.krotov.models.IP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 09.04.2017.
 */

@Data
public class Stream {

    public Stream(List<IP> flow) {

        double sizeOnTransportLayerFromServer = 0;
        double sizeOnTransportLayerFromClient = 0;
        double sizeDataOnTransportLayerFromClient = 0;
        double sizeDataOnTransportLayerFromServer = 0;
        int toClient = 3;
        int numberOfServingsFromServer = 0;
        int numberOfServingsFromClient = 0;


        for (IP ipPacket : flow) {

            if (ipPacket.isToMe()) {

                if (toClient != 1) { numberOfServingsFromClient++; toClient = 1; }

                sizeDataOnTransportLayerFromClient += ipPacket.getTransportDataLength();
                sizeOnTransportLayerFromClient += ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength();
                flowFromClient.add(ipPacket);
                sizesOfSegmentsFromClient.add(ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength());
                sizesOfDataFromClient.add(ipPacket.getTransportDataLength());
            } else {

                if (toClient != 0) {numberOfServingsFromServer++; toClient = 0; }

                sizeDataOnTransportLayerFromServer += ipPacket.getTransportDataLength();
                sizeOnTransportLayerFromServer += ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength();
                flowFromServer.add(ipPacket);
                sizesOfSegmentsFromServer.add(ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength());
                sizesOfDataFromServer.add(ipPacket.getTransportDataLength());
            }
        }

        //КПД клиента – количество переданной нагрузки прикладного уровня,
        //делённое на общее количество переданной нагрузки прикладного и транспортного уровня

        averageSizeOnTransportLayerFromClient = sizeOnTransportLayerFromClient / flowFromClient.size();
        averageSizeOnTransportLayerFromServer = sizeOnTransportLayerFromServer / flowFromServer.size();
        averageSizeDataOnTransportLayerFromClient = sizeDataOnTransportLayerFromClient / flowFromClient.size();
        averageSizeDataOnTransportLayerFromServer = sizeDataOnTransportLayerFromServer / flowFromServer.size();
        standardDeviationOfPacketSizeFromClient = standardDeviation(sizesOfSegmentsFromClient);
        standardDeviationOfPacketSizeFromServer = standardDeviation(sizesOfSegmentsFromServer);
        standardDeviationOfDataOnTransportLayerFromClien = standardDeviation(sizesOfDataFromClient);
        standardDeviationOfDataOnTransportLayerFromServer = standardDeviation(sizesOfDataFromServer);
        averageNumberOfDataPacketsFromClient = sizesOfSegmentsFromClient.size() / numberOfServingsFromClient;
        averageNumberOfDataPacketsFromServer = sizesOfSegmentsFromServer.size() / numberOfServingsFromServer;

    }

    // Расчетные данные

    // Пакеты в потоке
    private List<IP> flowFromClient = new ArrayList<>();
    private List<IP> flowFromServer = new ArrayList<>();

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
    private double averageSizeOnTransportLayerFromClient;

    // Стандартное отклонение размера пакета со стороны клиента
    private double standardDeviationOfPacketSizeFromClient;

    // Средний размер пакета со стороны сервера
    private double averageSizeOnTransportLayerFromServer;

    // Стандартное отклонение размера пакета со стороны сервера
    private double standardDeviationOfPacketSizeFromServer;

    // Средний размер порции данных со стороны клиента
    private double averageSizeDataOnTransportLayerFromClient;

    // Стандартное отклонение размера порции данных со стороны клиента
    private double standardDeviationOfDataOnTransportLayerFromClien;

    // Средний размер данных со стороны сервера
    private double averageSizeDataOnTransportLayerFromServer;

    // Стандартное отклонение размера данных со стороны сервера
    private double standardDeviationOfDataOnTransportLayerFromServer;

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
