package analyzer;

import jpcap.packet.TCPPacket;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Я on 02.02.2017.
 */
public class aPacket {

    public aPacket(TCPPacket packet) {
        this.src_ip = packet.src_ip;
        this.dst_ip = packet.dst_ip;
    }

    // Расчетные данные

    private InetAddress src_ip;
    private InetAddress dst_ip;

    // Размеры сегментов со стороны клиента
    private List<Integer> sizesOfSegmentsFromClient = new ArrayList<>();

    // Размеры сегментов со стороны сервера
    private List<Integer> sizesOfSegmentsFromServer = new ArrayList<>();

    // Размеры порций данных со стороны клиента
    private List<Integer> sizesOfDataFromClient = new ArrayList<>();

    // Размеры порций данных со стороны сервера
    private List<Integer> sizesOfDataFromServer = new ArrayList<>();

    public InetAddress getSrc_ip() {
        return src_ip;
    }

    public InetAddress getDst_ip() {
        return dst_ip;
    }

    public List<Integer> getSizesOfSegmentsFromClient() {
        return sizesOfSegmentsFromClient;
    }

    public List<Integer> getSizesOfSegmentsFromServer() {
        return sizesOfSegmentsFromServer;
    }

    public List<Integer> getSizesOfDataFromClient() {
        return sizesOfDataFromClient;
    }

    public List<Integer> getSizesOfDataFromServer() {
        return sizesOfDataFromServer;
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

    //Общее количество переданных порций данных со стороны клиента
   // private double;
   /* Общее количество переданных байт со стороны сервера
    Общее количество переданной нагрузки прикладного уровня со стороны сервера
    Общее количество переданных сегментов транспортного уровня со стороны сервера
    Общее количество переданных порций данных со стороны сервера
    Размер первого сегмента транспортного уровня со стороны клиента*/

}
