package analyzer.database;

import jpcap.packet.TCPPacket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TCPPacketDB {

    public static void setPacket(Connection connection, TCPPacket packet) {

        try {
            String query = "INSERT INTO tcp_packet(src_ip, dst_ip, protocol, priority, hop, " +
                    "src_port, dst_port, sequence_number, window_size, flag)  VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, String.valueOf(packet.src_ip));
            pstmt.setString(2, String.valueOf(packet.dst_ip));
            pstmt.setInt(3, packet.protocol);
            pstmt.setInt(4, packet.priority);
            pstmt.setInt(5, packet.hop_limit);
            pstmt.setInt(6, packet.src_port);
            pstmt.setInt(7, packet.dst_port);
            pstmt.setLong(8, packet.sequence);
            pstmt.setInt(9, packet.window);
            if (packet.ack && packet.syn)
                pstmt.setString(10, String.format("SYN ACK(%d)", packet.ack_num));
            else if (packet.ack)
                pstmt.setString(10, String.format("%s (%d)", String.valueOf(TCPFlag.select(packet)), packet.ack_num));
            else pstmt.setString(10, String.valueOf(TCPFlag.select(packet)));
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }

    private enum TCPFlag {
        SYN, ACK, FIN, PSH, RST, URG, RSV1, RSV2;

        public static TCPFlag select(TCPPacket packet) {

            if (packet.syn) return SYN;
            if (packet.ack) return ACK;
            if (packet.fin) return FIN;
            if (packet.psh) return PSH;
            if (packet.rst) return RST;
            if (packet.urg) return URG;
            if (packet.rsv1) return RSV1;
            if (packet.rsv2) return RSV2;

            throw new IllegalArgumentException();

        }
    }

}
