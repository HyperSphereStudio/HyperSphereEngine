package Connection.Read;

import Connection.Packet;
import com.esotericsoftware.kryonet.Connection;

public interface PacketReader {
    void readPacket(Connection c, Packet packet);
}
