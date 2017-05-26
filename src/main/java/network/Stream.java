package network;

import lombok.Data;
import models.IP;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Me on 09.04.2017.
 */

@Data
public class Stream {


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
    private double standardDeviationOfDataOnTransportLayerFromClient;

    // Средний размер данных со стороны сервера
    private double averageSizeDataOnTransportLayerFromServer;

    // Стандартное отклонение размера данных со стороны сервера
    private double standardDeviationOfDataOnTransportLayerFromServer;

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
    private double sizeOnTransportLayerFromClient;

    //Общее количество переданной нагрузки прикладного уровня со стороны клиента
    private double sizeDataOnTransportLayerFromClient;

    //Общее количество переданных сегментов транспортного уровня со стороны клиента
    private int numberOfServingsFromClient;

    //Общее количество переданных байт со стороны сервера
    private double sizeOnTransportLayerFromServer;

    //Общее количество переданной нагрузки прикладного уровня со стороны сервера
    private double sizeDataOnTransportLayerFromServer;

    //Общее количество переданных сегментов транспортного уровня со стороны сервера
    private int numberOfServingsFromServer;

    private String testApp;

    public Stream(List<IP> flow) throws Exception {

        testApp = TestApp.getMas()[new Random().nextInt(TestApp.getMas().length)];

        sizeOnTransportLayerFromServer = 0;
        sizeOnTransportLayerFromClient = 0;
        sizeDataOnTransportLayerFromClient = 0;
        sizeDataOnTransportLayerFromServer = 0;
        numberOfServingsFromServer = 0;
        numberOfServingsFromClient = 0;
        int toClient = 3;

        for (IP ipPacket : flow) {

            if (ipPacket.isToMe()) {

                if (toClient != 1) {
                    numberOfServingsFromClient++;
                    toClient = 1;
                }

                sizeDataOnTransportLayerFromClient += ipPacket.getTransportDataLength();
                sizeOnTransportLayerFromClient += ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength();
                flowFromClient.add(ipPacket);
                sizesOfSegmentsFromClient.add(ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength());
                sizesOfDataFromClient.add(ipPacket.getTransportDataLength());

            } else {

                if (toClient != 0) {
                    numberOfServingsFromServer++;
                    toClient = 0;
                }

                sizeDataOnTransportLayerFromServer += ipPacket.getTransportDataLength();
                sizeOnTransportLayerFromServer += ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength();
                flowFromServer.add(ipPacket);
                sizesOfSegmentsFromServer.add(ipPacket.getTransportDataLength() + ipPacket.getTransportHeaderLength());
                sizesOfDataFromServer.add(ipPacket.getTransportDataLength());
            }
        }

        //КПД клиента – количество переданной нагрузки прикладного уровня,
        //делённое на общее количество переданной нагрузки прикладного и транспортного уровня

        averageSizeOnTransportLayerFromClient = sizeOnTransportLayerFromClient / (flowFromClient.size() == 0 ? 1 : flowFromClient.size());
        averageSizeOnTransportLayerFromServer = sizeOnTransportLayerFromServer / (flowFromServer.size() == 0 ? 1 : flowFromServer.size());
        averageSizeDataOnTransportLayerFromClient = sizeDataOnTransportLayerFromClient / (flowFromClient.size() == 0 ? 1 : flowFromClient.size());
        averageSizeDataOnTransportLayerFromServer = sizeDataOnTransportLayerFromServer / (flowFromServer.size() == 0 ? 1 : flowFromServer.size());
        standardDeviationOfPacketSizeFromClient = standardDeviation(sizesOfSegmentsFromClient);
        standardDeviationOfPacketSizeFromServer = standardDeviation(sizesOfSegmentsFromServer);
        standardDeviationOfDataOnTransportLayerFromClient = standardDeviation(sizesOfDataFromClient);
        standardDeviationOfDataOnTransportLayerFromServer = standardDeviation(sizesOfDataFromServer);
        averageNumberOfDataPacketsFromClient = sizesOfSegmentsFromClient.size() / (numberOfServingsFromClient == 0 ? 1 : numberOfServingsFromClient);
        averageNumberOfDataPacketsFromServer = sizesOfSegmentsFromServer.size() / (numberOfServingsFromServer == 0 ? 1 : numberOfServingsFromServer);
        efficiencyOfClient = sizeDataOnTransportLayerFromClient / (sizeOnTransportLayerFromClient == 0 ? 1 : sizeOnTransportLayerFromClient);
        efficiencyOfServer = sizeDataOnTransportLayerFromServer / (sizeOnTransportLayerFromServer == 0 ? 1 : sizeOnTransportLayerFromServer);
        ratio = sizeOnTransportLayerFromClient / (sizeOnTransportLayerFromServer == 0 ? 1 : sizeOnTransportLayerFromServer);
        ratioOfData = sizeDataOnTransportLayerFromClient / (sizeDataOnTransportLayerFromServer == 0 ? 1 : sizeDataOnTransportLayerFromServer);
        ratioOfNumberOfPackets = numberOfServingsFromClient / (numberOfServingsFromServer == 0? 1 : numberOfServingsFromServer);

    }

