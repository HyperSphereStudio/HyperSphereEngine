package Connection;
import Connection.Read.PacketReader;
import com.esotericsoftware.kryonet.Connection;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

public abstract class PacketEvent {
    public PacketReader packetReader;
    public static Short2ObjectOpenHashMap<PacketEvent> packetEvents = new Short2ObjectOpenHashMap<>();
    public String label;

    public PacketEvent(short id, PacketReader packetReader, String label) {
        this.packetReader = packetReader;
        packetEvents.put(id, this);
        this.label = label;
    }

    public void read(Connection c, Packet p){
        packetReader.readPacket(c, p);
    }

}