    private static double standardDeviation(List<? extends Number> entry) {

        double sr = 0;

        for (Number number : entry) {
            sr += Double.parseDouble(String.valueOf(number));
        }

        sr /= entry.size();

        double o = 0;

        for (Number number : entry) {
            o += Math.pow((Double.parseDouble(String.valueOf(number)) - sr), 2);
        }

        double result = Math.sqrt(o / (entry.size() - 1));
        return String.valueOf(result).equals("-0.0") ? 0 : result;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "\nflowFromClient=" + flowFromClient +
                "\n  flowFromServer=" + flowFromServer +
                "\n  sizesOfSegmentsFromClient=" + sizesOfSegmentsFromClient +
                "\n sizesOfSegmentsFromServer=" + sizesOfSegmentsFromServer +
                "\n sizesOfDataFromClient=" + sizesOfDataFromClient +
                "\n sizesOfDataFromServer=" + sizesOfDataFromServer +
                "\n averageSizeOnTransportLayerFromClient=" + averageSizeOnTransportLayerFromClient +
                "\n standardDeviationOfPacketSizeFromClient=" + standardDeviationOfPacketSizeFromClient +
                "\n averageSizeOnTransportLayerFromServer=" + averageSizeOnTransportLayerFromServer +
                "\n standardDeviationOfPacketSizeFromServer=" + standardDeviationOfPacketSizeFromServer +
                "\n averageSizeDataOnTransportLayerFromClient=" + averageSizeDataOnTransportLayerFromClient +
                "\n standardDeviationOfDataOnTransportLayerFromClient=" + standardDeviationOfDataOnTransportLayerFromClient +
                "\n averageSizeDataOnTransportLayerFromServer=" + averageSizeDataOnTransportLayerFromServer +
                "\n standardDeviationOfDataOnTransportLayerFromServer=" + standardDeviationOfDataOnTransportLayerFromServer +
                "\n averageNumberOfDataPacketsFromClient=" + averageNumberOfDataPacketsFromClient +
                "\n averageNumberOfDataPacketsFromServer=" + averageNumberOfDataPacketsFromServer +
                "\n efficiencyOfClient=" + efficiencyOfClient +
                "\n efficiencyOfServer=" + efficiencyOfServer +
                "\n ratio=" + ratio +
                "\n ratioOfData=" + ratioOfData +
                "\n ratioOfNumberOfPackets=" + ratioOfNumberOfPackets +
                "\n sizeOnTransportLayerFromClient=" + sizeOnTransportLayerFromClient +
                "\n sizeDataOnTransportLayerFromClient=" + sizeDataOnTransportLayerFromClient +
                "\n numberOfServingsFromClient=" + numberOfServingsFromClient +
                "\n sizeOnTransportLayerFromServer=" + sizeOnTransportLayerFromServer +
                "\n sizeDataOnTransportLayerFromServer=" + sizeDataOnTransportLayerFromServer +
                "\n numberOfServingsFromServer=" + numberOfServingsFromServer +
                "\n testApp='" + testApp + '\'' +
                "}\n\n";
    }

    public String toFile() {
        return averageSizeOnTransportLayerFromClient +
                "," + standardDeviationOfPacketSizeFromClient +
                "," + averageSizeOnTransportLayerFromServer +
                "," + standardDeviationOfPacketSizeFromServer +
                "," + averageSizeDataOnTransportLayerFromClient +
                "," + standardDeviationOfDataOnTransportLayerFromClient +
                "," + averageSizeDataOnTransportLayerFromServer +
                "," + standardDeviationOfDataOnTransportLayerFromServer +
                "," + averageNumberOfDataPacketsFromClient +
                "," + averageNumberOfDataPacketsFromServer +
                "," + efficiencyOfClient +
                "," + efficiencyOfServer +
                "," + ratio +
                "," + ratioOfData +
                "," + ratioOfNumberOfPackets +
                "," + sizeOnTransportLayerFromClient +
                "," + sizeDataOnTransportLayerFromClient +
                "," + numberOfServingsFromClient +
                "," + sizeOnTransportLayerFromServer +
                "," + sizeDataOnTransportLayerFromServer +
                "," + numberOfServingsFromServer +
                "," + testApp;
    }

    public static String[] getNameFields() {

        Field[] fields = Stream.class.getDeclaredFields();

        String[] nameFields = new String[fields.length - 6];

        for (int i = 6; i < fields.length; i++) {
            nameFields[i - 6] = fields[i].getName();
        }

        return nameFields;
    }

    public static void main(String[] args) {
        int i = 0;
        for (String s : getNameFields()) {
            System.out.println(i++ + " " + s);
        }

    }
}
